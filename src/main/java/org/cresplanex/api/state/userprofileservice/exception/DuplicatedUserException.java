package org.cresplanex.api.state.userprofileservice.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DuplicatedUserException extends RuntimeException {

    private final List<String> userIds;

    public DuplicatedUserException(List<String> userIds) {
        super("Duplicated users: " + userIds.stream().reduce((a, b) -> a + ", " + b).orElse(""));
        this.userIds = userIds;
    }
}
