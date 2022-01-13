package com.uki.uber.ride;

import com.uki.uber.driver.DriverModel;
import com.uki.uber.geometry.GeoLocation;
import com.uki.uber.geometry.GeoLocationConverter;
import com.uki.uber.user.UserModel;
import com.uki.uber.util.BaseModel;
import com.uki.uber.vote.VoteModel;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "uber_ride")
public class RideModel extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uber_ride_id")
    private Long id;

    @Column(name = "user_location", nullable = false)
    @Convert(converter = GeoLocationConverter.class)
    private GeoLocation location;

    @Column(name = "user_destination", nullable = false)
    @Convert(converter = GeoLocationConverter.class)
    private GeoLocation destination;

    @Column(name = "driver_arrival_at", columnDefinition = "TIMESTAMP")
    private Instant driverArrivalAt;

    @Column(name = "finished_at", columnDefinition = "TIMESTAMP")
    private Instant finishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private DriverModel driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private UserModel user;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private VoteModel vote;
    private double duration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RideModel rideModel = (RideModel) o;
        return id != null && Objects.equals(id, rideModel.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
