package com.uki.uber.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface RefreshTokenRepository extends RefreshTokenRepositoryCustom, JpaRepository<RefreshToken, Long> {

    @Query("update RefreshToken set isValid = false where checksumToken = :token")
    @Modifying
    @Transactional
    void invalidateRefreshToken(String token);
}
