package org.cresplanex.api.state.userprofileservice.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class NotFoundUserException extends RuntimeException {

    private final List<String> userIds;

    public NotFoundUserException(List<String> userIds) {
        super("Not found users: " + userIds.stream().reduce((a, b) -> a + ", " + b).orElse(""));
        this.userIds = userIds;
    }
}
