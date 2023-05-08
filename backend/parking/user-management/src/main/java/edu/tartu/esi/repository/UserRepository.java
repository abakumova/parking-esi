package edu.tartu.esi.repository;

import edu.tartu.esi.dto.UserDto;
import edu.tartu.esi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    boolean existsByEmail(String email);

    Optional<User> findUserById(String id);

    Optional<User> findByEmail(String email);
    Optional<User> findUserByEmail(String email);

    @Query("SELECT e from User e")
    Page<User> findAll(Specification<UserDto> specification, Pageable pageable);
}