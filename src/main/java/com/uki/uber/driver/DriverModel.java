package com.uki.uber.driver;


import com.uki.uber.util.BaseModel;
import com.uki.uber.util.Gender;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DriverModel extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(columnDefinition = "DATE", nullable = false)
    private Instant birthdate;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "mobile_phone", nullable = false)
    private String mobilePhone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
    private String JMBG;

    @Column(nullable = false)
    private Gender gender;

    @OneToMany(mappedBy = "driver",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<DriverCarModel> driverCarModels = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DriverModel that = (DriverModel) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}




