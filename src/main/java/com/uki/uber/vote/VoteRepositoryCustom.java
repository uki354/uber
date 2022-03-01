package com.uki.uber.vote;

public interface VoteRepositoryCustom {

    void saveVote(byte score, long driver, String username);
}
