package org.cresplanex.api.state.userprofileservice.saga.reply;

import lombok.Getter;

@Getter
public abstract class BaseSuccessfullyReply<Data> {
    private Data data;
    private String code;
    private String caption;
    private String timestamp;

    public BaseSuccessfullyReply(Data data, String code, String caption, String timestamp) {
        this.data = data;
        this.code = code;
        this.caption = caption;
        this.timestamp = timestamp;
    }

    public BaseSuccessfullyReply() {
    }

    abstract public String getType();
}
