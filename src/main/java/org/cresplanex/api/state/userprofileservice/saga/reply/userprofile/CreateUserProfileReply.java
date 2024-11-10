package org.cresplanex.api.state.userprofileservice.saga.reply.userprofile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateUserProfileReply {
    public static final String TYPE = "org.cresplanex.service.userprofile.saga.reply.userprofile.CreateUserProfileReply";

    private String userProfileId;
}
