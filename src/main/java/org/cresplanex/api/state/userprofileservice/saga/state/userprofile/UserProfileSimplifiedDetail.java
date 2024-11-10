package org.cresplanex.api.state.userprofileservice.saga.state.userprofile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileSimplifiedDetail {
    private String userId;
    private String name;
    private String email;
    private String nickname;
}
