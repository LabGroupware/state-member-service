package org.cresplanex.api.state.userprofileservice.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.cresplanex.api.state.userprofileservice.event.model.userprofile.UserProfileDomainEvent;

@Data
@AllArgsConstructor
public abstract class SuccessfullyJob {

    private final String jobId;
    private final Object endedData;

    abstract public String getType();
}
