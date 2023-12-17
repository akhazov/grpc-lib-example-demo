package com.akhazov.grpcuserservice.error;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private final Error error;

    public ServiceException(Error error, Object... args) {
        super(error.getDescription().formatted(args));
        this.error=error;
    }

    public ServiceException(Error error) {
        this.error = error;
    }

}
