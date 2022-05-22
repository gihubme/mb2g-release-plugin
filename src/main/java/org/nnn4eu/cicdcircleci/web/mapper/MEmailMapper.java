package org.nnn4eu.cicdcircleci.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.nnn4eu.cicdcircleci.domain.MEmail;
import org.nnn4eu.cicdcircleci.web.model.MEmailDto;

@Mapper(componentModel = "spring",
        uses = {DateMapper.class})
public interface MEmailMapper {

    MEmailMapper INSTANCE = Mappers.getMapper(MEmailMapper.class);

    MEmail dtoToMEmail(MEmailDto ts);

    MEmailDto meEmailToDto(MEmail ts);
}
