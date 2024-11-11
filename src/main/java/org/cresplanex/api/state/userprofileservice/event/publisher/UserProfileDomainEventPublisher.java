package org.cresplanex.api.state.userprofileservice.event.publisher;

import org.cresplanex.api.state.userprofileservice.event.EventAggregateChannel;
import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.cresplanex.api.state.userprofileservice.event.model.userprofile.UserProfileDomainEvent;
import org.cresplanex.core.events.aggregates.AbstractAggregateDomainEventPublisher;
import org.cresplanex.core.events.publisher.DomainEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserProfileDomainEventPublisher extends AbstractAggregateDomainEventPublisher<UserProfileEntity, UserProfileDomainEvent> {

    public UserProfileDomainEventPublisher(DomainEventPublisher eventPublisher) {
        super(eventPublisher, UserProfileEntity.class, UserProfileEntity::getUserProfileId, EventAggregateChannel.USER_PROFILE);
    }
}
