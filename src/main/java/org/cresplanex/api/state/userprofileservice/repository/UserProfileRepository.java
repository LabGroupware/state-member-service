package org.cresplanex.api.state.userprofileservice.repository;

import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.cresplanex.api.state.userprofileservice.enums.UserProfileSortType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT u FROM UserProfileEntity u ORDER BY " +
            "CASE WHEN :sortType = 'CREATED_AT_ASC' THEN u.createdAt END ASC, " +
            "CASE WHEN :sortType = 'CREATED_AT_DESC' THEN u.createdAt END DESC, " +
            "CASE WHEN :sortType = 'NAME_ASC' THEN u.name END ASC, " +
            "CASE WHEN :sortType = 'NAME_DESC' THEN u.name END DESC")
    List<UserProfileEntity> findListWithOffsetPagination(UserProfileSortType sortType, Pageable pageable);

    @Query("SELECT u FROM UserProfileEntity u WHERE u.userId IN :userIds ORDER BY " +
            "CASE WHEN :sortType = 'CREATED_AT_ASC' THEN u.createdAt END ASC, " +
            "CASE WHEN :sortType = 'CREATED_AT_DESC' THEN u.createdAt END DESC, " +
            "CASE WHEN :sortType = 'NAME_ASC' THEN u.name END ASC, " +
            "CASE WHEN :sortType = 'NAME_DESC' THEN u.name END DESC")
    List<UserProfileEntity> findListByUserIds(List<String> userIds, UserProfileSortType sortType);

    @Query("SELECT u FROM UserProfileEntity u ORDER BY " +
            "CASE WHEN :sortType = 'CREATED_AT_ASC' THEN u.createdAt END ASC, " +
            "CASE WHEN :sortType = 'CREATED_AT_DESC' THEN u.createdAt END DESC, " +
            "CASE WHEN :sortType = 'NAME_ASC' THEN u.name END ASC, " +
            "CASE WHEN :sortType = 'NAME_DESC' THEN u.name END DESC")
    List<UserProfileEntity> findList(UserProfileSortType sortType);

    @Query("SELECT COUNT(u) FROM UserProfileEntity u")
    int countList();

    @Query("SELECT COUNT(u) FROM UserProfileEntity u WHERE u.userId IN :userIds")
    int countListByUserIds(List<String> userIds);
}
