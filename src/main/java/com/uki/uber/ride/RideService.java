package com.uki.uber.ride;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uki.uber.driver.DriverModel;
import com.uki.uber.driver.DriverRepository;
import com.uki.uber.geometry.GeoLocation;
import com.uki.uber.geometry.geocoding.ReverseGeocodingResponse;

import com.uki.uber.user.UserModel;
import com.uki.uber.user.dao.UserRepository;
import com.uki.uber.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ObjectMapper mapper;
    @Value("${geocoding.key}")
    public String KEY;
    public final String LIMIT = "&limit=1";
    public String GEOCODING_URL;

    @PostConstruct
    public void initUrl(){
       GEOCODING_URL = "http://api.positionstack.com/v1/reverse?access_key=" +  KEY  + "&query=";
    }



    public List<RideDto> findRidesByUser(String username, int pageNumber){
        List<RideDto> rideDtos = rideRepository.findAllRidesByUser(username, pageNumber);
        for (RideDto ride:
             rideDtos) {
            ride.setLocationName(doReverseGeocoding(ride.getLocation()));
            ride.setDestinationName(doReverseGeocoding(ride.getDestination()));
        }
        return rideDtos;
    }

    public String doReverseGeocoding(GeoLocation geoLocation){
        RestTemplate restTemplate = new RestTemplate();
        String latitude  = geoLocation.getLatitude();
        String longitude = geoLocation.getLongitude();

        String result = restTemplate.getForObject(GEOCODING_URL + latitude + "," + longitude + LIMIT,String.class);
        try{
            ReverseGeocodingResponse  results = mapper.readValue(result, ReverseGeocodingResponse.class);
            return results.getData()[0].getLabel();
        }catch (Exception e){
            throw new RuntimeException("Error while doing reverse geocoding");
        }




    }

    public void saveRide(GeoLocation location, GeoLocation destination, long driverId){
        DriverModel driver = driverRepository.getById(driverId);
        UserModel user = userRepository.findUserAndFetchRoles(userService.getLoggedInUser());
        RideModel ride = RideModel.builder()
                .location(location)
                .destination(destination)
                .driver(driver)
                .user(user).build();

        rideRepository.save(ride);
    }

}
