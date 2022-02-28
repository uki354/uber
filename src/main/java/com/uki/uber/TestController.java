package com.uki.uber;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uki.uber.directions.DirectionsService;
import com.uki.uber.directions.DriverLocation;
import com.uki.uber.directions.model.DirectionsResponse;
import com.uki.uber.geometry.GeoLocation;
import com.uki.uber.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;




@RestController
@AllArgsConstructor
public class TestController {

    private final UserService userService;
    private final RedisTemplate<String, String> redisTemplate;
    private final DirectionsService directionsService;

    @GetMapping("/user")
    public String protectedResource(){
        return "protected resource";
    }

    @GetMapping("/test")
    public DirectionsResponse testing(@RequestBody String body) throws  Exception{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        GeoLocation origin = mapper.convertValue(node.get("origin"), GeoLocation.class);
        GeoLocation destination = mapper.convertValue(node.get("destination"), GeoLocation.class);

        System.out.println(origin);
        System.out.println(destination);
        return directionsService.findDirection(origin, destination);

    }
    @GetMapping("/test2")
    public DriverLocation test(@RequestBody GeoLocation geoLocation){
        return directionsService.searchForDriver(geoLocation);
    }


//    @PostMapping("/driver")
//    public void getDriver(@RequestBody GeoLocation geoLocation){
//        // client sends geolocation
//        // search for nearest driver in given radius
//        // if not found update one random driver to given radius
//        // return driver
//        // - use google directions api to generate map
//        // - probbaly message queue to push location updates to simulate drivers
//
//        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo();
//        Point point = new Point(Double.parseDouble(geoLocation.getLatitude()),
//                                Double.parseDouble(geoLocation.getLongitude()));
//        Distance distance = new Distance(50, Metrics.KILOMETERS);
//        Circle radius = new Circle(point, distance);
//
//        GeoReference<String> geoReference = new GeoReference.GeoCoordinateReference<>(Double.parseDouble(geoLocation.getLatitude()),
//                Double.parseDouble(geoLocation.getLongitude()));
//
//        GeoResults<RedisGeoCommands.GeoLocation<String>> drivers = geoOperations.search("drivers", radius);
//        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOperations.search("drivers", geoReference, distance, RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs().sortAscending().limit(1));
//        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> resultsContent = results.getContent();
//        if ( resultsContent.size() < 1){
//
//        }else {
//            double longitude = resultsContent.get(0).getContent().getPoint().getY();
//            double latitude  = resultsContent.get(0).getContent().getPoint().getX();
//
//            // make request to directions api get route steps
//            // send each step overtime to client to simulate driver movement
//            //
//
//
//
//        }
//
//
//        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> geoResultList = drivers.getContent();
//        if (geoResultList.size() > 0){
//            geoResultList.stream().max(Comparator.comparingDouble(geoLocationGeoResult -> geoLocationGeoResult.getContent().getPoint().getX()));
//
//        }
//
//
//        // search for driver based on location
//        Distance distance = new Distance(1000, Metrics.KILOMETERS);
//        Circle circle = new Circle(point, distance);
//
//        GeoResults<RedisGeoCommands.GeoLocation<String>> drivers = geoOperations.search("drivers", circle);
//        if(drivers.getContent().size() == 0 ){
//            System.out.println("no available drivers for that location");
//            System.out.println("generating new drivers");
//            Point point1 = new Point(45.267136, 19.833549);
//            geoOperations.add("drivers", point1, "driverTest2");
//            System.out.println("returning driver * with geo *");
//        }else{
//            List<GeoResult<RedisGeoCommands.GeoLocation<String>>> content = drivers.getContent();
//            content.forEach(System.out::println);
//        }
//
//    }


}
