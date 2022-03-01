package com.uki.uber.vote;

public interface VoteService {

    void vote(byte score, long driverId, String username);

}
