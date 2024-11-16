package org.cresplanex.api.state.userprofileservice.event.publisher;

import org.cresplanex.api.state.common.event.EventAggregateType;
import org.cresplanex.api.state.common.event.model.userprofile.UserProfileDomainEvent;
import org.cresplanex.api.state.common.event.publisher.AggregateDomainEventPublisher;
import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.cresplanex.core.events.publisher.DomainEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserProfileDomainEventPublisher extends AggregateDomainEventPublisher<UserProfileEntity, UserProfileDomainEvent> {

    public UserProfileDomainEventPublisher(DomainEventPublisher eventPublisher) {
        super(eventPublisher, UserProfileEntity.class, EventAggregateType.USER_PROFILE);
    }
}
