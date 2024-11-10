package org.cresplanex.api.state.userprofileservice.event.subscriber;

import lombok.AllArgsConstructor;
import org.cresplanex.api.state.userprofileservice.config.ApplicationConfiguration;
import org.cresplanex.api.state.userprofileservice.event.EventAggregateChannel;
import org.cresplanex.api.state.userprofileservice.event.handler.UserEventHandler;
import org.cresplanex.core.events.subscriber.DomainEventDispatcher;
import org.cresplanex.core.events.subscriber.DomainEventDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class UserSubscriber {

    private final ApplicationConfiguration applicationConfiguration;

    @Bean
    public DomainEventDispatcher domainEventDispatcher(UserEventHandler userEventHandler, DomainEventDispatcherFactory domainEventDispatcherFactory) {
        return domainEventDispatcherFactory.make("%s.eventListener.%s".formatted(
                applicationConfiguration.getName(), EventAggregateChannel.USER), userEventHandler.domainEventHandlers());
    }
}
