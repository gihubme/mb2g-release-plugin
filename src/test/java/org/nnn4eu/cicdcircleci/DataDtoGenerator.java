package org.nnn4eu.cicdcircleci;

import org.nnn4eu.cicdcircleci.shared.Util;
import org.nnn4eu.cicdcircleci.web.model.*;

import java.time.OffsetDateTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class DataDtoGenerator {

    public static CustomerDto generateCustomerDto(String first, String second, boolean withDatesAndId) {
        CustomerDto dto = generateCustomerDto(withDatesAndId);
        dto.setFirstName(first);
        dto.setSecondName(second);
        return dto;
    }

    public static void removeDatesAndIds(CustomerDto dto) {
        dto.setId(null);
        dto.setCreatedDate(null);
        dto.setLastModifiedDate(null);
        dto.getPhones().values().stream().forEach(a -> a.setId(null));
        dto.getEmails().values().stream().forEach(a -> a.setId(null));
        dto.getAddresses().values().stream().forEach(a -> a.setId(null));

        dto.getPhones().values().stream().forEach(a -> {
            a.setCreatedDate(null);
            a.setLastModifiedDate(null);
        });
        dto.getEmails().values().stream().forEach(a -> {
            a.setCreatedDate(null);
            a.setLastModifiedDate(null);
        });
        dto.getAddresses().values().stream().forEach(a -> {
            a.setCreatedDate(null);
            a.setLastModifiedDate(null);
        });
    }

    public static CustomerDto generateCustomerDto(boolean withDatesAndId) {
        Random rand = new Random();
        String ran = "-" + Util.randomString(3);
        CustomerDto customerDto = CustomerDto.builder()
                .firstName("Rony" + ran)
                .secondName("Kickly" + ran)
                .addresses(new EnumMap<>(
                        Map.of(
                                ContactTypeE.PRIMARY, generateMAddressDto(rand, ContactTypeE.PRIMARY),
                                ContactTypeE.OFFICE, generateMAddressDto(rand, ContactTypeE.OFFICE),
                                ContactTypeE.DELIVERY, generateMAddressDto(rand, ContactTypeE.DELIVERY)
                        )))
                .emails(new EnumMap<>(
                        Map.of(
                                ContactTypeE.PRIMARY, generateMEmail(rand, ContactTypeE.PRIMARY),
                                ContactTypeE.OFFICE, generateMEmail(rand, ContactTypeE.OFFICE)
                        )))
                .phones(new EnumMap<>(
                        Map.of(
                                ContactTypeE.PRIMARY, generateMPhone(rand, ContactTypeE.PRIMARY),
                                ContactTypeE.OFFICE, generateMPhone(rand, ContactTypeE.OFFICE)
                        )))
                .id(generateId(rand))
                .lastModifiedDate(OffsetDateTime.now().minusHours(6L))
                .createdDate(OffsetDateTime.now().minusDays(2L))
                .build();
        if (!withDatesAndId) removeDatesAndIds(customerDto);
        return customerDto;
    }

    public static Long generateId(Random rand) {
        return (rand.nextLong() & Long.MAX_VALUE); // always positive
    }

    public static MAddressDto generateMAddressDto(Random rand, ContactTypeE type) {
        return MAddressDto.builder()
                .city("Rom" + Util.randomString(3)).housNumber("9oL")
                .name("Rony Kickly" + Util.randomString(3))
                .street("Peerk" + Util.randomString(3) + "Str.")
                .zip("89" + Util.randomString(3))
                .id(generateId(rand))
                .lastModifiedDate(OffsetDateTime.now().minusHours(6L))
                .createdDate(OffsetDateTime.now().minusDays(2L))
                .contactType(type)
                .build();
    }

    public static MEmailDto generateMEmail(Random rand, ContactTypeE type) {
        return MEmailDto.builder()
                .email("rony.kickly" + Util.randomString(3) + "@bpm.com")
                .id(generateId(rand))
                .lastModifiedDate(OffsetDateTime.now().minusHours(6L))
                .createdDate(OffsetDateTime.now().minusDays(2L))
                .contactType(type)
                .build();
    }

    public static MPhoneDto generateMPhone(Random ran, ContactTypeE type) {

        String number = ran.ints(1000, 99999).findFirst().getAsInt() + ""
                + ran.ints(0, 999999).findFirst().getAsInt();
        return MPhoneDto.builder()
                .nationalNumber(number).countryCode("+" + ran.ints(10, 99).findFirst().getAsInt())
                .id(generateId(ran))
                .lastModifiedDate(OffsetDateTime.now().minusHours(6L))
                .createdDate(OffsetDateTime.now().minusDays(2L))
                .contactType(type)
                .build();
    }
}
