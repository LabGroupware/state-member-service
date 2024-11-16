package org.cresplanex.api.state.userprofileservice.event.subscriber;

import lombok.AllArgsConstructor;
import org.cresplanex.api.state.common.constants.ServiceType;
import org.cresplanex.api.state.common.event.EventAggregateType;
import org.cresplanex.api.state.common.event.subscriber.BaseSubscriber;
import org.cresplanex.api.state.userprofileservice.event.handler.UserEventHandler;
import org.cresplanex.core.events.subscriber.DomainEventDispatcher;
import org.cresplanex.core.events.subscriber.DomainEventDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class UserSubscriber extends BaseSubscriber {

    @Bean
    public DomainEventDispatcher domainEventDispatcher(
            UserEventHandler userEventHandler, DomainEventDispatcherFactory domainEventDispatcherFactory) {;
        return domainEventDispatcherFactory.make(
                this.getDispatcherId(ServiceType.NOVA_USER_PROFILE, EventAggregateType.USER),
                userEventHandler.domainEventHandlers()
        );
    }
}
