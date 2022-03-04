package com.uki.uber.driver;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<DriverModel, Long>, DriverRepositoryCustom {
}
