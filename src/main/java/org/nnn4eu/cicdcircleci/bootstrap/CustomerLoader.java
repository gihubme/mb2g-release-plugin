package org.nnn4eu.cicdcircleci.bootstrap;

import lombok.RequiredArgsConstructor;
import org.nnn4eu.cicdcircleci.domain.Customer;
import org.nnn4eu.cicdcircleci.domain.MAddress;
import org.nnn4eu.cicdcircleci.domain.MEmail;
import org.nnn4eu.cicdcircleci.domain.MPhone;
import org.nnn4eu.cicdcircleci.repository.CustomerRepository;
import org.nnn4eu.cicdcircleci.shared.Util;
import org.nnn4eu.cicdcircleci.web.model.ContactTypeE;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Random;

@Service
@ConditionalOnProperty(
        value = "dataloading.enabled",
        havingValue = "true",
        matchIfMissing = false)
@RequiredArgsConstructor
public class CustomerLoader implements CommandLineRunner {
    private final CustomerRepository customerRepository;

    @Value("${dataloading.amount}")
    Integer amount;

    @Override
    public void run(String... args) throws Exception {
        loadCustomerObjects(amount);
    }

    private void loadCustomerObjects(int num) {
        Random rand = new Random();
        if (customerRepository.count() == 0) {
            for (int i = 0; i < num; i++) {
                String first = "Jo-" + Util.randomString(5);
                String second = "Thompson-" + Util.randomString(5);
                Customer customerToSave = new Customer();
                customerToSave.setFirstName(first);
                customerToSave.setSecondName(second);
                customerToSave.setPhones(createPhones(rand));
                customerToSave.setEmails(createEmails(first, rand));
                customerToSave.setAddresses(createAddresses(first + " " + second, rand));
                customerRepository.save(customerToSave);
            }
        }
    }

    private EnumMap<ContactTypeE, MEmail> createEmails(String name, Random rand) {
        EnumMap<ContactTypeE, MEmail> emails = new EnumMap<>(ContactTypeE.class);
        emails.put(ContactTypeE.PRIMARY, getEmail(name, ContactTypeE.PRIMARY));

        if (rand.nextBoolean()) {
            emails.put(ContactTypeE.PERSONAL, getEmail(name, ContactTypeE.PERSONAL));
        }

        if (rand.nextBoolean()) {
            emails.put(ContactTypeE.OFFICE, getEmail(name, ContactTypeE.OFFICE));
        }

        if (rand.nextBoolean()) {
            emails.put(ContactTypeE.BILLING, getEmail(name, ContactTypeE.BILLING));
        }
        if (rand.nextBoolean()) {
            emails.put(ContactTypeE.DELIVERY, getEmail(name, ContactTypeE.DELIVERY));
        }
        return emails;
    }

    private MEmail getEmail(String name, ContactTypeE type) {
        return MEmail.builder()
                .email(
                        name.replace(" ", "") +
                                Util.randomString(3) + "@" +
                                Util.randomString(3) + ".com")
                .contactType(type)
                .build();
    }

    private EnumMap<ContactTypeE, MAddress> createAddresses(String name, Random rand) {
        EnumMap<ContactTypeE, MAddress> addresses = new EnumMap<>(ContactTypeE.class);
        addresses.put(ContactTypeE.PRIMARY, getAddress(name, rand, ContactTypeE.PRIMARY));

        if (rand.nextBoolean()) {
            addresses.put(ContactTypeE.PERSONAL, getAddress(name, rand, ContactTypeE.PERSONAL));
        }

        if (rand.nextBoolean()) {
            addresses.put(ContactTypeE.OFFICE, getAddress(name, rand, ContactTypeE.OFFICE));
        }

        if (rand.nextBoolean()) {
            addresses.put(ContactTypeE.BILLING, getAddress(name, rand, ContactTypeE.BILLING));
        }
        if (rand.nextBoolean()) {
            addresses.put(ContactTypeE.DELIVERY, getAddress(name, rand, ContactTypeE.DELIVERY));
        }

        return addresses;
    }

    private MAddress getAddress(String name, Random rand, ContactTypeE type) {
        return MAddress.builder()
                .name(name)
                .city("City-" + Util.randomString(5))
                .housNumber(rand.nextInt() + "")
                .street("Street-" + Util.randomString(4))
                .zip(Util.randomString(5))
                .contactType(type)
                .build();
    }

    private EnumMap<ContactTypeE, MPhone> createPhones(Random rand) {
        EnumMap<ContactTypeE, MPhone> phones = new EnumMap<>(ContactTypeE.class);
        phones.put(ContactTypeE.PRIMARY, getPhone(rand, ContactTypeE.PRIMARY));

        if (rand.nextBoolean()) {
            phones.put(ContactTypeE.PERSONAL, getPhone(rand, ContactTypeE.PERSONAL));
        }

        if (rand.nextBoolean()) {
            phones.put(ContactTypeE.OFFICE, getPhone(rand, ContactTypeE.OFFICE));
        }

        if (rand.nextBoolean()) {
            phones.put(ContactTypeE.BILLING, getPhone(rand, ContactTypeE.BILLING));
        }
        if (rand.nextBoolean()) {
            phones.put(ContactTypeE.DELIVERY, getPhone(rand, ContactTypeE.DELIVERY));
        }
        return phones;
    }

    private MPhone getPhone(Random rand, ContactTypeE type) {

        String tStr = "";
        for (int i = 0; rand.nextBoolean() && i < 3; i++) {
            int t = rand.ints(0, 99).findFirst().getAsInt();
            tStr = (t > 0) ? t + tStr : tStr;
        }

        tStr = rand.ints(1000, 9999).findFirst().getAsInt() + tStr;
        return MPhone.builder()
                .phone("+49" + tStr)
                .contactType(type)
                .build();
    }
}
