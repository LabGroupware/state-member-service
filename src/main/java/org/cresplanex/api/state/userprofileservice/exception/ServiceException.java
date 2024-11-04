package org.cresplanex.api.state.userprofileservice.exception;

import build.buf.gen.userprofile.v1.UserProfileServiceErrorCode;

public abstract class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    abstract public UserProfileServiceErrorCode getServiceErrorCode();
    abstract public String getErrorCaption();
}
