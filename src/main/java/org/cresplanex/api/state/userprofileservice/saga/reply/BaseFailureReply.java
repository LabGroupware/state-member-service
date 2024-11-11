package org.cresplanex.api.state.userprofileservice.saga.reply;

import lombok.Getter;

@Getter
public abstract class BaseFailureReply {
    private Object data;
    private String code;
    private String caption;
    private String timestamp;

    public BaseFailureReply(Object data, String code, String caption, String timestamp) {
        this.data = data;
        this.code = code;
        this.caption = caption;
        this.timestamp = timestamp;
    }

    public BaseFailureReply() {
    }

    abstract public String getType();
}
