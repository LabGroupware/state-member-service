package org.cresplanex.api.state.userprofileservice.config;

import org.cresplanex.core.commands.consumer.CoreCommandConsumerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    // 実装
    CoreCommandConsumerConfiguration.class
})
public class CommandConsumerConfiguration {
}
