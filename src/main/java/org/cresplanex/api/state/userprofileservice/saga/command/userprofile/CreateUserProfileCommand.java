package org.cresplanex.api.state.userprofileservice.saga.command.userprofile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateUserProfileCommand extends UserProfileSagaCommand {
    public static final String TYPE = "org.cresplanex.nova.service.userprofile.saga.command.userprofile.CreateUserProfileCommand";

    private final String userId;
    private final String name;
    private final String email;
    private final String nickname;
}
