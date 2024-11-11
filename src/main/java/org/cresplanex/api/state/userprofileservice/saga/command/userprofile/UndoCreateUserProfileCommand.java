package org.cresplanex.api.state.userprofileservice.saga.command.userprofile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UndoCreateUserProfileCommand extends UserProfileSagaCommand {
    public static final String TYPE = "org.cresplanex.nova.service.userprofile.saga.command.userprofile.UndoCreateUserProfileCommand";

    private final String userProfileId;
}
