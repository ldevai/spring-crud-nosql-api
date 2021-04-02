package io.devai.tutorials.app.dao;

import io.devai.tutorials.app.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
