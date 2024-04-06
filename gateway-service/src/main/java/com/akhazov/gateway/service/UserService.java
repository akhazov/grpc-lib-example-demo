package com.akhazov.gateway.service;

import com.akhazov.gateway.model.CreateUserRequest;
import com.akhazov.gateway.model.CreateUserResponse;
import com.akhazov.gateway.model.UserInfo;
import com.akhazov.gateway.model.UsersResponse;
import com.akhazov.grpc.clientservice.*;
import com.akhazov.grpc.usercacheserver.CacheUserServiceGrpc;
import com.akhazov.grpc.usercacheserver.GetCachedUserByIdGrpcRequest;
import com.google.protobuf.Empty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @GrpcClient("cache-user-service")
    private CacheUserServiceGrpc.CacheUserServiceBlockingStub cacheUserGrpcService;

    @GrpcClient("grpc-user-service")
    private UserServiceGrpc.UserServiceBlockingStub userGrpcService;

    public UserInfo getUserById(Integer userId) {
        var grpcRequest = GetCachedUserByIdGrpcRequest.newBuilder()
                .setUserId(userId)
                .build();
        var userById = cacheUserGrpcService.getUserById(grpcRequest);
        return new UserInfo(userById.getUserId(), userById.getFirstName());
    }

    public void deleteUserById(Integer userId) {
        DeleteUserByIdGrpcRequest request = DeleteUserByIdGrpcRequest
                .newBuilder()
                .setUserId(userId)
                .build();

        userGrpcService.deleteUserById(request);
    }

    public CreateUserResponse createUser(CreateUserRequest createRequest) {

        CreateUserGrpcRequest prepareRequest = CreateUserGrpcRequest.newBuilder()
                .setFirstName(createRequest.firstName())
                .setLastName(createRequest.lastName())
                .build();

        CreateUserGrpcResponse response = userGrpcService.createUser(prepareRequest);

        return new CreateUserResponse(response.getUserId());
    }

    public UsersResponse getUsers() {
        GetUsersGrpcResponse users = userGrpcService.getUsers(Empty.getDefaultInstance());

        List<UsersResponse.User> userList = users.getUsersList().stream()
                .map(it -> new UsersResponse.User(
                        it.getUserId(),
                        it.getFirstName(),
                        it.getLastName(),
                        it.getUserStatus().name(),
                        it.getCreationDate()))
                .toList();

        return new UsersResponse(userList);
    }
}
