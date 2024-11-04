package org.cresplanex.api.state.userprofileservice.event.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreated implements UserDomainEvent {

    private String userId;
    private String name;
    private String email;
}
