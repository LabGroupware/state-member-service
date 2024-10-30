package org.cresplanex.api.state.userprofileservice.handler;

import org.cresplanex.api.state.userprofileservice.entity.User;
import org.cresplanex.api.state.userprofileservice.mapper.proto.UserProtoMapper;
import org.cresplanex.api.state.userprofileservice.service.UserService;

import build.buf.gen.user.v1.GetUserRequest;
import build.buf.gen.user.v1.GetUserResponse;
import build.buf.gen.user.v1.UserServiceGrpc.UserServiceImplBase;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class UserServiceHandler extends UserServiceImplBase {

    private final UserService userService;

    @Override
    public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        User user = userService.getUser(request.getUserId());

        responseObserver.onNext(UserProtoMapper.toGetUserResponse(user));
        responseObserver.onCompleted();
    }
}
