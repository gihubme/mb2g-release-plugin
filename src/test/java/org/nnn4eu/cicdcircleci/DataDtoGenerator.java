package org.nnn4eu.cicdcircleci;

import org.nnn4eu.cicdcircleci.shared.Util;
import org.nnn4eu.cicdcircleci.web.model.*;

import java.time.OffsetDateTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class DataDtoGenerator {

    public static CustomerDto generateCustomerDto() {
        Random rand = new Random();
        String ran = "-" + Util.randomString(3);
        return CustomerDto.builder()
                .firstName("Rony" + ran)
                .secondName("Kickly" + ran)
                .addresses(new EnumMap<>(
                        Map.of(
                                ContactTypeE.PRIMARY, generateMAddressDto(rand),
                                ContactTypeE.OFFICE, generateMAddressDto(rand),
                                ContactTypeE.DELIVERY, generateMAddressDto(rand)
                        )))
                .emails(new EnumMap<>(
                        Map.of(
                                ContactTypeE.PRIMARY, generateMEmail(rand),
                                ContactTypeE.OFFICE, generateMEmail(rand)
                        )))
                .phones(new EnumMap<>(
                        Map.of(
                                ContactTypeE.PRIMARY, generateMPhone(rand),
                                ContactTypeE.OFFICE, generateMPhone(rand)
                        )))
                .id(generateId(rand))
                .lastModifiedDate(OffsetDateTime.now().minusHours(6L))
                .createdDate(OffsetDateTime.now().minusDays(2L))
                .build();
    }

    public static Long generateId(Random rand) {
        return (rand.nextLong() & Long.MAX_VALUE); // always positive
    }

    public static MAddressDto generateMAddressDto(Random rand) {
        return MAddressDto.builder()
                .city("Rom" + Util.randomString(3)).housNumber("9oL")
                .name("Rony Kickly" + Util.randomString(3))
                .street("Peerk" + Util.randomString(3) + "Str.")
                .zip("89" + Util.randomString(3))
                .id(generateId(rand))
                .lastModifiedDate(OffsetDateTime.now().minusHours(6L))
                .createdDate(OffsetDateTime.now().minusDays(2L))
                .build();
    }

    public static MEmailDto generateMEmail(Random rand) {
        return MEmailDto.builder()
                .email("rony.kickly" + Util.randomString(3) + "@bpm.com")
                .id(generateId(rand))
                .lastModifiedDate(OffsetDateTime.now().minusHours(6L))
                .createdDate(OffsetDateTime.now().minusDays(2L))
                .build();
    }

    public static MPhoneDto generateMPhone(Random ran) {

        String number = ran.ints(9999, 999999).findFirst().getAsInt() + ""
                + ran.ints(0, 999999).findFirst().getAsInt();
        return MPhoneDto.builder()
                .nationalNumber(number).countryCode("+" + ran.ints(10, 99).findFirst().getAsInt())
                .id(generateId(ran))
                .lastModifiedDate(OffsetDateTime.now().minusHours(6L))
                .createdDate(OffsetDateTime.now().minusDays(2L))
                .build();
    }
}
