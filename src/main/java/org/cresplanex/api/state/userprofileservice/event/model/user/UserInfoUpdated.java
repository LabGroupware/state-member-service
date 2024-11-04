package org.cresplanex.api.state.userprofileservice.event.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoUpdated implements UserDomainEvent {

    private String userId;
    private String name;
    private String email;
}
