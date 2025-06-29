package org.example.backend.config;

import org.example.backend.util.CurrentUserEmailResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CurrentUserEmailResolver currentUserEmailResolver;

    public WebConfig(CurrentUserEmailResolver currentUserEmailResolver) {
        this.currentUserEmailResolver = currentUserEmailResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserEmailResolver);
    }
}
