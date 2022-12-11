package com.pstreicher.famcloud.repository;

import com.pstreicher.famcloud.domain.HobbyRadar;
import com.pstreicher.famcloud.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface HobbyRadarRepository extends CrudRepository<HobbyRadar, Long> {
    HobbyRadar findByUserInfoId(Long userInfoId);
}
