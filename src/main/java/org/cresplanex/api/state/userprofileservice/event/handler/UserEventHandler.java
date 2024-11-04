package org.cresplanex.api.state.userprofileservice.event.handler;

import org.cresplanex.api.state.userprofileservice.constants.EventAggregateTypes;
import org.cresplanex.api.state.userprofileservice.constants.EventTypes;
import org.cresplanex.api.state.userprofileservice.event.model.user.UserCreated;
import org.cresplanex.api.state.userprofileservice.event.model.user.UserInfoUpdated;
import org.cresplanex.core.events.common.DomainEventEnvelope;
import org.cresplanex.core.events.subscriber.DomainEventHandlers;
import org.cresplanex.core.events.subscriber.DomainEventHandlersBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class UserEventHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType(EventAggregateTypes.USER)
                .onEvent(UserCreated.class, this::handleUserCreated, EventTypes.USER_CREATED)
                .onEvent(UserInfoUpdated.class, this::handleUserInfoUpdated, EventTypes.USER_INFO_UPDATED)
                .build();
    }

    private void handleUserCreated(DomainEventEnvelope<UserCreated> dee) {
        logger.info("Handling UserCreated event for user {}", dee.getAggregateId());
        logger.info("Message: {}", dee.getMessage());
        logger.info("Event id: {}", dee.getEventId());
        logger.info("Event type: {}", dee.getAggregateType());
        logger.info("User name: {}", dee.getEvent().getName());
        logger.info("User email: {}", dee.getEvent().getEmail());
        logger.info("User id: {}", dee.getEvent().getUserId());
    }

    private void handleUserInfoUpdated(DomainEventEnvelope<UserInfoUpdated> dee) {
        logger.info("Handling UserProfileUpdated event for user {}", dee.getAggregateId());
        logger.info("Message: {}", dee.getMessage());
        logger.info("Event id: {}", dee.getEventId());
        logger.info("Event type: {}", dee.getAggregateType());
        logger.info("User name: {}", dee.getEvent().getName());
        logger.info("User email: {}", dee.getEvent().getEmail());
        logger.info("User id: {}", dee.getEvent().getUserId());
    }
}
