package com.akhazov.grpc.userservice.mapping;

import com.akhazov.grpc.clientservice.*;
import com.akhazov.grpc.userservice.model.UserEntity;
import com.google.protobuf.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity createUserRequestToEntity(CreateUserGrpcRequest request);

    @Mapping(target = "userId", source = "id")
    CreateUserGrpcResponse entityToCreateUserResponse(UserEntity entity);

    @Mapping(target = "userId", source = "id")
    GetUserByIdGrpcResponse userEntityToGetUserByIdResponse(UserEntity userEntity);

    UserEntity updateUserEntity(UpdateUserGrpcRequest request, @MappingTarget UserEntity userEntity);

    @Mapping(target = "userId", source = "id")
    UpdateUserGrpcResponse userEntityToUpdateUserResponse(UserEntity savedEntity);

    List<GetUsersGrpcResponse.User> userListToGetUsersResponse(List<UserEntity> users);

    @Mapping(target = "userId", source = "id")
    GetUsersGrpcResponse.User userEntityToUserResponse(UserEntity usersEntity);

    default Timestamp sqlTimestampToProtobuf(java.sql.Timestamp value) {
        return Timestamp.newBuilder()
                .setSeconds(value.getSeconds())
                .setNanos(value.getNanos())
                .build();
    }

}
