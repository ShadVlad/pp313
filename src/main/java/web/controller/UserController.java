package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.Role;
import web.model.User;
import web.service.UserService;

@Controller
public class UserController {

    @GetMapping("/admin/users")
    public String adminPage(Model model) {
        User userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userauth", userAuth);
        return "users";
    }

    @GetMapping("/user")
    public String userPage(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //User user = userService.getUserByName(userAuth.getUsername());
        model.addAttribute("user", user);
        Role roleAdmin = new Role();
        roleAdmin = new Role(1L,"admin");
        model.addAttribute("roleAdmin", roleAdmin);
        model.addAttribute("userauth", user);
        return "user";
    }
}


