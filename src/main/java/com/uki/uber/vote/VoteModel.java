package com.uki.uber.vote;



import com.uki.uber.driver.DriverModel;
import com.uki.uber.user.UserModel;
import com.uki.uber.util.BaseModel;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_vote")
@Builder
public class VoteModel extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private UserModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private DriverModel driver;

    @Column(columnDefinition = "TINYINT")
    private byte score;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VoteModel voteModel = (VoteModel) o;
        return id != null && Objects.equals(id, voteModel.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
