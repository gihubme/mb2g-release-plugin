package org.nnn4eu.cicdcircleci.web.mapper;

import org.mapstruct.Mapper;
import org.nnn4eu.cicdcircleci.domain.MEmail;
import org.nnn4eu.cicdcircleci.web.model.MEmailDto;

@Mapper(uses = {DateMapper.class})
public interface MEmailMapper {
    MEmailDto mEmailDtoToMEmeail(MEmail ts);

    MEmail mEmailDtoToMEmail(MEmailDto ts);
}
