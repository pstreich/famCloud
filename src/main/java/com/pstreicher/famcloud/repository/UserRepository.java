package com.pstreicher.famcloud.repository;

import com.pstreicher.famcloud.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
}
