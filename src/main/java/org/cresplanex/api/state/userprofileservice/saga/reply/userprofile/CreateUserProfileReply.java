package org.cresplanex.api.state.userprofileservice.saga.reply.userprofile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.cresplanex.api.state.userprofileservice.saga.reply.BaseSuccessfullyReply;

public class CreateUserProfileReply extends BaseSuccessfullyReply<CreateUserProfileReply.Data> {
    public static final String TYPE = "org.cresplanex.nova.service.userprofile.saga.reply.userprofile.CreateUserProfileReply";

    public CreateUserProfileReply(Data data, String code, String caption, String timestamp) {
        super(data, code, caption, timestamp);
    }

    public CreateUserProfileReply() {
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Data {
        private String userProfileId;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
