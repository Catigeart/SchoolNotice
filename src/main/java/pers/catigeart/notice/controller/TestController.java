package pers.catigeart.notice.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.catigeart.notice.entity.User;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/4/20 15:38
 */
@RestController
public class TestController {

    @GetMapping(value = "/test")
    public User getTest(@RequestParam(name = "username", required = true) String username,
                        @RequestParam(name = "password", required = true) String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        System.out.println(user);
        return user;
    }

    @PostMapping(value = "/test")
    public User postTest(@Validated @RequestBody User user) {
        System.out.println(user);
        return user;
    }
}
