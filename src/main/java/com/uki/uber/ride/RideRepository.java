package com.uki.uber.ride;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RideRepository extends JpaRepository<RideModel, Long>, RideRepositoryCustom {


}
