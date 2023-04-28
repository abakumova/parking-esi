package edu.tartu.esi.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;;

import static edu.tartu.esi.security.SecurityConstants.AUTHORITIES;

@Slf4j
public class JwtEmailAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtEmailAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                   JwtProperties jwtProperties,
                                                   SecretKey secretKey,
                                                   String filterProcessesUrl) {
        this.authenticationManager = authenticationManager;
        this.jwtProperties = jwtProperties;
        this.secretKey = secretKey;
        setFilterProcessesUrl(filterProcessesUrl);
    }

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {
        try {
            EmailAndPasswordAuthenticationRequest authenticationRequest = getAuthenticationRequest(request);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            );

            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            log.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    private EmailAndPasswordAuthenticationRequest getAuthenticationRequest(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getInputStream(), EmailAndPasswordAuthenticationRequest.class);
    }

    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim(AUTHORITIES, authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(jwtProperties.getTokenExpirationHours(), ChronoUnit.HOURS)))
                .signWith(secretKey)
                .compact();
        response.addHeader(jwtProperties.getAuthorizationHeaderName(), jwtProperties.getTokenPrefix() + token);
    }
}
