package com.uki.uber.driver;


import com.uki.uber.util.Gender;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;


import java.time.Instant;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class DriverRepositoryCustomImpl implements DriverRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public DriverDto getDriverDto(long driverId) {

        Stream<Tuple> tupleStream = em.createQuery(
                "select d.id as id, d.firstName as name, d.lastName as lastName, d.birthdate as birthdate," +
                        " d.imagePath as imagePath, d.mobilePhone as mobilePhone, d.gender as gender, dc.id as car" +
                        " from DriverModel d left join d.driverCarModels dc " +
                        "where d.id = " + driverId  + " and dc.isDeleted = false " , Tuple.class).getResultStream();

        Map<Long, DriverDto> driverDtoMap = new LinkedHashMap<>();
        List<DriverDto> driverDtos = tupleStream
                .map(tuple -> driverDtoMap.computeIfAbsent(tuple.get("id", Long.class),
                        id -> DriverDto.builder().firstName(tuple.get("name", String.class))
                                .lastName(tuple.get("lastName", String.class))
                                .birthdate(tuple.get("birthdate", Instant.class))
                                .imagePath(tuple.get("imagePath", String.class))
                                .mobilePhone(tuple.get("mobilePhone", String.class))
                                .gender(tuple.get("gender", Gender.class))
                                .carId(tuple.get("car", Long.class))
                                .id(tuple.get("id", Long.class))
                                .build()
                        )).distinct().collect(Collectors.toList());

        return driverDtos.stream().findFirst().orElseThrow(()-> new RuntimeException("Driver not found with id " + driverId));

    }
}
