package org.cresplanex.api.state.userprofileservice.saga.proxy;

import org.cresplanex.api.state.userprofileservice.saga.SagaCommandChannel;
import org.cresplanex.api.state.userprofileservice.saga.command.userprofile.CreateUserProfileCommand;
import org.cresplanex.api.state.userprofileservice.saga.command.userprofile.UndoCreateUserProfileCommand;
import org.cresplanex.core.saga.simpledsl.CommandEndpoint;
import org.cresplanex.core.saga.simpledsl.CommandEndpointBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserProfileServiceProxy {

    public final CommandEndpoint<CreateUserProfileCommand> createUserProfile
            = CommandEndpointBuilder
            .forCommand(CreateUserProfileCommand.class)
            .withChannel(SagaCommandChannel.USER_PROFILE)
            .withCommandType(CreateUserProfileCommand.TYPE)
            .build();

    public final CommandEndpoint<UndoCreateUserProfileCommand> undoCreateUserProfile
            = CommandEndpointBuilder
            .forCommand(UndoCreateUserProfileCommand.class)
            .withChannel(SagaCommandChannel.USER_PROFILE)
            .withCommandType(UndoCreateUserProfileCommand.TYPE)
            .build();
}
