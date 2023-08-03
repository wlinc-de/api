package de.wlinc.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(configurer -> {
                    configurer.configurationSource(request -> {
                        var cors = new org.springframework.web.cors.CorsConfiguration();
                        cors.setAllowedOrigins(List.of("*"));
                        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                        cors.setAllowedHeaders(List.of("*"));
                        return cors;
                    });
                })
                .authorizeHttpRequests(reg -> {
                    //reg.requestMatchers("/link").permitAll();
                    reg.requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll();
                    reg.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll();
                    reg.requestMatchers("/error").permitAll();
                    reg.anyRequest().authenticated();
                })
                .oauth2ResourceServer(configurer -> configurer.jwt(withDefaults()));
        return http.build();
    }
}
