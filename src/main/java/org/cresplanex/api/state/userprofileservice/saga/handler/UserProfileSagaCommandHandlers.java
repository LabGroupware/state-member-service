package org.cresplanex.api.state.userprofileservice.saga.handler;

import lombok.RequiredArgsConstructor;
import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.cresplanex.api.state.userprofileservice.saga.LockTargetType;
import org.cresplanex.api.state.userprofileservice.saga.SagaCommandChannel;
import org.cresplanex.api.state.userprofileservice.saga.command.userprofile.CreateUserProfileCommand;
import org.cresplanex.api.state.userprofileservice.saga.command.userprofile.UndoCreateUserProfileCommand;
import org.cresplanex.api.state.userprofileservice.saga.reply.userprofile.CreateUserProfileReply;
import org.cresplanex.api.state.userprofileservice.service.UserProfileService;
import org.cresplanex.core.commands.consumer.CommandHandlers;
import org.cresplanex.core.commands.consumer.CommandMessage;
import org.cresplanex.core.messaging.common.Message;
import org.cresplanex.core.saga.participant.SagaCommandHandlersBuilder;

import static org.cresplanex.core.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static org.cresplanex.core.commands.consumer.CommandHandlerReplyBuilder.withSuccess;
import static org.cresplanex.core.saga.participant.SagaReplyMessageBuilder.withLock;

@RequiredArgsConstructor
public class UserProfileSagaCommandHandlers {

    private final UserProfileService userProfileService;

    public CommandHandlers commandHandlers() {
        return SagaCommandHandlersBuilder
                .fromChannel(SagaCommandChannel.USER_PROFILE)
                .onMessage(CreateUserProfileCommand.class,
                        CreateUserProfileCommand.TYPE,
                        this::handleCreateUserProfileCommand
                )
                .onMessage(UndoCreateUserProfileCommand.class,
                        UndoCreateUserProfileCommand.TYPE,
                        this::handleUndoCreateUserProfileCommand
                )
                .build();
    }

    private Message handleCreateUserProfileCommand(CommandMessage<CreateUserProfileCommand> cmd) {
        try {
            CreateUserProfileCommand command = cmd.getCommand();
            UserProfileEntity userProfile = new UserProfileEntity();
            userProfile.setUserId(command.getUserId());
            userProfile.setName(command.getName());
            userProfile.setEmail(command.getEmail());
            userProfile.setNickname(command.getNickname());
            userProfile = userProfileService.create(userProfile);

            return withLock(LockTargetType.USER_PROFILE, userProfile.getUserProfileId())
                    .withSuccess(new CreateUserProfileReply(userProfile.getUserProfileId()), CreateUserProfileReply.TYPE);
        } catch (Exception e) {
            return withFailure();
        }
    }

    private Message handleUndoCreateUserProfileCommand(CommandMessage<UndoCreateUserProfileCommand> cmd) {
        try {
            UndoCreateUserProfileCommand command = cmd.getCommand();
            String userProfileId = command.getUserProfileId();
            userProfileService.undoCreate(userProfileId);
            return withSuccess();
        } catch (Exception e) {
            return withFailure();
        }
    }
}
