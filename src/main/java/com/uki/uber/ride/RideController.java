package com.uki.uber.ride;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uki.uber.directions.DirectionsService;
import com.uki.uber.directions.DriverLocation;
import com.uki.uber.directions.model.DirectionsResponse;
import com.uki.uber.geometry.GeoLocation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ride")
public class RideController {

    private final DirectionsService directionsService;
    private final RideService rideService;
    private final ObjectMapper mapper;


    @PostMapping("/direction")
    public DirectionsResponse findDirection(@RequestBody String body) throws Exception{
        JsonNode node = mapper.readTree(body);
        GeoLocation origin = mapper.convertValue(node.get("origin"), GeoLocation.class);
        GeoLocation destination = mapper.convertValue(node.get("destination"), GeoLocation.class);

        return directionsService.findDirection(origin,destination);
    }

    @GetMapping("/driver")
    public DriverLocation searchForADriver(@RequestParam(name = "l") String geoLocation){
        return directionsService.searchForDriver(extractGeoLocations(geoLocation));
    }

    @GetMapping("/history")
    public List<RideDto> findUserRideHistory(@RequestParam(name = "user") String username,
                                             @RequestParam(name = "page", defaultValue = "1") int pageNumber){
        return rideService.findRidesByUser(username,pageNumber);
    }

    @GetMapping("/save")
    public void saveRide(@RequestParam(name = "l") String location,
                         @RequestParam(name = "d") String destination,
                         @RequestParam(name = "dId") long driverId){

        rideService.saveRide(extractGeoLocations(location), extractGeoLocations(destination), driverId);
    }

    private GeoLocation extractGeoLocations(String geolocation){
        String[] split = geolocation.split(",");
        return new GeoLocation(split[0], split[1]);
    }





}
