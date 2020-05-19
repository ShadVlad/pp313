package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@RestController
@RequestMapping("/")
public class RESTUserController {
    private final UserService userService;

    public RESTUserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/admin")
//    public ResponseEntity<?> getAllUsers() {
//    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);

    }

    @PostMapping(value = "/admin/add")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if ("".equals(user.getUsername()) ||
            "".equals(user.getPassword()) ||
            user.getRoles().size() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.add(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.delete(userService.getUserById(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
