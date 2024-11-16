package org.cresplanex.api.state.userprofileservice.handler;

import build.buf.gen.userprofile.v1.*;
import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.cresplanex.api.state.userprofileservice.mapper.proto.ProtoMapper;
import org.cresplanex.api.state.userprofileservice.service.UserProfileService;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@RequiredArgsConstructor
@GrpcService
public class UserProfileServiceHandler extends UserProfileServiceGrpc.UserProfileServiceImplBase {

    private final UserProfileService userProfileService;

    @Override
    public void findUserProfile(FindUserProfileRequest request, StreamObserver<FindUserProfileResponse> responseObserver) {
        UserProfileEntity userProfile = userProfileService.findById(request.getUserProfileId());

        UserProfile userProfileProto = ProtoMapper.convert(userProfile);
        FindUserProfileResponse response = FindUserProfileResponse.newBuilder()
                .setUserProfile(userProfileProto)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findUserProfileByUserId(FindUserProfileByUserIdRequest request, StreamObserver<FindUserProfileByUserIdResponse> responseObserver) {
        UserProfileEntity userProfile = userProfileService.findByUserId(request.getUserId());

        UserProfile userProfileProto = ProtoMapper.convert(userProfile);
        FindUserProfileByUserIdResponse response = FindUserProfileByUserIdResponse.newBuilder()
                .setUserProfile(userProfileProto)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findUserProfileByEmail(FindUserProfileByEmailRequest request, StreamObserver<FindUserProfileByEmailResponse> responseObserver) {
        UserProfileEntity userProfile = userProfileService.findByEmail(request.getEmail());

        UserProfile userProfileProto = ProtoMapper.convert(userProfile);
        FindUserProfileByEmailResponse response = FindUserProfileByEmailResponse.newBuilder()
                .setUserProfile(userProfileProto)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // TODO: pagination + with count
    @Override
    public void getUserProfiles(GetUserProfilesRequest request, StreamObserver<GetUserProfilesResponse> responseObserver) {
        List<UserProfileEntity> userProfiles = userProfileService.get();

        List<UserProfile> userProfileProtos = userProfiles.stream()
                .map(ProtoMapper::convert).toList();
        GetUserProfilesResponse response = GetUserProfilesResponse.newBuilder()
                .addAllUserProfiles(userProfileProtos)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
