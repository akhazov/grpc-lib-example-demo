package com.akhazov.grpc.userservice.service;

import com.akhazov.grpc.clientservice.*;
import com.akhazov.grpc.userservice.error.Error;
import com.akhazov.grpc.userservice.error.ServiceException;
import com.akhazov.grpc.userservice.mapping.UserMapper;
import com.akhazov.grpc.userservice.model.UserEntity;
import com.akhazov.grpc.userservice.model.UserStatus;
import com.akhazov.grpc.userservice.repository.UserRepository;
import com.google.protobuf.Empty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public CreateUserGrpcResponse createUser(CreateUserGrpcRequest request) {
        UserEntity preparedEntity = userMapper.createUserRequestToEntity(request);
        preparedEntity.setUserStatus(UserStatus.NEW);
        UserEntity savedEntity = userRepository.save(preparedEntity);
        return userMapper.entityToCreateUserResponse(savedEntity);
    }

    public GetUserByIdGrpcResponse getUserById(Integer userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(Error.USER_NOT_FOUND, userId));
        return userMapper.userEntityToGetUserByIdResponse(userEntity);
    }

    @Transactional
    public UpdateUserGrpcResponse updateUser(UpdateUserGrpcRequest request) {
        UserEntity userEntity = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ServiceException(Error.USER_NOT_FOUND, request.getUserId()));
        UserEntity preparedEntity = userMapper.updateUserEntity(request, userEntity);
        UserEntity savedEntity = userRepository.save(preparedEntity);
        return userMapper.userEntityToUpdateUserResponse(savedEntity);
    }

    public GetUsersGrpcResponse findAllUsers(Empty request) {
        List<UserEntity> users = userRepository.findAll();
        return GetUsersGrpcResponse.newBuilder()
                .addAllUsers(userMapper.userListToGetUsersResponse(users))
                .build();
    }

    @Transactional
    public void deleteUserById(Integer userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(Error.USER_NOT_FOUND, userId));
        userRepository.delete(userEntity);
    }

}
