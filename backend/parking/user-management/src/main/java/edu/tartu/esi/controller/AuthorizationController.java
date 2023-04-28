package edu.tartu.esi.controller;


import edu.tartu.esi.dto.UserDto;
import edu.tartu.esi.security.jwt.EmailAndPasswordAuthenticationRequest;
import edu.tartu.esi.security.jwt.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "Authorization")
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class AuthorizationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    // a new end-point that allows users to authenticate themselves and generate the jwt token
    //This endpoint will receive the userDto, authenticate her/him with existing users in the database, then if authenticated, it will create the jwt
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody UserDto userDto) {

        // authenticationManager.authenticate attempts to authenticate the passed Authentication object, returning a fully populated Authentication object (including granted authorities) if successful.
        // UsernamePasswordAuthenticationToken can be used by the authenticationManager and we are passing the user name and password to it.
        // To use the authenticationManager, you need to define a Bean for it, check SecurityConfig.java, it is defined there.
        // Note that verifying the user is a required before generating the token, otherwise, we will be generating tokens for users that we cannot authenticate

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
        // If the user is authenticated we generate the token, otherwise, we throw an exception
        log.info("authentication.isAuthenticated()  {} ", authentication);

        if (authentication.isAuthenticated()) {
            log.info("jwtService.generateToken(authRequest.getName())  {} ", jwtService.generateToken(userDto.getEmail()).toString());
            return jwtService.generateToken(userDto.getEmail());
        } else {
            throw new UsernameNotFoundException("The user cannot be authenticated");
        }
    }

    @ApiOperation(value = "Authorization endpoint.",
            notes = "Returns Authorization header with a JWT in case of successful authorization.")
    @PostMapping(value = "/get-token", consumes = APPLICATION_JSON_VALUE)
    public void getToken(@ApiParam(value = "Username and password to authorize.", required = true)
                         @RequestBody EmailAndPasswordAuthenticationRequest authenticationRequest) {
    }
}
