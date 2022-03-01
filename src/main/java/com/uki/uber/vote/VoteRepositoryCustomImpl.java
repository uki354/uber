package com.uki.uber.vote;

import com.uki.uber.driver.DriverModel;
import com.uki.uber.user.UserModel;
import com.uki.uber.user.UserModel_;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class VoteRepositoryCustomImpl implements VoteRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


    public void saveVote(byte score, long driver, String username){
        Session session = em.unwrap(Session.class);
        DriverModel driverRef = em.getReference(DriverModel.class, driver);
        UserModel userRef = session.byNaturalId(UserModel.class).using(UserModel_.USERNAME,username).load();

        VoteModel vote = VoteModel.builder()
                .user(userRef)
                .driver(driverRef)
                .score(score).build();

        em.persist(vote);
    }
}
