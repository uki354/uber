package com.uki.uber.user.service;

import com.uki.uber.user.UserModel;
import com.uki.uber.user.dao.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



@Service
@AllArgsConstructor
public class UserServiceImpl implements  UserService {

    private final UserRepository userRepository;
    public static final String UPLOAD_DIR_PATH = "src/main/resources/static/user_uploads/";
    public static final String UPLOAD_DIR = "user_uploads/";
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void createNewUser(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveUser(user);
    }

    @Override
    @Transactional
    public void updateUserImage(String username, MultipartFile image) {
        userRepository.updateUserImage(username,UPLOAD_DIR + username + ".png");
        uploadUserImage(username,image);
    }

    private void uploadUserImage(String user, MultipartFile image){
        try {
            File file = new File(UPLOAD_DIR_PATH + user + ".png");
            BufferedImage img = ImageIO.read(image.getInputStream());
            ImageIO.write(img, "png", file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
