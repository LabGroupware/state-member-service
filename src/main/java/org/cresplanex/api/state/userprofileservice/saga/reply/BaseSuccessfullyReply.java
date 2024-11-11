package org.cresplanex.api.state.userprofileservice.saga.reply;

import lombok.Getter;

@Getter
public abstract class BaseSuccessfullyReply<Data> {
    private final Data data;
    private final String code;
    private final String caption;
    private final String timestamp;

    public BaseSuccessfullyReply(Data data, String code, String caption, String timestamp) {
        this.data = data;
        this.code = code;
        this.caption = caption;
        this.timestamp = timestamp;
    }

    abstract public String getType();
}
