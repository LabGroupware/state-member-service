package org.cresplanex.api.state.userprofileservice.repository;

import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, String> {

    Optional<UserProfileEntity> findByUserId(String userId);
    Optional<UserProfileEntity> findByEmail(String email);

    /**
     * UserIdのリストに紐づくUserProfileEntityのリストを取得。
     *
     * @param userIds ユーザーIDリスト
     * @return UserProfileEntityのリスト
     */
    List<UserProfileEntity> findAllByUserIdIn(List<String> userIds);
}
