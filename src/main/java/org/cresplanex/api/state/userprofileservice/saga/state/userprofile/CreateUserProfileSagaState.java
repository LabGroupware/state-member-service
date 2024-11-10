package org.cresplanex.api.state.userprofileservice.saga.state.userprofile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cresplanex.api.state.userprofileservice.saga.command.userprofile.CreateUserProfileCommand;
import org.cresplanex.api.state.userprofileservice.saga.command.userprofile.UndoCreateUserProfileCommand;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserProfileSagaState {

    private String userProfileId;
    private UserProfileSimplifiedDetail userProfileDetail;


    public CreateUserProfileCommand makeCreateUserProfileCommand() {
        return new CreateUserProfileCommand(
                userProfileDetail.getUserId(),
                userProfileDetail.getName(),
                userProfileDetail.getEmail(),
                userProfileDetail.getNickname()
        );
    }

    public UndoCreateUserProfileCommand makeUndoCreateUserProfileCommand() {
        return new UndoCreateUserProfileCommand(userProfileId);
    }
}
