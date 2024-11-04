package org.cresplanex.api.state.userprofileservice.mapper.proto;

import build.buf.gen.userprofile.v1.UserProfile;
import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;

import org.cresplanex.api.state.userprofileservice.utils.ValueFromNullable;

public class ProtoMapper {

    public static UserProfile toUserProfile(UserProfileEntity userProfileEntity) {

        return UserProfile.newBuilder()
                .setUserId(userProfileEntity.getUserId())
                .setName(userProfileEntity.getName())
                .setEmail(userProfileEntity.getEmail())
                .setGivenName(ValueFromNullable.toNullableString(userProfileEntity.getGivenName()))
                .setFamilyName(ValueFromNullable.toNullableString(userProfileEntity.getFamilyName()))
                .setMiddleName(ValueFromNullable.toNullableString(userProfileEntity.getMiddleName()))
                .setNickname(ValueFromNullable.toNullableString(userProfileEntity.getNickname()))
                .setProfile(ValueFromNullable.toNullableString(userProfileEntity.getProfile()))
                .setPicture(ValueFromNullable.toNullableString(userProfileEntity.getPicture()))
                .setWebsite(ValueFromNullable.toNullableString(userProfileEntity.getWebsite()))
                .setPhone(ValueFromNullable.toNullableString(userProfileEntity.getPhone()))
                .setGender(ValueFromNullable.toNullableString(userProfileEntity.getGender()))
                .setBirthdate(ValueFromNullable.toNullableString(userProfileEntity.getBirthdate()))
                .setZoneinfo(ValueFromNullable.toNullableString(userProfileEntity.getZoneinfo()))
                .setLocale(ValueFromNullable.toNullableString(userProfileEntity.getLocale()))
                .build();
    }
}
