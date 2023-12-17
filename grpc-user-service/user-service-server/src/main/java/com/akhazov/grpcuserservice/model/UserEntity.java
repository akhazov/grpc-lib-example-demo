package com.akhazov.grpcuserservice.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_status")
    @Enumerated(value = EnumType.ORDINAL)
    private UserStatus userStatus;

    @Column(name = "creation_date")
    @CreationTimestamp
    private Timestamp creationDate;

}
