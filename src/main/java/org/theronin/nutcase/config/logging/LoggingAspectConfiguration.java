package org.theronin.nutcase.config.logging;

import org.springframework.context.annotation.*;
import org.theronin.nutcase.config.Constants;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
    public MethodLoggerInterceptor loggingAspect() {
        return new MethodLoggerInterceptor();
    }
}
