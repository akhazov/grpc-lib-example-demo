package com.akhazov.grpcuserservice.api;

import com.akhazov.grpc.clientservice.*;
import com.akhazov.grpcuserservice.service.UserService;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class GrpcUserService extends UserServiceGrpc.UserServiceImplBase {

    private final UserService userService;

    @Override
    public void createUser(CreateUserGrpcRequest request, StreamObserver<CreateUserGrpcResponse> responseObserver) {
        log.info("Запрос на создание пользователя: " + request);
        CreateUserGrpcResponse response = userService.createUser(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserById(GetUserByIdGrpcRequest request, StreamObserver<GetUserByIdGrpcResponse> responseObserver) {
        log.info("Получение пользователя по Id: " + request.getUserId());
        GetUserByIdGrpcResponse response = userService.getUserById(request.getUserId());
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateUser(UpdateUserGrpcRequest request, StreamObserver<UpdateUserGrpcResponse> responseObserver) {
        log.info("Обновление пользователя: " + request);
        UpdateUserGrpcResponse response = userService.updateUser(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUsers(Empty request, StreamObserver<GetUsersGrpcResponse> responseObserver) {
        log.info("Получение списка пользователей.");
        GetUsersGrpcResponse response = userService.findAllUsers(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteUserById(DeleteUserByIdGrpcRequest request, StreamObserver<Empty> responseObserver) {
        log.info("Удаление пользователя по id: " + request);
        userService.deleteUserById(request.getUserId());
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
