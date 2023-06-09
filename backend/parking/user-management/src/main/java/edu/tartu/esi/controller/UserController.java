package edu.tartu.esi.controller;

import edu.tartu.esi.dto.UserDto;
import edu.tartu.esi.dto.PaginatedResponseDto;
import edu.tartu.esi.search.GenericSearchDto;
import edu.tartu.esi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Validated
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public PaginatedResponseDto<UserDto> fetchAllUsers(GenericSearchDto<UserDto> genericSearchDto) {
        return userService.getUsers(genericSearchDto);
    }

    @GetMapping(value = "/users/{id}", produces = {"application/json"})
    public UserDto fetchUser(@Valid @PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping(value = "/users/{id}/balance", produces = {"application/json"})
    public String fetchUserBalance(@Valid @PathVariable String id) {
        return userService.getUserBalanceById(id);
    }

    @PutMapping(value = "/users/{id}/balance", produces = {"application/json"})
    public void updateUserBalance(@Valid @PathVariable String id, @RequestBody String balance) {
        userService.updateUserBalanceById(id, balance);
    }

    @Operation(summary = "Create user", security = {})
    @SecurityRequirements(value = {})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "403", description = "Invalid credentials")
    })
    @PostMapping(value = "/users", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.ok("User was created");
    }

    @DeleteMapping(value = "/users/{id}", produces = {"application/json"})
    public ResponseEntity<String> deleteUser(@Valid @PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User was deleted");
    }

    @PutMapping(value = "/users/{id}", produces = {"application/json"})
    public ResponseEntity<String> updateUser(@Valid @PathVariable String id, @Valid @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
        return ResponseEntity.ok("User was updated");
    }
}
