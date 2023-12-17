package com.akhazov.grpcuserservice.error;

import build.buf.validate.Violation;
import lombok.Getter;

import java.util.List;

@Getter
public class ServiceValidationException extends RuntimeException {

    private final Error error;
    private final List<Violation> violations;

    public ServiceValidationException(Error error, List<Violation> violations) {
        super(error.getDescription());
        this.violations = violations;
        this.error = error;
    }

}
