package org.cresplanex.api.state.userprofileservice.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cresplanex.api.state.userprofileservice.event.model.userprofile.UserProfileDomainEvent;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class SuccessfullyJob {

    private String jobId;
    private Object endedData;

    abstract public String getType();
}
