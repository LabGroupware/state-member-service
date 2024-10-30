package org.cresplanex.api.state.userprofileservice.mapper.proto;

import org.cresplanex.api.state.userprofileservice.entity.User;
import org.cresplanex.api.state.userprofileservice.utils.FieldSetter;

import build.buf.gen.user.v1.GetUserResponse;

public class UserProtoMapper {

    /**
     * Convert User to GetUserResponse
     *
     * @param user
     * @return
     */
    public static GetUserResponse toGetUserResponse(User user) {
        GetUserResponse.Builder response = GetUserResponse.newBuilder()
                .setUserId(user.getUserId())
                .setName(user.getName())
                .setEmail(user.getEmail());
        FieldSetter.setIfNotNull(
                user,
                response,
                "GivenName",
                "FamilyName",
                "MiddleName",
                "Nickname",
                "Profile",
                "Picture",
                "Website",
                "Phone",
                "Gender",
                "Birthdate",
                "Zoneinfo",
                "Locale"
        );

        return response.build();
    }
}
