package com.uki.uber.user.dao;

import com.uki.uber.user.UserModel;
import com.uki.uber.user.UserModel_;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveUser(UserModel user) {
        em.persist(user);
        em.flush();
    }

    @Override
    public UserModel findUserAndFetchRoles(String username) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserModel> cq = cb.createQuery(UserModel.class);

        Root<UserModel> root = cq.from(UserModel.class);
        root.fetch(UserModel_.roles, JoinType.INNER);

        Predicate predicate = cb.equal(root.get(UserModel_.username), username);
        cq.where(predicate);
        cq.distinct(true);

        TypedQuery<UserModel> q = em.createQuery(cq);
        q.setHint(QueryHints.PASS_DISTINCT_THROUGH, false);
        return q.getSingleResult();
    }
}
