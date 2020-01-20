package com.mofit.orch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.LinkedList;
import java.util.List;

@Configuration
public class PropertiesReadConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        List<Resource> resources = new LinkedList<>();
        resources.add(new ClassPathResource("test/test-base.yml"));
        configurer.setLocations(resources.toArray(new Resource[0]));
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;

    }
}