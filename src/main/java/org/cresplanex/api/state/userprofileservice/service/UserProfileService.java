package org.cresplanex.api.state.userprofileservice.service;

import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.cresplanex.api.state.userprofileservice.event.publisher.UserProfileDomainEventPublisher;
import org.cresplanex.api.state.userprofileservice.exception.UserProfileNotFoundException;
import org.cresplanex.api.state.userprofileservice.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    private final UserProfileDomainEventPublisher domainEventPublisher;

    public UserProfileEntity findById(String userProfileId) {
        return userProfileRepository.findById(userProfileId).orElseThrow(() -> {
            return new UserProfileNotFoundException(
                    UserProfileNotFoundException.FindType.BY_ID,
                    userProfileId
            );
        });
    }

    public UserProfileEntity findByUserId(String userId) {
        return userProfileRepository.findByUserId(userId).orElseThrow(() -> {
            return new UserProfileNotFoundException(
                    UserProfileNotFoundException.FindType.BY_USER_ID,
                    userId
            );
        });
    }

    public UserProfileEntity findByEmail(String email) {
        return userProfileRepository.findByEmail(email).orElseThrow(() -> {
            return new UserProfileNotFoundException(
                    UserProfileNotFoundException.FindType.BY_EMAIL,
                    email
            );
        });
    }

    public UserProfileEntity create(String userId, String name, String email) {
        //        domainEventPublisher.publish(EventAggregateTypes.USER, userId,
//                Collections.singletonList(new UserCreated(userId, "tesst", "email@example.com")),
//                EventTypes.USER_CREATED);

        UserProfileEntity profile = new UserProfileEntity();
        profile.setUserId(userId);
        profile.setName(name);
        profile.setEmail(email);
        return userProfileRepository.save(profile);
    }
}
