package org.nnn4eu.cicdcircleci.web.mapper;

import lombok.RequiredArgsConstructor;
import org.nnn4eu.cicdcircleci.domain.MPhone;
import org.nnn4eu.cicdcircleci.web.model.MPhoneDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MPhoneMapper {
    private final DateMapper dateMapper;

    public MPhone dtoToMPhone(MPhoneDto ts) {
        if (ts != null) {
            return MPhone.builder()
                    .contactType(ts.getContactType())
                    .phone(ts.getCountryCode() + ts.getNationalNumber().replace(" ", ""))
                    .build();
        } else {
            return null;
        }
    }

    public MPhoneDto mphoneToDto(MPhone ts) {
        if (ts != null) {
            return MPhoneDto.builder()
                    .lastModifiedDate(dateMapper.asOffsetDateTime(ts.getLastModifiedDate()))
                    .createdDate(dateMapper.asOffsetDateTime(ts.getCreatedDate()))
                    .version(ts.getVersion())
                    .id(ts.getId())
                    .nationalNumber(ts.getPhone().substring(3))
                    .countryCode(ts.getPhone().substring(0, 3))
                    .contactType(ts.getContactType())
                    .build();
        } else {
            return null;
        }
    }

}
