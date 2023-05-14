//package edu.tartu.esi.security;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.configurationprocessor.json.JSONException;
//import org.springframework.http.MediaType;
//import org.springframework.lang.NonNull;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//
//    @Autowired
//    private WebClient.Builder webClientBuilder;
//
//    @Value("${webclient.email}")
//    private String email;
//    @Value("${webclient.password}")
//    private String password;
//
//    @SneakyThrows
//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
//        String token = getToken(email, password);
//
////        if (request.getServletPath().contains("/api/v1/parking-slots")) {
////            filterChain.doFilter(request, response);
////            return;
////        }
//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String userEmail;
//        final String userRole;
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        jwt = authHeader.substring(7);
//        userEmail = jwtService.extractUsername(jwt);
//        userRole = jwtService.extractRole(jwt); // Extract the user role from the JWT
//
//        log.warn("!!!@###$%%%% userEmail {}", userEmail);
//        log.warn("!!!@###$%%%% token {}", token);
//
////        UserDetails userDetails = webClientBuilder.build()
////                .get()
////                .uri("http://localhost:8089/api/v1/auth/user-details?email=" + userEmail)
////                .header("Authorization", "Bearer " + token)
////                .retrieve()
////                .bodyToMono(UserDetails.class)
////                .block();
//
//        Mono<UserDetails> userDetailsMono = webClientBuilder
//                .build()
//                .get()
//                .uri("http://localhost:8089/api/v1/auth/user-details?email=" + userEmail)
//                .header("Authorization", "Bearer " + token)
//                .retrieve()
//                .bodyToMono(UserDetails.class);
//
//        UserDetails userDetails = userDetailsMono.block();
//
//        log.warn("!@#$$%^%%^&& userDetails {}", userDetails);
//
//        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            //UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//            if (jwtService.isTokenValid(jwt, userDetails)) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities()
//                );
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
//                authToken.setDetails(userRole); // Set the user role as an additional detail in the authentication token
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private String getToken(String email, String password) throws JSONException, JsonProcessingException {
//        Map<String, String> params = new HashMap<>();
//        params.put("email", email);
//        params.put("password", password);
//
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(params);
//        log.warn("!!!!!!!!!!!!!!!!!!!!!! {}", json);
//
//        JsonNode jwtTokenMono = webClientBuilder
//                .build()
//                .post()
//                .uri("http://localhost:8089/api/v1/auth/authenticate")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(json))
//                .retrieve()
//                .bodyToMono(JsonNode.class)
//                .map(s -> s.path("access_token"))
//                .block();
//
//        log.warn("!!!!!!!!!!!!!!!!!!!!!! {}", jwtTokenMono);
//        return jwtTokenMono.toString();
//    }
//}