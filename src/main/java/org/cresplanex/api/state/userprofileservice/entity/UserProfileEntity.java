package org.cresplanex.api.state.userprofileservice.entity;

import java.time.LocalDate;

import org.cresplanex.api.state.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.cresplanex.api.state.common.utils.OriginalAutoGenerate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_profiles")
public class UserProfileEntity extends BaseEntity {

    @Override
    public String getId() {
        return userProfileId;
    }

    @Override
    public void setId(String id) {
        userProfileId = id;
    }

    @Id
    @OriginalAutoGenerate
    @Column(name = "user_profile_id", length = 100, nullable = false, unique = true)
    private String userProfileId;

    @Column(name = "user_id", length = 100, nullable = false, unique = true)
    private String userId;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @Column(name = "given_name", length = 100)
    private String givenName;

    @Column(name = "family_name", length = 100)
    private String familyName;

    @Column(name = "middle_name", length = 100)
    private String middleName;

    @Column(name = "nickname", length = 100)
    private String nickname;

    @Lob
    @Column(name = "profile")
    private String profile;

    @Lob
    @Column(name = "picture")
    private String picture;

    @Lob
    @Column(name = "website")
    private String website;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "gender", length = 1)
    private String gender;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "zoneinfo", length = 50)
    private String zoneinfo;

    @Column(name = "locale", length = 10)
    private String locale;
}
