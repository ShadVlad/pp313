package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RESTUserController {
    private final UserService userService;

    public RESTUserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/admin")
//    public ResponseEntity<?> getAllUsers() {
//    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);

    }

    @PostMapping(value = "/admin/add")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User userDS = new User();
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
        userService.add(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.delete(userService.getUserById(id));
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
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(userService.rolesList(), HttpStatus.OK);
    }
}
