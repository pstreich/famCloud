package com.pstreicher.famcloud.mapper;

import com.pstreicher.famcloud.dto.HobbyRadarDTO;
import com.pstreicher.famcloud.domain.HobbyRadar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HobbyRadarMapper {

    HobbyRadarMapper MAPPER = Mappers.getMapper(HobbyRadarMapper.class);

    @Mapping(source = "color", target = "color")
    HobbyRadar hobbyRadarDTOToHobbyRadar(HobbyRadarDTO hobbyRadar);

}
