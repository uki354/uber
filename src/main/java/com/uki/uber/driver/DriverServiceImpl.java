package com.uki.uber.driver;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;


    public DriverDto findDriverById(long driverId){
        return driverRepository.getDriverDto(driverId);
    }
}
