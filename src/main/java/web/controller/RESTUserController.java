package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RestTemplateService;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RESTUserController {
    private final RestTemplateService userService;

    public RESTUserController(RestTemplateService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsersList(), HttpStatus.OK);

    }

    @PostMapping(value = "/admin/add")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        UserDetails userDS = new User();
        userDS = userService.getUserByName(user.getUsername());
        if (userDS != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if ("".equals(user.getUsername()) ||
            "".equals(user.getPassword()) ||
            user.getRoles().size() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Role> rolesAdd = new ArrayList<>();
            for (Role role : user.getRoles()) {
                rolesAdd.add(userService.getRoleByName(role.getRole()));
            }
        user.setRoles(rolesAdd);
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/admin/edit/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/admin/edit")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        List<Role> rolesAdd = new ArrayList<>();
        for (Role role : user.getRoles()) {
            rolesAdd.add(userService.getRoleByName(role.getRole()));
        }
        user.setRoles(rolesAdd);
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(userService.getAllRoles(), HttpStatus.OK);
    }
}
