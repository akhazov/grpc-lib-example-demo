package com.akhazov.grpc.userservice.error;


import io.grpc.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Error {

    USER_NOT_FOUND("CS-0001", "Клиент с идентификатором %s не найден", Status.NOT_FOUND),
    REQUEST_VALIDATION_ERROR("CS-0010", "Ошибка валидации запроса", Status.INVALID_ARGUMENT);

    private final String code;
    private final String description;
    private final Status status;

}
