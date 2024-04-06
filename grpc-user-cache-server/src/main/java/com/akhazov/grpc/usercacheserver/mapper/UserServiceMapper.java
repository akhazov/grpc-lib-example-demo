package com.akhazov.grpc.usercacheserver.mapper;

import com.akhazov.grpc.clientservice.GetUserByIdGrpcResponse;
import com.akhazov.grpc.usercacheserver.GetCachedUserByIdGrpcResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserServiceMapper {

    @Mapping(target = "allFields", ignore = true)
    GetCachedUserByIdGrpcResponse getUserToCachedResponse(GetUserByIdGrpcResponse response);
}
