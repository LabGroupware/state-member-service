package org.cresplanex.api.state.userprofileservice.service;

import org.cresplanex.api.state.userprofileservice.entity.User;
import org.cresplanex.api.state.userprofileservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.getUser(userId);
    }
}
