package com.uki.uber;


import com.uki.uber.user.UserModel;
import com.uki.uber.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class TestController {

    private final UserService userService;

    @PostMapping("/user")
    public String createUser(UserModel user, @RequestParam("image") MultipartFile image){
        userService.createNewUser(user,image);
        return "user created";
    }
}
