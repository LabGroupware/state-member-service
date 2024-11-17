package org.cresplanex.api.state.userprofileservice.saga.state.userprofile;

import lombok.*;
import org.cresplanex.api.state.common.dto.userpreference.UserPreferenceDto;
import org.cresplanex.api.state.common.dto.userprofile.UserProfileDto;
import org.cresplanex.api.state.common.saga.command.userpreference.CreateUserPreferenceCommand;
import org.cresplanex.api.state.common.saga.command.userprofile.CreateUserProfileCommand;
import org.cresplanex.api.state.common.saga.state.SagaState;
import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.cresplanex.api.state.userprofileservice.saga.model.userprofile.CreateUserProfileSaga;

@Setter
@Getter
@NoArgsConstructor
public class CreateUserProfileSagaState
        extends SagaState<CreateUserProfileSaga.Action, UserProfileEntity> {
    private InitialData initialData;
    private UserProfileDto userProfileDto = UserProfileDto.empty();
    private UserPreferenceDto userPreferenceDto = UserPreferenceDto.empty();

    @Override
    public String getId() {
        return userProfileDto.getUserProfileId();
    }

    @Override
    public Class<UserProfileEntity> getEntityClass() {
        return UserProfileEntity.class;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InitialData {
        private String userId;
        private String name;
        private String email;
        private String nickname;
    }

    public CreateUserProfileCommand.Exec makeCreateUserProfileCommand() {
        return new CreateUserProfileCommand.Exec(
                initialData.getUserId(),
                initialData.getName(),
                initialData.getEmail(),
                initialData.getNickname()
        );
    }

    public CreateUserProfileCommand.Undo makeUndoCreateUserProfileCommand() {
        return new CreateUserProfileCommand.Undo(userProfileDto.getUserProfileId());
    }

    public CreateUserPreferenceCommand.Exec makeCreateUserPreferenceCommand() {
        return new CreateUserPreferenceCommand.Exec(initialData.getUserId());
    }

    public CreateUserPreferenceCommand.Undo makeUndoCreateUserPreferenceCommand() {
        return new CreateUserPreferenceCommand.Undo(userPreferenceDto.getUserPreferenceId());
    }
}
