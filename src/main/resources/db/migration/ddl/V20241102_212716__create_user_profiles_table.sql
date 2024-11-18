CREATE TABLE user_profiles (
    user_profile_id VARCHAR(100) PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL UNIQUE,
    version INTEGER DEFAULT 0 NOT NULL,
    name VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL,
    given_name VARCHAR(100),
    family_name VARCHAR(100),
    middle_name VARCHAR(100),
    nickname VARCHAR(100),
    profile TEXT,
    picture TEXT,
    website TEXT,
    phone VARCHAR(15),
    gender CHAR(1),
    birthdate DATE,
    zoneinfo VARCHAR(50),
    locale VARCHAR(10),
    created_at TIMESTAMP NOT NULL,
    created_by varchar(50) NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    updated_by varchar(50) DEFAULT NULL
);

CREATE INDEX user_profiles_user_id_index ON user_profiles (user_id);
CREATE INDEX user_profiles_email_index ON user_profiles (email);
CREATE INDEX user_profiles_name_index ON user_profiles (name);
CREATE INDEX user_profiles_locale_index ON user_profiles (locale);