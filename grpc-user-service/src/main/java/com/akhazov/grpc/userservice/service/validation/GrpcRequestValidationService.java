package com.akhazov.grpc.userservice.service.validation;

import build.buf.protovalidate.ValidationResult;
import build.buf.protovalidate.Validator;
import com.akhazov.grpc.userservice.error.Error;
import com.akhazov.grpc.userservice.error.ServiceValidationException;
import com.google.protobuf.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GrpcRequestValidationService {

    private final Validator validator;

    @SneakyThrows
    public void validateRequest(Message message) {
        ValidationResult validated = validator.validate(message);
        if (!validated.isSuccess()) {
            log.warn("{}", validated);
            throw new ServiceValidationException(Error.REQUEST_VALIDATION_ERROR, validated.getViolations());
        }
    }

}
