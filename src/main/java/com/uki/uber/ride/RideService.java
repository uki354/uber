package com.uki.uber.ride;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RideService {

    private final RideRepository rideRepository;

    public void findRidesByUser(String username){
        List<RideDto> rideDtos = rideRepository.findAllRidesByUser(username);
        for (RideDto ride:
             rideDtos) {

        }

    }
}
