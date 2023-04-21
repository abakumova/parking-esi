package edu.tartu.esi.repository;

import edu.tartu.esi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    long countAllByEmail(String email);
}