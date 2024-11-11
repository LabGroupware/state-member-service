package org.cresplanex.api.state.userprofileservice.event.model.userprofile;

import org.cresplanex.api.state.userprofileservice.event.model.FailedJob;

public class FailedCreateUserProfile extends FailedJob implements UserProfileDomainEvent {
    public static final String TYPE = "org.cresplanex.nova.service.userprofile.event.UserProfile.CreateFailed";

    public FailedCreateUserProfile(
            String jobId,
            Object data,
            String actionCode,
            String internalCode,
            String internalCaption,
            String timestamp,
            Object endedErrorAttributes
    ) {
        super(jobId, data, actionCode, internalCode, internalCaption, timestamp, endedErrorAttributes);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
