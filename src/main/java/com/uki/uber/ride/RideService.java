package com.uki.uber.ride;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uki.uber.geometry.GeoLocation;
import com.uki.uber.geometry.geocoding.ReverseGeocodingResponse;

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
    private final ObjectMapper mapper;
    @Value("${geocoding.key}")
    public String KEY;
    public final String LIMIT = "&limit=1";
    public String GEOCODING_URL;

    @PostConstruct
    public void initUrl(){
       GEOCODING_URL = "http://api.positionstack.com/v1/reverse?access_key=" +  KEY  + "&query=";
    }



    public List<RideDto> findRidesByUser(String username){
        List<RideDto> rideDtos = rideRepository.findAllRidesByUser(username);
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
}
