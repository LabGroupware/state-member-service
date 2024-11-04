package org.cresplanex.api.state.userprofileservice.exception;

import build.buf.gen.userprofile.v1.UserProfileServiceErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserProfileNotFoundException extends ServiceException {

    private final FindType findType;
    private final String aggregateId;

    public UserProfileNotFoundException(FindType findType, String aggregateId) {
        this(findType, aggregateId, "Model not found: " + findType.name() + " with id " + aggregateId);
    }

    public UserProfileNotFoundException(FindType findType, String aggregateId, String message) {
        super(message);
        this.findType = findType;
        this.aggregateId = aggregateId;
    }

    public UserProfileNotFoundException(FindType findType, String aggregateId, String message, Throwable cause) {
        super(message, cause);
        this.findType = findType;
        this.aggregateId = aggregateId;
    }

    public enum FindType {
        BY_ID,
        BY_EMAIL,
        BY_USER_ID
    }

    @Override
    public UserProfileServiceErrorCode getServiceErrorCode() {
        return UserProfileServiceErrorCode.USER_PROFILE_SERVICE_ERROR_CODE_USER_PROFILE_NOT_FOUND;
    }

    @Override
    public String getErrorCaption() {
        return switch (findType) {
            case BY_ID -> "User Profile not found (ID = %s)".formatted(aggregateId);
            case BY_EMAIL -> "User Profile not found (Email = %s)".formatted(aggregateId);
            case BY_USER_ID -> "User Profile not found (User ID = %s)".formatted(aggregateId);
            default -> "User Profile not found";
        };
    }
}
