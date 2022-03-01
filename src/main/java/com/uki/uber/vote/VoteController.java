package com.uki.uber.vote;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/vote")
    public void vote(@RequestParam String username,
                     @RequestParam long driver,
                     @RequestParam byte score){

        voteService.vote(score,driver,username);

    }
}
