package org.cresplanex.api.state.userprofileservice.saga.proxy;

import org.cresplanex.api.state.common.saga.SagaCommandChannel;
import org.cresplanex.api.state.common.saga.command.userpreference.CreateUserPreferenceCommand;
import org.cresplanex.core.saga.simpledsl.CommandEndpoint;
import org.cresplanex.core.saga.simpledsl.CommandEndpointBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserPreferenceServiceProxy {

    public final CommandEndpoint<CreateUserPreferenceCommand.Exec> createUserPreference
            = CommandEndpointBuilder
            .forCommand(CreateUserPreferenceCommand.Exec.class)
            .withChannel(SagaCommandChannel.USER_PREFERENCE)
            .withCommandType(CreateUserPreferenceCommand.Exec.TYPE)
            .build();

    public final CommandEndpoint<CreateUserPreferenceCommand.Undo> undoCreateUserPreference
            = CommandEndpointBuilder
            .forCommand(CreateUserPreferenceCommand.Undo.class)
            .withChannel(SagaCommandChannel.USER_PREFERENCE)
            .withCommandType(CreateUserPreferenceCommand.Undo.TYPE)
            .build();
}
