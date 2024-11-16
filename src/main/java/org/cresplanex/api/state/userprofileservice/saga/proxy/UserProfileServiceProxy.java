package org.cresplanex.api.state.userprofileservice.saga.proxy;

import org.cresplanex.api.state.common.saga.SagaCommandChannel;
import org.cresplanex.api.state.common.saga.command.userprofile.CreateUserProfileCommand;
import org.cresplanex.core.saga.simpledsl.CommandEndpoint;
import org.cresplanex.core.saga.simpledsl.CommandEndpointBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserProfileServiceProxy {

    public final CommandEndpoint<CreateUserProfileCommand.Exec> createUserProfile
            = CommandEndpointBuilder
            .forCommand(CreateUserProfileCommand.Exec.class)
            .withChannel(SagaCommandChannel.USER_PROFILE)
            .withCommandType(CreateUserProfileCommand.Exec.TYPE)
            .build();

    public final CommandEndpoint<CreateUserProfileCommand.Undo> undoCreateUserProfile
            = CommandEndpointBuilder
            .forCommand(CreateUserProfileCommand.Undo.class)
            .withChannel(SagaCommandChannel.USER_PROFILE)
            .withCommandType(CreateUserProfileCommand.Undo.TYPE)
            .build();
}
