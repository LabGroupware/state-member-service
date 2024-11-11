package org.cresplanex.api.state.userprofileservice.saga.command.userprofile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateUserProfileCommand extends UserProfileSagaCommand {
    public static final String TYPE = "org.cresplanex.nova.service.userprofile.saga.command.userprofile.CreateUserProfileCommand";

    private String userId;
    private String name;
    private String email;
    private String nickname;
}
