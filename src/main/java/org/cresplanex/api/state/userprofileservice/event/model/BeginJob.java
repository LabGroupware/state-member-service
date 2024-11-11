package org.cresplanex.api.state.userprofileservice.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.cresplanex.api.state.userprofileservice.event.model.userprofile.UserProfileDomainEvent;

import java.util.List;

@Data
@AllArgsConstructor
public abstract class BeginJob {

    private final String jobId;
    private final List<String> toActionCodes;

    abstract public String getType();
}
