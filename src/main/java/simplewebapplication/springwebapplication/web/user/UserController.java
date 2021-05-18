package simplewebapplication.springwebapplication.web.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import simplewebapplication.springwebapplication.service.user.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/non")
    public String readOnly() {

        userService.createReadOnlyUser();

        return "redirect:/boards";
    }

    @GetMapping("/login")
    public String userLogin() {
        return "users/user-login";
    }
}
