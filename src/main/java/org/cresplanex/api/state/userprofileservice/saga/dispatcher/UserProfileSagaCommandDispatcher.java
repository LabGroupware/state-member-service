package org.cresplanex.api.state.userprofileservice.saga.dispatcher;

import lombok.AllArgsConstructor;
import org.cresplanex.api.state.userprofileservice.config.ApplicationConfiguration;
import org.cresplanex.api.state.userprofileservice.saga.SagaCommandChannel;
import org.cresplanex.api.state.userprofileservice.saga.handler.UserProfileSagaCommandHandlers;
import org.cresplanex.core.saga.participant.SagaCommandDispatcher;
import org.cresplanex.core.saga.participant.SagaCommandDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class UserProfileSagaCommandDispatcher {

    private final ApplicationConfiguration applicationConfiguration;

    @Bean
    public SagaCommandDispatcher userProfileSagaCommandHandlersDispatcher(
            UserProfileSagaCommandHandlers userProfileSagaCommandHandlers,
            SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {
        return sagaCommandDispatcherFactory.make("%s.sagaCommandDispatcher.%s".formatted(
                applicationConfiguration.getName(), SagaCommandChannel.USER_PROFILE),
                userProfileSagaCommandHandlers.commandHandlers());
    }
}
