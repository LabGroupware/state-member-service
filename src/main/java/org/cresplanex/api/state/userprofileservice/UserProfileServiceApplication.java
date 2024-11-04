package org.cresplanex.api.state.userprofileservice;

import org.cresplanex.api.state.userprofileservice.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ConfigurationPropertiesScan
public class UserProfileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserProfileServiceApplication.class, args);
    }

}
