package com.pstreicher.famcloud.service;

import com.pstreicher.famcloud.domain.HobbyRadar;
import com.pstreicher.famcloud.domain.UserInfo;
import com.pstreicher.famcloud.dto.HobbyRadarDTO;

public interface HobbyRadarService {
    HobbyRadar addHobbyRadar(HobbyRadarDTO hobbyRadar, UserInfo userInfo);
}
