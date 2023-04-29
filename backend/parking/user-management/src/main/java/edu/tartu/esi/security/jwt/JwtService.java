package edu.tartu.esi.security.jwt;

import edu.tartu.esi.security.UserDetailsServiceImpl;
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
    private UserDetailsServiceImpl userDetailsService;

    // A funtion to generate the token
    // jwt has three componenets, all of them are called claims
    // We need to store these three compoenents in a format that allows us to deal with them properly, and that is why we are using HashMap()
    // A HashMap allows storing items in "key/value" pairs, and you can access them by an index of another type (e.g. a String).
    // After that, we are passing the claims (HashMap()) and the user name to another function to create the jwt token.
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }


    private String createToken(Map<String, Object> claims, String userName) {
        // This is not required as  user authorities are verified in SecurityConfig/securityFilterChain()
        // I am adding the user authorities/roles to the claims of the jwt token
        // Just an example for some of you that might need to add some user data (e.g., isEmployee, isAdmin) to the claims of the jwt token
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        String rolesClaim = roles.toString();
        log.info("rolesClaim  {} ", rolesClaim);
        int start = rolesClaim.indexOf("[");
        int end = rolesClaim.indexOf("]");
        rolesClaim = rolesClaim.substring(start + 1, end);
        log.info("claims  {} ", rolesClaim);

        // After, extracting the authorities/roles and removing the "[" "]", e are adding the authorities/roles to the claim
        claims.put("roles", rolesClaim);

        return Jwts.builder() //Adds all given name/value pairs to the JSON Claims in the payload.
                .setClaims(claims) // setting our claims
                // JWTs claims are pieces of information asserted about a subject,
                // The subject identifies the principal that is the subject of the JWT.
                // The subject of our JWT is the user in our case, and here we are using her/his name
                .setSubject(userName)
                //.addClaims(roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // when setting an expiration "Date", we use milliseconds, the easiet way to set it is using the following format 1000(1000 milliseconds  = second)*60 (1 second * 60 =  a minutes) *60 (1 minute * 60 =  1 hour) -> 1000*60*60 = 1 hour
                // .signWith(Key key, SignatureAlgorithm alg) signs the constructed JWT with the specified key using the specified algorithm, producing a JWS.
                // For the key, we are using a function to created, and for the SignatureAlgorithm we are using the "HS256" hashing algorithm.
                // .compact() is the final step, and it builds the JWT and serializes it to a compact, URL-safe string according to the JWT Compact Serialization rules.
                .signWith(signingKey(), SignatureAlgorithm.HS256).compact();
    }

    // For generating a secret key, You can use https://www.allkeysgenerator.com/Random/Security-Encryption-Key-Generator.aspx
    // Check the HEX and it is better to use a 256-bit to generate your secret key
    public static final String SECRET = "4D6351665468576D5A7134743777217A25432A462D4A614E645267556B586E32";

    private Key signingKey() {
        // Creating a byte array for our key by decoding our SECRET using BASE64 encoding scheme
        byte[] keyDecoder = Decoders.BASE64.decode(SECRET);
        // Keys.hmacShaKeyFor(bytes) converts the byte array into a valid Key using the HMAC-SHA algorithms based on the specified key byte array
        return Keys.hmacShaKeyFor(keyDecoder);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // A function to extract the subject of the token (user name)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // A function to extract the user roles/authorities
    public String extractRoles(String token) {
        String claimRoles = extractAllClaims(token).get("roles", String.class);
        return claimRoles;
    }

    // A function to extract the token expiration date
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // A function to check whether the token is not expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // A funtion to validate the token, it takes the token and userDetails as input, and check username and whther the token is not expiered
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        log.info("extractRoles  {} ", extractRoles(token));
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
