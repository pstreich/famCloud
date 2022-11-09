package com.pstreicher.famcloud.repository;

import com.pstreicher.famcloud.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
