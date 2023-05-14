package edu.tartu.esi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static edu.tartu.esi.model.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/**",
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

                .requestMatchers(GET, "/api/v1/users").hasRole(ADMIN.name())
                .requestMatchers(GET, "/api/v1/users/{id}").hasAnyRole(ADMIN.name(), USER.name(), LANDLORD.name())
                .requestMatchers(GET, "/api/v1/users/{id}/balance").hasAnyRole(ADMIN.name(), USER.name(), LANDLORD.name())
                .requestMatchers(PUT, "/api/v1/users/{id}/balance").hasRole(ADMIN.name())
                .requestMatchers(POST, "/api/v1/users").hasAnyRole(ADMIN.name(), USER.name(), LANDLORD.name())
                .requestMatchers(DELETE, "/api/v1/users").hasAnyRole(ADMIN.name(), USER.name(), LANDLORD.name())
                .requestMatchers(PUT, "/api/v1/users").hasAnyRole(ADMIN.name(), USER.name(), LANDLORD.name())

                .requestMatchers(POST, "/api/v1/users").permitAll()
                .requestMatchers(POST, "/api/v1/auth/authenticate").permitAll()
                .requestMatchers(GET, "/api/v1/auth/user-details").permitAll()
                .requestMatchers(POST, "/api/v1/auth/register").permitAll()
                .requestMatchers(POST, "/api/v1/auth/refresh-token").permitAll()

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());

        return http.build();
    }
}