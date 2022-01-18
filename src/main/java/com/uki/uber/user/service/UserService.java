package com.uki.uber.user.service;

import com.uki.uber.user.UserModel;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void createNewUser(UserModel user, MultipartFile image);
    void updateUserImage(String username, MultipartFile image);
}
