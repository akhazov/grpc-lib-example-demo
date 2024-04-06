package com.akhazov.gateway.model;

public record CreateUserRequest(
        String firstName,
        String lastName
) {
}
