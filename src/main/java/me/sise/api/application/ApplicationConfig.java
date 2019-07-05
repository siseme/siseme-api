package me.sise.api.application;

import me.sise.api.application.common.util.ShortUrlBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableCaching
public class ApplicationConfig {
    @Bean
    public ShortUrlBuilder shortUrlBuilder() {
        return new ShortUrlBuilder();
    }
}
