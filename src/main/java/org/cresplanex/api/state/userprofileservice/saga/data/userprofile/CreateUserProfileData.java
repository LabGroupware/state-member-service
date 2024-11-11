package org.cresplanex.api.state.userprofileservice.saga.data.userprofile;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateUserProfileData {
    private final String userProfileId;
}
