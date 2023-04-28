package edu.tartu.esi.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@Getter
@Setter
@Validated
@EnableConfigurationProperties
@Component
@Configuration
//@ConfigurationProperties(prefix = "application.jwt")
public class JwtProperties {

    @NotBlank
    @Value("${application.jwt.passphrase}")
    private String passphrase;
    @Value("${application.jwt.tokenPrefix}")
    private String tokenPrefix;
    @NotNull
    @Positive
    @Value("${application.jwt.tokenExpirationHours}")
    private Integer tokenExpirationHours;

    public String getAuthorizationHeaderName() {
        return HttpHeaders.AUTHORIZATION;
    }
}
