package com.akhazov.gateway.model;


import java.util.List;

public record UsersResponse (
        List<User> users
){
    public record User(
            Integer userId,
            String firstName,
            String lastName,
            String userStatus,
            String creationDate
    ) {
            }
}

