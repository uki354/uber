package com.uki.uber.geometry;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoLocation {

    private String latitude;
    private String longitude;

    public GeoLocation(double latitude, double longitude) {
        String lat = String.valueOf(latitude);
        String lng = String.valueOf(longitude);
        this.latitude = lat;
        this.longitude = lng;
    }
}
