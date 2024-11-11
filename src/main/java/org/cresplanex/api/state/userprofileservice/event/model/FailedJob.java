package org.cresplanex.api.state.userprofileservice.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.cresplanex.api.state.userprofileservice.event.model.userprofile.UserProfileDomainEvent;

@Data
@AllArgsConstructor
public abstract class FailedJob {

    private final String jobId;
    private final Object data;
    private final String actionCode;
    private final String internalCode;
    private final String internalCaption;
    private final String timestamp;
    private final Object endedErrorAttributes;

    abstract public String getType();
}
