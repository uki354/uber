package com.uki.uber.user;

import com.uki.uber.anotation.ValidImage;
import com.uki.uber.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewUser(UserModel user,
                                           @ValidImage
                                           @RequestParam(name = "image",required = false) MultipartFile file){
        userService.createNewUser(user, file);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/update/image")
    public ResponseEntity<?> updateUserImage(String username, @RequestParam("image") MultipartFile image){
        userService.updateUserImage(username, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
