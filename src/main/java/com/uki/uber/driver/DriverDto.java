package com.uki.uber.driver;


import com.uki.uber.util.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DriverDto {

    private long id;
    private String firstName;
    private String lastName;
    private Instant birthdate;
    private String imagePath;
    private String mobilePhone;
    private Gender gender;
    private long carId;


}
