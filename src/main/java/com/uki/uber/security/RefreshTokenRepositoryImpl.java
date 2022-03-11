package com.uki.uber.security;



import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepositoryCustom {


    @PersistenceContext
    private EntityManager em;


    public RefreshToken findRefreshToken(String tokenChecksum){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RefreshToken> cq = cb.createQuery(RefreshToken.class);
        Root<RefreshToken> root = cq.from(RefreshToken.class);

        Predicate predToken = cb.equal(root.get(RefreshToken_.CHECKSUM_TOKEN),tokenChecksum);
        Predicate predValid = cb.equal(root.get(RefreshToken_.IS_VALID),true);

        cq.where(predToken, predValid);
        return em.createQuery(cq).getSingleResult();
    }





}
