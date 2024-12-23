package org.cresplanex.api.state.userprofileservice.exception;

import build.buf.gen.userprofile.v1.*;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@Slf4j
@GrpcAdvice
public class GrpcExceptionAdvice {

     @GrpcExceptionHandler(UserProfileNotFoundException.class)
     public Status handleUserProfileNotFoundException(UserProfileNotFoundException e) {
        UserProfileServiceUserProfileNotFoundError.Builder descriptionBuilder =
                UserProfileServiceUserProfileNotFoundError.newBuilder()
                .setMeta(buildErrorMeta(e));

        switch (e.getFindType()) {
            case BY_ID:
                descriptionBuilder
                        .setFindFieldType(UserProfileUniqueFieldType.USER_PROFILE_UNIQUE_FIELD_TYPE_USER_PROFILE_ID)
                        .setUserProfileId(e.getAggregateId());
                break;
            case BY_EMAIL:
                descriptionBuilder
                        .setFindFieldType(UserProfileUniqueFieldType.USER_PROFILE_UNIQUE_FIELD_TYPE_EMAIL)
                        .setEmail(e.getAggregateId());
                break;
            case BY_USER_ID:
                descriptionBuilder
                        .setFindFieldType(UserProfileUniqueFieldType.USER_PROFILE_UNIQUE_FIELD_TYPE_USER_ID)
                        .setUserId(e.getAggregateId());
                break;
        }

         return Status.NOT_FOUND
                 .withDescription(descriptionBuilder.build().toString())
                 .withCause(e);
     }

     private UserProfileServiceErrorMeta buildErrorMeta(ServiceException e) {
         return UserProfileServiceErrorMeta.newBuilder()
                 .setCode(e.getServiceErrorCode())
                 .setMessage(e.getErrorCaption())
                 .build();
     }

    @GrpcExceptionHandler
    public Status handleInternal(Throwable e) {
         log.error("Internal error", e);

        String message = e.getMessage() != null ? e.getMessage() : "Unknown error occurred";

         UserProfileServiceInternalError.Builder descriptionBuilder =
                 UserProfileServiceInternalError.newBuilder()
                         .setMeta(UserProfileServiceErrorMeta.newBuilder()
                                 .setCode(UserProfileServiceErrorCode.USER_PROFILE_SERVICE_ERROR_CODE_INTERNAL)
                                 .setMessage(message)
                                 .build());

         return Status.INTERNAL
                 .withDescription(descriptionBuilder.build().toString())
                 .withCause(e);
    }
}
