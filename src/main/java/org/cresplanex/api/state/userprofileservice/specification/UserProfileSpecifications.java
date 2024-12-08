package org.cresplanex.api.state.userprofileservice.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.hibernate.type.descriptor.java.StringJavaType;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserProfileSpecifications {

    public static Specification<UserProfileEntity> whereUserProfileIds(Iterable<String> userProfileIds) {
        List<String> userProfileIdList = new ArrayList<>();
        userProfileIds.forEach(userProfileId -> {
            userProfileIdList.add(new StringJavaType().wrap(userProfileId, null));
        });

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            predicate = criteriaBuilder.and(predicate, root.get("userProfileId").in(userProfileIdList));
            return predicate;
        };
    }

    public static Specification<UserProfileEntity> whereUserIds(Iterable<String> userIds) {
        List<String> userIdList = new ArrayList<>();
        userIds.forEach(userId -> {
            userIdList.add(new StringJavaType().wrap(userId, null));
        });

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            predicate = criteriaBuilder.and(predicate, root.get("userId").in(userIdList));
            return predicate;
        };
    }
}
