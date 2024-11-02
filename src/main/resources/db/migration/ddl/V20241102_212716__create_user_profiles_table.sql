CREATE TABLE user_profiles (
    user_id VARCHAR(1000) PRIMARY KEY,
    name VARCHAR(1000) NOT NULL,
    email VARCHAR(1000) NOT NULL,
    given_name VARCHAR(1000),
    family_name VARCHAR(1000),
    middle_name VARCHAR(1000),
    nickname VARCHAR(1000),
    profile VARCHAR(1000),
    picture VARCHAR(1000),
    website VARCHAR(1000),
    phone VARCHAR(1000),
    gender VARCHAR(1000),
    birthdate VARCHAR(1000),
    zoneinfo VARCHAR(1000),
    locale VARCHAR(1000)
);

CREATE INDEX user_profiles_email_index ON user_profiles (email);