package org.cresplanex.api.state.userprofileservice.event.model.userprofile;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.cresplanex.api.state.userprofileservice.event.model.ProcessedJob;

public class ProcessedCreateUserProfile extends ProcessedJob implements UserProfileDomainEvent {
    public final static String TYPE = "org.cresplanex.nova.service.userprofile.event.UserProfile.CreateProcessed";

    public ProcessedCreateUserProfile(
            String jobId,
            Object data,
            String actionCode,
            String internalCode,
            String internalCaption,
            String timestamp
    ) {
        super(jobId, data, actionCode, internalCode, internalCaption, timestamp);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
