package edu.tartu.esi.controller;

import edu.tartu.esi.dto.PaginatedResponseDto;
import edu.tartu.esi.dto.UserDto;
import edu.tartu.esi.search.GenericSearchDto;
import edu.tartu.esi.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static edu.tartu.esi.security.SecurityConstants.ADMIN_ROLE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public PaginatedResponseDto<UserDto> fetchAllUsers(GenericSearchDto<UserDto> genericSearchDto) {
        return userService.getUsers(genericSearchDto);
    }

    @RolesAllowed(ADMIN_ROLE)
    @GetMapping(value = "/users/{id}", produces = {"application/json"})
    public UserDto fetchUser(@Valid @PathVariable String id) {
        return userService.getUserById(id);
    }

    @RolesAllowed(ADMIN_ROLE)
    @PostMapping(value = "/users", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.ok("User was created");
    }

    @RolesAllowed(ADMIN_ROLE)
    @DeleteMapping(value = "/users/{id}", produces = {"application/json"})
    public ResponseEntity<String> deleteUser(@Valid @PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User was deleted");
    }

    @RolesAllowed(ADMIN_ROLE)
    @PutMapping(value = "/users/{id}", produces = {"application/json"})
    public ResponseEntity<String> updateUser(@Valid @PathVariable String id, @Valid @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
        return ResponseEntity.ok("User was updated");
    }
}
