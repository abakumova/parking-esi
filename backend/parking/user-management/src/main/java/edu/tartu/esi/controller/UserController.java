package edu.tartu.esi.controller;

import edu.tartu.esi.dto.PaginatedResponseDto;
import edu.tartu.esi.dto.UserDto;
import edu.tartu.esi.search.GenericSearchDto;
import edu.tartu.esi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Validated
public class UserController {

    //Pageable

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
