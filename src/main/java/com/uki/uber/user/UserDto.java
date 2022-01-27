package com.uki.uber.user;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.Set;


@Data
@AllArgsConstructor
public class UserDto {

    private String username;
    private String firstName;
    private String lastName;
    private Set<String> roles;

}
