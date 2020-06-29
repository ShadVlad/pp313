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
    private final RestTemplateService restTemplateService;

    public RESTUserController(RestTemplateService userService) {
        this.restTemplateService = userService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> listUser = restTemplateService.getUsersList();
        return new ResponseEntity<>(listUser, HttpStatus.OK);

    }

    @PostMapping(value = "/admin/add")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        List<Role> rolesAdd = new ArrayList<>();
            for (Role role : user.getRoles()) {
                rolesAdd.add(restTemplateService.getRoleByName(role.getRole()));
            }
        user.setRoles(rolesAdd);
        restTemplateService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        restTemplateService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/admin/edit/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User user = restTemplateService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/admin/edit")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        List<Role> rolesAdd = new ArrayList<>();
        for (Role role : user.getRoles()) {
            rolesAdd.add(restTemplateService.getRoleByName(role.getRole()));
        }
        user.setUserName(user.getUsername());
        user.setRoles(rolesAdd);
        restTemplateService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(restTemplateService.getAllRoles(), HttpStatus.OK);
    }
}
