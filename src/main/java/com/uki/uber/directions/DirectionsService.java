package com.uki.uber.directions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uki.uber.directions.model.DirectionsResponse;
import com.uki.uber.geometry.GeoLocation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class DirectionsService {

    private final RedisTemplate<String, String> redisTemplate;
    public static final String URL = "https://maps.googleapis.com/maps/api/directions/json?";
    public static final String DRIVERS_KEY = "drivers";
    public static final int SEARCH_RADIUS = 50;


    @Value("${maps.key}")
    private String key;

    public DirectionsResponse findDirection(GeoLocation origin, GeoLocation destination){
        StringBuilder sb = new StringBuilder();
        sb.append(URL).append("origin=").append(origin.getLatitude()).append(",").append(origin.getLongitude())
                        .append("&destination=").append(destination.getLatitude()).append(",").append(destination.getLongitude())
                        .append("&key=").append(key);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(sb.toString(),String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            return  mapper.readValue(response, DirectionsResponse.class);
        }catch (Exception e){
            throw new RuntimeException("Error parsing directions");
        }
    }

    public DriverLocation searchForDriver(String lat, String lng){
        double latitude  = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lng);

        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> drivers = findDriverInRadius(latitude, longitude, SEARCH_RADIUS);

        if(drivers.size() < 1){
            GeoLocation driversGeoLocation = generateGeolocation(latitude, longitude, SEARCH_RADIUS);
            List<GeoResult<RedisGeoCommands.GeoLocation<String>>> nearestDriver = findNearestDriver(latitude, longitude);

            String driver  = nearestDriver.get(0).getContent().getName();
            updateDriversLocation(driver,latitude, longitude);

            return new DriverLocation(driver, driversGeoLocation);
         }else{
            GeoResult<RedisGeoCommands.GeoLocation<String>> geoLocationGeoResult = drivers.get(0);
            RedisGeoCommands.GeoLocation<String> content = geoLocationGeoResult.getContent();
            GeoLocation driversGeoLocation = new GeoLocation(content.getPoint().getX(),content.getPoint().getY());


           return new DriverLocation(content.getName(), driversGeoLocation);
        }
    }

    public DriverLocation searchForDriver(GeoLocation geoLocation){
        return searchForDriver(geoLocation.getLatitude(), geoLocation.getLongitude());
    }

    public GeoLocation generateGeolocation(double lat, double lng, int radius){
        int randomPointInRadius = ThreadLocalRandom.current().nextInt(-radius/2, radius/2);
        double kmToDegree = randomPointInRadius * 0.0089;

        double latitude = lat + kmToDegree;
        double longitude = lng + kmToDegree / Math.cos(lng) * 0.018;

        return new GeoLocation(latitude,longitude);
    }

    public List<GeoResult<RedisGeoCommands.GeoLocation<String>>> findNearestDriver(double lat, double lng){
        return findDriverInRadius(lat,lng,Integer.MAX_VALUE);
    }

    public List<GeoResult<RedisGeoCommands.GeoLocation<String>>>  findDriverInRadius(double lat, double lng, int radius){
        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo();
        GeoReference<String> geoReference = new GeoReference.GeoCoordinateReference<>(lat, lng);
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        RedisGeoCommands.GeoSearchCommandArgs args =
                RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs().sortAscending().limit(1).includeCoordinates();
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOperations.search(DRIVERS_KEY, geoReference, distance, args);
        assert results != null;
        return results.getContent();

    }

    public void updateDriversLocation(String driver, double lat, double lng){
        Point point = new Point(lat,lng);
        redisTemplate.opsForGeo().add(DRIVERS_KEY, point, driver);
    }


}
