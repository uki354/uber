package com.uki.uber.directions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class EndLocation {

    private double lat;
    private double lng;
}
