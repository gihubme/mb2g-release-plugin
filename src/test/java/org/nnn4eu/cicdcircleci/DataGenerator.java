package org.nnn4eu.cicdcircleci;

import org.nnn4eu.cicdcircleci.domain.Customer;
import org.nnn4eu.cicdcircleci.domain.MAddress;
import org.nnn4eu.cicdcircleci.domain.MEmail;
import org.nnn4eu.cicdcircleci.domain.MPhone;
import org.nnn4eu.cicdcircleci.shared.Util;
import org.nnn4eu.cicdcircleci.web.model.ContactTypeE;

import java.util.Map;

public class DataGenerator {
    public static Customer createCustomer(String first) {
        String second = "Thompson-" + Util.randomString(5);
        Customer customerToSave = new Customer();
        MAddress addr = createMAddress(first + " " + second);
        customerToSave.setFirstName(first);
        customerToSave.setSecondName(second);
        customerToSave.setPhones(Map.of(ContactTypeE.PRIMARY,
                MPhone.builder().phone("+4498775673").contactType(ContactTypeE.PRIMARY).build()));
        customerToSave.setEmails(Map.of(ContactTypeE.PRIMARY,
                MEmail.builder().email(first + "." + second + "@gmail.com").contactType(ContactTypeE.PRIMARY).build()));
        customerToSave.setAddresses(Map.of(ContactTypeE.PRIMARY, addr));

        return customerToSave;
    }

    public static MAddress createMAddress(String name) {
        return MAddress.builder()
                .name(name)
                .city("City-" + Util.randomString(5))
                .housNumber("66")
                .street("Street-" + Util.randomString(4))
                .zip(Util.randomString(5))
                .contactType(ContactTypeE.PRIMARY)
                .build();
    }
}
