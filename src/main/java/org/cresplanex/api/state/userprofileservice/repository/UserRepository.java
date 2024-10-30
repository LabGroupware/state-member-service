package org.cresplanex.api.state.userprofileservice.repository;

import org.cresplanex.api.state.userprofileservice.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    public User getUser(String userId) {
        return new User(
                userId,
                "John Doe",
                "test@example.com",
                "John",
                "Doe",
                null,
                "John",
                "https://example.com",
                "https://example.com/picture",
                "https://example.com",
                "123456789",
                "male",
                "2021-01-01",
                "Asia/Tokyo",
                "ja-JP"
        );
    }
}
