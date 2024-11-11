package org.cresplanex.api.state.userprofileservice.event.handler;

import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.cresplanex.api.state.userprofileservice.event.EventAggregateChannel;
import org.cresplanex.api.state.userprofileservice.event.model.user.UserCreated;
import org.cresplanex.api.state.userprofileservice.service.UserProfileService;
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
    private final UserProfileService userProfileService;

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType(EventAggregateChannel.USER)
                .onEvent(UserCreated.class, this::handleUserCreated, UserCreated.TYPE)
                .build();
    }

    private void handleUserCreated(DomainEventEnvelope<UserCreated> dee) {
        logger.info("Handling UserCreated event for user {}", dee.getAggregateId());
        UserProfileEntity userProfile = new UserProfileEntity();
        userProfile.setUserId(dee.getEvent().getUserId());
        userProfile.setName(dee.getEvent().getName());
        userProfile.setEmail(dee.getEvent().getEmail());
        userProfile.setNickname(dee.getEvent().getNickname());
        this.userProfileService.beginCreate(
                userProfile
        );
    }
}
