package edu.tartu.esi;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    //TODO add open endpoints - search
    public static final List<String> openApiEndpoints = List.of(
            "api/v1/auth/authenticate",
            "api/v1/auth/register",
            "api/v1/location",
            "api/v1/"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}