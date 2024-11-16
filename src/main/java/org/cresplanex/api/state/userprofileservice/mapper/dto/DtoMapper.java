package org.cresplanex.api.state.userprofileservice.mapper.dto;

import org.cresplanex.api.state.common.dto.userprofile.UserProfileDto;
import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;

import java.time.format.DateTimeFormatter;

public class DtoMapper {

    public static UserProfileDto convert(UserProfileEntity userProfileEntity) {
        return UserProfileDto.builder()
                .userId(userProfileEntity.getUserId())
                .userProfileId(userProfileEntity.getUserProfileId())
                .name(userProfileEntity.getName())
                .email(userProfileEntity.getEmail())
                .givenName(userProfileEntity.getGivenName())
                .familyName(userProfileEntity.getFamilyName())
                .middleName(userProfileEntity.getMiddleName())
                .nickname(userProfileEntity.getNickname())
                .profile(userProfileEntity.getProfile())
                .picture(userProfileEntity.getPicture())
                .website(userProfileEntity.getWebsite())
                .phone(userProfileEntity.getPhone())
                .gender(userProfileEntity.getGender())
                .birthdate(
                        userProfileEntity.getBirthdate() != null
                                ? userProfileEntity.getBirthdate().format(DateTimeFormatter.ISO_DATE)
                                : null
                )
                .zoneinfo(userProfileEntity.getZoneinfo())
                .locale(userProfileEntity.getLocale())
                .build();
    }
}
