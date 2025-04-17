package com.cse5382.assignment.Config;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cse5382.assignment.Component.JwtFilter;
@Configuration
@EnableConfigurationProperties(AuthUserProperties.class)
public class SecurityConfig {

    private final AuthUserProperties authUsersProperties;
    
    private final JwtFilter jwtFilter;

    public SecurityConfig(AuthUserProperties authUsersProperties, JwtFilter jwtFilter) {
        this.authUsersProperties = authUsersProperties;
        this.jwtFilter =  jwtFilter;
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        List<UserDetails> userDetailsList = Optional.ofNullable(authUsersProperties.getUsers())
                .orElse(Collections.emptyList())
                .stream()
                .map(user -> User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRoles().split(","))
                        .build())
                .collect(Collectors.toList());

        return new InMemoryUserDetailsManager(userDetailsList);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // For demo purposes only
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/**").permitAll()
//                    .requestMatchers("/**").permitAll()
                    .requestMatchers("/phoneBook/list").hasAnyRole("READ", "READ_WRITE")
                    .requestMatchers("/phoneBook/add", "/phoneBook/remove").hasAnyRole("WRITE_ONLY", "READ_WRITE")
                    .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) //
                .build();
    }
}

