package org.cresplanex.api.state.userprofileservice.event.model.userprofile;

import org.cresplanex.api.state.userprofileservice.event.model.BeginJob;

import java.util.List;

public class BeginCreateUserProfile extends BeginJob implements UserProfileDomainEvent{
    public static final String TYPE = "org.cresplanex.nova.service.userprofile.event.UserProfile.CreateBegin";

    public BeginCreateUserProfile(String jobId, List<String> toActionCodes) {
        super(jobId, toActionCodes);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
