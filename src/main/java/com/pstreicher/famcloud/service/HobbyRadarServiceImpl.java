package com.pstreicher.famcloud.service;

import com.pstreicher.famcloud.domain.HobbyRadar;
import com.pstreicher.famcloud.domain.UserInfo;
import com.pstreicher.famcloud.dto.HobbyRadarDTO;
import com.pstreicher.famcloud.mapper.HobbyRadarMapper;
import com.pstreicher.famcloud.repository.HobbyRadarRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HobbyRadarServiceImpl implements HobbyRadarService {

    private final HobbyRadarRepository hobbyRadarRepository;

    public HobbyRadarServiceImpl(HobbyRadarRepository hobbyRadarRepository) {
        this.hobbyRadarRepository = hobbyRadarRepository;
    }

    public HobbyRadar addHobbyRadar(HobbyRadarDTO hobbyRadarDTO, UserInfo userInfo) {
        HobbyRadar hobbyRadar = HobbyRadarMapper.MAPPER.hobbyRadarDTOToHobbyRadar(hobbyRadarDTO);
        Optional<HobbyRadar> oldHobbyRadar = Optional.of(new HobbyRadar());
        if (userInfo.getHobbyRadar() != null) {
            oldHobbyRadar = hobbyRadarRepository.findById(userInfo.getHobbyRadar().getId());
        }
        oldHobbyRadar.ifPresent(radar -> hobbyRadar.setId(radar.getId()));
        return hobbyRadarRepository.save(hobbyRadar);
    }
}
