package org.nnn4eu.cicdcircleci.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.nnn4eu.cicdcircleci.domain.MAddress;
import org.nnn4eu.cicdcircleci.web.model.MAddressDto;

@Mapper(componentModel = "spring",
        uses = {DateMapper.class})
public interface MAddressMapper {

    MAddressMapper INSTANCE = Mappers.getMapper(MAddressMapper.class);

    MAddress dtoToMaddress(MAddressDto addressDto);

    MAddressDto maddressToDto(MAddress address);
}
