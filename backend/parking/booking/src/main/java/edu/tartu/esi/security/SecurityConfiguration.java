package edu.tartu.esi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static edu.tartu.esi.security.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/auth/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html"
                )
                .permitAll()

//                .requestMatchers(GET, "api/v1/p/bookings/by-user/**").hasAnyRole(ADMIN.name(), LANDLORD.name())
//                .requestMatchers(GET, "api/v1/bookings/**").hasAnyRole(ADMIN.name(), LANDLORD.name(), USER.name())
//                .requestMatchers(POST, "api/v1/bookings").hasAnyRole(ADMIN.name(), USER.name())
//                .requestMatchers(PUT, "api/v1/bookings/**").hasAnyRole(ADMIN.name(), USER.name())
//                .requestMatchers(DELETE, "api/v1/bookings/**").hasAnyRole(ADMIN.name())

                //.requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())*/

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}