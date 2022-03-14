package com.uki.uber.ride;

import com.uki.uber.driver.DriverModel;
import com.uki.uber.driver.DriverModel_;
import com.uki.uber.user.UserModel_;
import com.uki.uber.vote.VoteModel;
import com.uki.uber.vote.VoteModel_;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class RideRepository {

    @PersistenceContext
    private EntityManager em;

    public List<RideDto> findAllRidesByUser(String username, int pageNumber){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RideDto> cq = cb.createQuery(RideDto.class);

        Root<RideModel> root = cq.from(RideModel.class);
        Join<RideModel, DriverModel> driver = root.join(RideModel_.DRIVER);
        Join<RideModel, User> join = root.join(RideModel_.USER);
        Join<RideModel, VoteModel> vote = root.join(RideModel_.VOTE, JoinType.LEFT);

        cq.select(cb.construct(
                RideDto.class,
                root.get("location"),
                root.get("destination"),
                driver.get(DriverModel_.ID),
                vote.get(VoteModel_.SCORE),
                root.get(RideModel_.DURATION)
                ));
        cq.where(cb.equal(join.get(UserModel_.USERNAME), username));

        int pageSize = 10;
        return em.createQuery(cq).setFirstResult((pageNumber-1) * pageSize).setMaxResults(pageSize).getResultList();
    }



}
