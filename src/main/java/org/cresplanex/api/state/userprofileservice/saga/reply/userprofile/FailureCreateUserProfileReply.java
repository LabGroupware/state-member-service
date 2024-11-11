package org.cresplanex.api.state.userprofileservice.saga.reply.userprofile;

import org.cresplanex.api.state.userprofileservice.saga.reply.BaseFailureReply;

import java.util.Map;

public class FailureCreateUserProfileReply extends BaseFailureReply {
    public static final String TYPE = "org.cresplanex.nova.service.userprofile.saga.reply.userprofile.FailureCreateUserProfileReply";

    public FailureCreateUserProfileReply(Map<String, Object> data, String code, String caption, String timestamp) {
        super(data, code, caption, timestamp);
    }

    public FailureCreateUserProfileReply() {
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
