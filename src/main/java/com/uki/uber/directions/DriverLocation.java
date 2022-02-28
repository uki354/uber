package com.uki.uber.directions;

import com.uki.uber.geometry.GeoLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DriverLocation {

    private String id;
    private GeoLocation currentLocation;
}
