package edu.tartu.esi.service;

import edu.tartu.esi.dto.PaginatedResponseDto;
import edu.tartu.esi.dto.UserDto;
import edu.tartu.esi.exception.EmailAlreadyExistsException;
import edu.tartu.esi.exception.UserNotFoundException;
import edu.tartu.esi.mapper.UserMapper;
import edu.tartu.esi.model.User;
import edu.tartu.esi.model.UserRoleEnum;
import edu.tartu.esi.repository.UserRepository;
import edu.tartu.esi.search.GenericSearchDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final UserMapper userMapper;

    public void createUser(UserDto userDto) {
        assertUserDto(userDto, "Can't create a user info when user is null");
        if (userRepository.existsByEmail(userDto.getEmail())) {
            log.info("-- createUser; Email already exists");
            throw new EmailAlreadyExistsException(format("Email %s already exists", userDto.getEmail()));
        }
        User newUser = User.builder()
                .email(userDto.getEmail())
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(userDto.getPassword()))
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .userRole(userDto.getUserRole())
                .paymentMethod(userDto.getPaymentMethod())
                .build();
        userRepository.save(newUser);

        log.info("-- createUser; User {} is created", newUser.getId());
    }

    @Transactional
    public UserDto getUserById(String id) {
        log.info("-- fetchUser {}", id);
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException(format("User with id = %s wasn't found", id)));
        log.warn("-- get USER {}", userMapper.toDto(user));
        return userMapper.toDto(user);
    }

    public Optional<User> getUserByEmail(String email) {
        log.info("-- fetchUser by Email");
        return userRepository.findUserByEmail(email);
    }

    public PaginatedResponseDto<UserDto> getUsers(GenericSearchDto<UserDto> searchDto) {
        log.info("-- getUsers");
        Page<User> userList = userRepository.findAll(searchDto.getSpecification(), searchDto.getPageable());
        List<UserDto> userDtos = userMapper.toDtos(userList.getContent());

        return PaginatedResponseDto.<UserDto>builder()
                .page(searchDto.getPage())
                .size(userDtos.size())
                .sortingFields(Arrays.stream(searchDto.getSort())
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ")))
                .sortDirection(searchDto.getDir().name())
                .data(userDtos)
                .build();
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
        log.info("-- User {} is deleted", id);
    }

    public void updateUser(String id, UserDto userDto) {
        User newUser = User.builder()
                .id(id)
                .email(userDto.getEmail())
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(userDto.getPassword()))
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .userRole(userDto.getUserRole())
                .paymentMethod(userDto.getPaymentMethod())
                .build();
        userRepository.save(newUser);
        log.info("-- User {} is updated", id);
    }

    public String getUserBalanceById(String id) {
        log.info("-- getUserBalanceById");
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException(format("User with id = %s wasn't found", id)));
        return user.getPaymentMethod().getBalance();
    }

    public void updateUserBalanceById(String id, String balance) {
        log.info("-- updateUserBalanceById");
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException(format("User with id = %s wasn't found", id)));
        user.getPaymentMethod().setBalance(balance);
        userRepository.save(user);
    }

    private void assertUserDto(UserDto user, String msg) {
        if (user == null) {
            log.info("The body payload is missed");
            throw new IllegalArgumentException(msg);
        }
    }
}
