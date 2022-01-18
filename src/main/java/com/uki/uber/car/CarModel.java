package com.uki.uber.car;

import com.uki.uber.util.BaseModel;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "car")
public class CarModel extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(name = "image_path",nullable = false)
    private String imagePath;

    @Column(name = "chassis_number", nullable = false)
    private String chassisNumber;

    @Column(name = "kw", nullable = false)
    private int kW;

    @Column(name = "manufacture_year", columnDefinition = "DATE", nullable = false)
    private Instant manufactureYear;

    @Column(nullable = false)
    private FuelType fuelType;

    @Column(nullable = false)
    private String color;

    @Column(name = "number_of_seats", columnDefinition = "TINYINT", nullable = false)
    private byte numberOfSeats;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CarModel carModel = (CarModel) o;
        return id != null && Objects.equals(id, carModel.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
