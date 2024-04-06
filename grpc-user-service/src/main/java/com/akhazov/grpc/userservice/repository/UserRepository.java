package com.akhazov.grpc.userservice.repository;

import com.akhazov.grpc.userservice.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}