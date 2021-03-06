package com.uki.uber.user;

import com.uki.uber.anotation.ValidImage;
import com.uki.uber.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewUser(@RequestBody UserModel user){
        userService.createNewUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/update/image")
    public ResponseEntity<?> updateUserImage(String username, @RequestParam("image") MultipartFile image){
        userService.updateUserImage(username, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
