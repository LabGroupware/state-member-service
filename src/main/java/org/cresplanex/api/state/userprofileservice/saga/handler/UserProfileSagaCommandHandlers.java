package org.cresplanex.api.state.userprofileservice.saga.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.api.state.common.constants.UserProfileServiceApplicationCode;
import org.cresplanex.api.state.common.saga.LockTargetType;
import org.cresplanex.api.state.common.saga.SagaCommandChannel;
import org.cresplanex.api.state.common.saga.command.userprofile.CreateUserProfileCommand;
import org.cresplanex.api.state.common.saga.reply.userprofile.CreateUserProfileReply;
import org.cresplanex.api.state.common.saga.reply.userprofile.UserExistValidateReply;
import org.cresplanex.api.state.common.saga.validate.userprofile.UserExistValidateCommand;
import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.cresplanex.api.state.userprofileservice.exception.NotFoundUserException;
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

import static org.cresplanex.core.commands.consumer.CommandHandlerReplyBuilder.*;
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

                .onMessage(UserExistValidateCommand.class,
                        UserExistValidateCommand.TYPE,
                        this::handleUserExistValidateCommand
                )
                .build();
    }

    private LockTarget undoCreateUserProfilePreLock(
            CommandMessage<CreateUserProfileCommand.Undo> cmd, PathVariables pvs) {
        return new LockTarget(LockTargetType.USER_PROFILE, cmd.getCommand().getUserProfileId());
    }

    private Message handleCreateUserProfileCommand(CommandMessage<CreateUserProfileCommand.Exec> cmd) {
        try {
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
            return withLock(LockTargetType.USER_PROFILE, userProfile.getUserProfileId())
                    .withSuccess(reply, CreateUserProfileReply.Success.TYPE);
        } catch (Exception e) {
            CreateUserProfileReply.Failure reply = new CreateUserProfileReply.Failure(
                    null,
                    UserProfileServiceApplicationCode.INTERNAL_SERVER_ERROR,
                    "Failed to create user profile",
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            );
            return withException(reply, CreateUserProfileReply.Failure.TYPE);
        }
    }

    private Message handleUndoCreateUserProfileCommand(
            CommandMessage<CreateUserProfileCommand.Undo> cmd) {
        try {
            CreateUserProfileCommand.Undo command = cmd.getCommand();
            String userProfileId = command.getUserProfileId();
            userProfileService.undoCreate(userProfileId);
            return withSuccess();
        } catch (Exception e) {
            return withException();
        }
    }

    private Message handleUserExistValidateCommand(
            CommandMessage<UserExistValidateCommand> cmd) {
        try {
            UserExistValidateCommand command = cmd.getCommand();
            userProfileService.validateUsers(command.getUserIds());
            return withSuccess(
                    new UserExistValidateReply.Success(
                            null,
                            UserProfileServiceApplicationCode.SUCCESS,
                            "User exist validate successfully",
                            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    ), UserExistValidateReply.Success.TYPE
            );
        } catch (NotFoundUserException e) {
            UserExistValidateReply.Failure reply = new UserExistValidateReply.Failure(
                    new UserExistValidateReply.Failure.UserNotFound(e.getUserIds()),
                    UserProfileServiceApplicationCode.NOT_FOUND_USER,
                    "User not found",
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            );
            return withException(reply, UserExistValidateReply.Failure.TYPE);
        }catch (Exception e) {
            UserExistValidateReply.Failure reply = new UserExistValidateReply.Failure(
                    null,
                    UserProfileServiceApplicationCode.INTERNAL_SERVER_ERROR,
                    "Failed to validate user",
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            );
            return withException(reply, UserExistValidateReply.Failure.TYPE);
        }
    }
}
