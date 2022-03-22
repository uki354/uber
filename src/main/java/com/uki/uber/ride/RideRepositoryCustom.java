package com.uki.uber.ride;

import java.util.List;

public interface RideRepositoryCustom {
    List<RideDto> findAllRidesByUser(String username, int pageNumber);
}
