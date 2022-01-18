package com.uki.uber.user.dao;

import com.uki.uber.user.UserModel;
import org.springframework.stereotype.Repository;



import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    // password encoder


    @Override
    public void saveUser(UserModel user) {
        em.persist(user);
        em.flush();
    }





}
