package com.uki.uber.security;

public interface RefreshTokenRepositoryCustom {

    RefreshToken findRefreshToken(String token);
}
