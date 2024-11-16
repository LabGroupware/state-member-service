package org.cresplanex.api.state.userprofileservice.saga.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.api.state.common.constants.UserProfileServiceApplicationCode;
import org.cresplanex.api.state.common.saga.LockTargetType;
import org.cresplanex.api.state.common.saga.SagaCommandChannel;
import org.cresplanex.api.state.common.saga.command.userprofile.CreateUserProfileCommand;
import org.cresplanex.api.state.common.saga.reply.userprofile.CreateUserProfileReply;
import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.cresplanex.api.state.userprofileservice.mapper.dto.DtoMapper;
import org.cresplanex.api.state.userprofileservice.service.UserProfileService;
import org.cresplanex.core.commands.consumer.CommandHandlers;
import org.cresplanex.core.commands.consumer.CommandMessage;
import org.cresplanex.core.commands.consumer.PathVariables;
import org.cresplanex.core.messaging.common.Message;
import org.cresplanex.core.saga.lock.LockTarget;
import org.cresplanex.core.saga.participant.SagaCommandHandlersBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.cresplanex.core.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static org.cresplanex.core.commands.consumer.CommandHandlerReplyBuilder.withSuccess;
import static org.cresplanex.core.saga.participant.SagaReplyMessageBuilder.withLock;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserProfileSagaCommandHandlers {

    private final UserProfileService userProfileService;

    public CommandHandlers commandHandlers() {
        return SagaCommandHandlersBuilder
                .fromChannel(SagaCommandChannel.USER_PROFILE)
                .onMessage(CreateUserProfileCommand.Exec.class,
                        CreateUserProfileCommand.Exec.TYPE,
                        this::handleCreateUserProfileCommand
                )
                .onMessage(CreateUserProfileCommand.Undo.class,
                        CreateUserProfileCommand.Undo.TYPE,
                        this::handleUndoCreateUserProfileCommand
                )
                .withPreLock(this::undoCreateUserProfilePreLock)
                .build();
    }

    private LockTarget undoCreateUserProfilePreLock(
            CommandMessage<CreateUserProfileCommand.Undo> cmd, PathVariables pvs) {
        return new LockTarget(LockTargetType.USER_PROFILE, cmd.getCommand().getUserProfileId());
    }

    private Message handleCreateUserProfileCommand(CommandMessage<CreateUserProfileCommand.Exec> cmd) {
        try {
            log.info("Handling create user profile command");
            CreateUserProfileCommand.Exec command = cmd.getCommand();
            UserProfileEntity userProfile = new UserProfileEntity();
            userProfile.setUserId(command.getUserId());
            userProfile.setName(command.getName());
            userProfile.setEmail(command.getEmail());
            userProfile.setNickname(command.getNickname());
            userProfile = userProfileService.create(userProfile);
            CreateUserProfileReply.Success reply = new CreateUserProfileReply.Success(
                    new CreateUserProfileReply.Success.Data(DtoMapper.convert(userProfile)),
                    UserProfileServiceApplicationCode.SUCCESS,
                    "User profile created successfully",
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            );
            log.info("User profile created successfully");
            return withLock(LockTargetType.USER_PROFILE, userProfile.getUserProfileId())
                    .withSuccess(reply, CreateUserProfileReply.Success.TYPE);
        } catch (Exception e) {
            log.error("Failed to create user profile", e);
            CreateUserProfileReply.Failure reply = new CreateUserProfileReply.Failure(
                    null,
                    UserProfileServiceApplicationCode.INTERNAL_SERVER_ERROR,
                    "Failed to create user profile",
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            );
            return withFailure(reply, CreateUserProfileReply.Failure.TYPE);
        }
    }

    private Message handleUndoCreateUserProfileCommand(
            CommandMessage<CreateUserProfileCommand.Undo> cmd) {
        try {
            log.info("Handling undo create user profile command");
            CreateUserProfileCommand.Undo command = cmd.getCommand();
            String userProfileId = command.getUserProfileId();
            userProfileService.undoCreate(userProfileId);
            return withSuccess();
        } catch (Exception e) {
            log.error("Failed to undo create user profile", e);
            return withFailure();
        }
    }
}
