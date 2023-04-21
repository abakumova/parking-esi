package edu.tartu.esi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.esi.authservice.config.MyUserDetailsService;


import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtService {

    @Autowired
    private MyUserDetailsService userDetailsService;

    // A funtion to generate the token
    // jwt has three componenets, all of them are called claims
    // We need to store these three compoenents in a format that allows us to deal with them properly, and that is why we are using HashMap()
    // A HashMap allows storing items in "key/value" pairs, and you can access them by an index of another type (e.g. a String).
    // After that, we are passing the claims (HashMap()) and the user name to another function to create the jwt token.
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }


    // For generating a secret key, You can use https://www.allkeysgenerator.com/Random/Security-Encryption-Key-Generator.aspx
    // Check the HEX and it is better to use a 256-bit to generate your secret key
    public static final String SECRET = "4D6351665468576D5A7134743777217A25432A462D4A614E645267556B586E32";


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRoles(String token) {
        String claimRoles = extractAllClaims(token).get("roles", String.class);
        return claimRoles;
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        log.info("extractRoles  {} ", extractRoles(token));
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key signingKey() {
        byte[] keyDecoder = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyDecoder);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        String rolesClaim = roles.toString();
        log.info("rolesClaim  {} ", rolesClaim);
        int start = rolesClaim.indexOf("[");
        int end = rolesClaim.indexOf("]");
        rolesClaim = rolesClaim.substring(start + 1, end);
        log.info("claims  {} ", rolesClaim);

        claims.put("roles", rolesClaim);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(signingKey(), SignatureAlgorithm.HS256).compact();
    }
}
