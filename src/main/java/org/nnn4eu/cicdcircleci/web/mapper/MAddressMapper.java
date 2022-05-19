package org.nnn4eu.cicdcircleci.web.mapper;

import org.mapstruct.Mapper;
import org.nnn4eu.cicdcircleci.domain.MAddress;
import org.nnn4eu.cicdcircleci.web.model.MAddressDto;

@Mapper(componentModel = "spring",
        uses = {DateMapper.class})
public interface MAddressMapper {
    MAddress maddressDtoToMaddress(MAddressDto addressDto);

    MAddressDto MAddressToMAddressDto(MAddress address);
}
