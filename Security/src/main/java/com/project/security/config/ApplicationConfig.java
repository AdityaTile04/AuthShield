package com.project.security.config;

import com.project.security.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepo repo;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repo.findByEmail( username )
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
