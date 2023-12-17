package com.akhazov.grpcuserservice.repository;

import com.akhazov.grpcuserservice.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}