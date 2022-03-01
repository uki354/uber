package com.uki.uber.ride;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uki.uber.directions.DirectionsService;
import com.uki.uber.directions.DriverLocation;
import com.uki.uber.directions.model.DirectionsResponse;
import com.uki.uber.geometry.GeoLocation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ride")
public class RideController {

    private final DirectionsService directionsService;
    private final ObjectMapper mapper;


    @GetMapping("/direction")
    public DirectionsResponse findDirection(@RequestBody String body) throws Exception{
        JsonNode node = mapper.readTree(body);
        GeoLocation origin = mapper.convertValue(node.get("origin"), GeoLocation.class);
        GeoLocation destination = mapper.convertValue(node.get("destination"), GeoLocation.class);

        return directionsService.findDirection(origin,destination);
    }

    @GetMapping("/driver")
    public DriverLocation searchForADriver(@RequestParam(name = "l") GeoLocation location){
        return directionsService.searchForDriver(location);
    }



}
