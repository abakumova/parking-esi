package edu.tartu.esi.security;

//import edu.tartu.esi.config.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static edu.tartu.esi.security.Role.ADMIN;
import static edu.tartu.esi.security.Role.LANDLORD;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        //"/api/v1/**",
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

                .requestMatchers(GET, "/api/v1/parking-slots/by-id/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LANDLORD")
                .requestMatchers(GET, "/api/v1/parking-slots/by-status/**").permitAll()
                .requestMatchers(GET, "/api/v1/parking-slots/by-location/**").permitAll()
                .requestMatchers(GET, "/api/v1/parking-slots/by-landlord/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LANDLORD")
                .requestMatchers(POST, "/api/v1/parking-slots").hasAnyAuthority("ROLE_ADMIN", "ROLE_LANDLORD")
                .requestMatchers(PUT, "/api/v1/parking-slots/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LANDLORD")
                .requestMatchers(GET, "/api/v1/parking-slots").hasAnyAuthority("ROLE_ADMIN", "ROLE_LANDLORD")
                .requestMatchers(GET, "/api/v1/parking-slots/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LANDLORD")
                .requestMatchers(DELETE, "/api/v1/parking-slots/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LANDLORD")

                .requestMatchers(GET, "/api/v1/parking-slots/by-id/**").hasAnyAuthority("ROLE_LANDLORD")
                .requestMatchers(GET, "/api/v1/parking-slots/by-landlord/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LANDLORD")
                .requestMatchers(POST, "/api/v1/parking-slots").hasAnyAuthority("ROLE_ADMIN", "ROLE_LANDLORD")
                .requestMatchers(PUT, "/api/v1/parking-slots/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LANDLORD")
                .requestMatchers(DELETE, "/api/v1/parking-slots/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LANDLORD")

                //.requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())*/

//                .anyRequest()
//                .authenticated()
//
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .anyRequest()
//                .authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}