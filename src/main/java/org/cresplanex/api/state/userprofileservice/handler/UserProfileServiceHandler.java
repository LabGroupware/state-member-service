package org.cresplanex.api.state.userprofileservice.handler;

import build.buf.gen.cresplanex.nova.v1.Count;
import build.buf.gen.cresplanex.nova.v1.SortOrder;
import build.buf.gen.team.v1.CreateTeamResponse;
import build.buf.gen.userprofile.v1.*;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.api.state.common.entity.ListEntityWithCount;
import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.cresplanex.api.state.userprofileservice.enums.UserProfileSortType;
import org.cresplanex.api.state.userprofileservice.mapper.proto.ProtoMapper;
import org.cresplanex.api.state.userprofileservice.service.UserProfileService;
import org.cresplanex.api.state.common.enums.PaginationType;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@Slf4j
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

    @Override
    public void getUserProfiles(GetUserProfilesRequest request, StreamObserver<GetUserProfilesResponse> responseObserver) {
        UserProfileSortType sortType = switch (request.getSort().getOrderField()) {
            case USER_PROFILE_ORDER_FIELD_NAME -> (request.getSort().getOrder() == SortOrder.SORT_ORDER_ASC) ?
                    UserProfileSortType.NAME_ASC : UserProfileSortType.NAME_DESC;
            default -> (request.getSort().getOrder() == SortOrder.SORT_ORDER_ASC) ?
                    UserProfileSortType.CREATED_AT_ASC : UserProfileSortType.CREATED_AT_DESC;
        };
        PaginationType paginationType;
        switch (request.getPagination().getType()) {
            case PAGINATION_TYPE_CURSOR -> paginationType = PaginationType.CURSOR;
            case PAGINATION_TYPE_OFFSET -> paginationType = PaginationType.OFFSET;
            default -> paginationType = PaginationType.NONE;
        }

        ListEntityWithCount<UserProfileEntity> userProfiles = userProfileService.get(
                paginationType, request.getPagination().getLimit(), request.getPagination().getOffset(),
                request.getPagination().getCursor(), sortType, request.getWithCount());

        List<UserProfile> userProfileProtos = userProfiles.getData().stream()
                .map(ProtoMapper::convert).toList();
        GetUserProfilesResponse response = GetUserProfilesResponse.newBuilder()
                .addAllUserProfiles(userProfileProtos)
                .setCount(
                        Count.newBuilder().setIsValid(request.getWithCount())
                                .setCount(userProfiles.getCount()).build()
                )
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPluralUserProfiles(GetPluralUserProfilesRequest request, StreamObserver<GetPluralUserProfilesResponse> responseObserver) {
        UserProfileSortType sortType = switch (request.getSort().getOrderField()) {
            case USER_PROFILE_ORDER_FIELD_NAME -> (request.getSort().getOrder() == SortOrder.SORT_ORDER_ASC) ?
                    UserProfileSortType.NAME_ASC : UserProfileSortType.NAME_DESC;
            default -> (request.getSort().getOrder() == SortOrder.SORT_ORDER_ASC) ?
                    UserProfileSortType.CREATED_AT_ASC : UserProfileSortType.CREATED_AT_DESC;
        };
        List<UserProfile> userProfileProtos = this.userProfileService.getByUserProfileIds(
                request.getUserProfileIdsList(), sortType).stream()
                .map(ProtoMapper::convert).toList();
        GetPluralUserProfilesResponse response = GetPluralUserProfilesResponse.newBuilder()
                .addAllUserProfiles(userProfileProtos)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPluralUserProfilesByUserId(GetPluralUserProfilesByUserIdRequest request, StreamObserver<GetPluralUserProfilesByUserIdResponse> responseObserver) {

        UserProfileSortType sortType = switch (request.getSort().getOrderField()) {
            case USER_PROFILE_ORDER_FIELD_NAME -> (request.getSort().getOrder() == SortOrder.SORT_ORDER_ASC) ?
                    UserProfileSortType.NAME_ASC : UserProfileSortType.NAME_DESC;
            default -> (request.getSort().getOrder() == SortOrder.SORT_ORDER_ASC) ?
                    UserProfileSortType.CREATED_AT_ASC : UserProfileSortType.CREATED_AT_DESC;
        };
        List<UserProfile> userProfileProtos = this.userProfileService.getByUserIds(
                        request.getUserIdsList(), sortType).stream()
                .map(ProtoMapper::convert).toList();
        GetPluralUserProfilesByUserIdResponse response = GetPluralUserProfilesByUserIdResponse.newBuilder()
                .addAllUserProfiles(userProfileProtos)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createUserProfile(CreateUserProfileRequest request, StreamObserver<CreateUserProfileResponse> responseObserver) {
        String operatorId = request.getOperatorId();
        UserProfileEntity userProfile = new UserProfileEntity();
        userProfile.setUserId(request.getUserId());
        userProfile.setEmail(request.getEmail());
        userProfile.setName(request.getName());
        userProfile.setNickname(request.getName());

        String jobId = userProfileService.beginCreate(userProfile);
        CreateUserProfileResponse response = CreateUserProfileResponse.newBuilder()
                .setJobId(jobId)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
