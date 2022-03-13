package com.uki.uber.ride;

import com.uki.uber.geometry.GeoLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RideDto {

    private GeoLocation location;
    private GeoLocation destination;
    private long driverId;
    private Byte score;
    private double duration;
    private String locationName;
    private String destinationName;

    public RideDto(GeoLocation location, GeoLocation destination, long driverId, Byte score, double duration){
        this.location = location;
        this.destination = destination;
        this.driverId = driverId;
        this.score = score;
        this.duration = duration;
    }


    public RideDto(String locationName, String destinationName, long driverId, byte score, double duration){
        this.locationName = locationName;
        this.destinationName = destinationName;
        this.driverId = driverId;
        this.score = score;
        this.duration = duration;
    }

}
