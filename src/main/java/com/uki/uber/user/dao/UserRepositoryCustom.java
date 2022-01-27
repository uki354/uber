package com.uki.uber.user.dao;


import com.uki.uber.user.UserModel;


public interface UserRepositoryCustom {

    void saveUser(UserModel user);
    UserModel findUserAndFetchRoles(String username);

}
