package org.cresplanex.api.state.userprofileservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;
    private String name;
    private String email;
    private String givenName;
    private String familyName;
    private String middleName;
    private String nickname;
    private String profile;
    private String picture;
    private String website;
    private String phone;
    private String gender;
    private String birthdate;
    private String zoneinfo;
    private String locale;
}
