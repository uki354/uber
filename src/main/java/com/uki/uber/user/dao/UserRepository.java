package com.uki.uber.user.dao;

import com.uki.uber.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository  extends JpaRepository<UserModel, Long>, UserRepositoryCustom {

    @Query(value = "UPDATE uber_user SET imagePath = :imagePath WHERE firstName = :username")
    @Modifying
    void updateUserImage(String username, String imagePath);
}
