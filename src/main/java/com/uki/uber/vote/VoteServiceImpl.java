package com.uki.uber.vote;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class VoteServiceImpl  implements  VoteService{

    private final VoteRepositoryCustomImpl voteRepository;

    @Override
    @Transactional
    public void vote(byte score, long driverId, String username) {
        voteRepository.saveVote(score,driverId,username);
    }
}
