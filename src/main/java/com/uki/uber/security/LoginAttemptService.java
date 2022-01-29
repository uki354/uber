package com.uki.uber.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    public static final int MAX_ATTEMPT = 10;
    private LoadingCache<String, Integer> attemptCache;

    @PostConstruct
    private void generateCache(){
        attemptCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String s) throws Exception {
                return 0;
            }
        });
    }

    public void loginSucceeded(String key){
        attemptCache.invalidate(key);
    }

    public void loginFailed(String key){
        int attempts = 0;
        try{
            attempts = attemptCache.get(key);
        }catch (ExecutionException ignored){
        }
        attempts++;
        attemptCache.put(key,attempts);
    }

    public boolean isBlocked(String key){
        try{
            return attemptCache.get(key) >= MAX_ATTEMPT;
        }catch (ExecutionException e){
            return false;
        }
    }


}
