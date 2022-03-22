package com.uki.uber.driver;

import com.uki.uber.directions.DirectionsService;
import lombok.AllArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final RedisTemplate<String, String> redisTemplate;


    @PostConstruct
    public void initDriversLocationsInRedis(){
        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo();
        geoOperations.add(DirectionsService.DRIVERS_KEY,new Point(44.787197,20.457273),"1");
        geoOperations.add(DirectionsService.DRIVERS_KEY,new Point(45.267136,19.833549),"2");
        geoOperations.add(DirectionsService.DRIVERS_KEY,new Point(44.01667,20.91667),"3");
        geoOperations.add(DirectionsService.DRIVERS_KEY,new Point(43.32472,21.90333),"4");
        geoOperations.add(DirectionsService.DRIVERS_KEY,new Point(44.66278,20.93),"5");
        geoOperations.add(DirectionsService.DRIVERS_KEY,new Point(44.874241,20.645199),"6");
        geoOperations.add(DirectionsService.DRIVERS_KEY,new Point(46.100376,19.667587),"7");
    }


    public DriverDto findDriverById(long driverId){
        return driverRepository.getDriverDto(driverId);
    }
}
