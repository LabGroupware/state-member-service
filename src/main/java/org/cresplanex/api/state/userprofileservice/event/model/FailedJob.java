package org.cresplanex.api.state.userprofileservice.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cresplanex.api.state.userprofileservice.event.model.userprofile.UserProfileDomainEvent;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class FailedJob {

    private String jobId;
    private Object data;
    private String actionCode;
    private String internalCode;
    private String internalCaption;
    private String timestamp;
    private Object endedErrorAttributes;

    abstract public String getType();
}
