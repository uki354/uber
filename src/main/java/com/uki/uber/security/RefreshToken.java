package com.uki.uber.security;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "refresh_token", indexes = @Index(columnList = "token",unique = true))
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String token;
    @Column(name = "is_valid", columnDefinition = "TINYINT(1)")
    private boolean isValid;

}
