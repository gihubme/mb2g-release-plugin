package org.nnn4eu.cicdcircleci.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.nnn4eu.cicdcircleci.web.model.ContactTypeE;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;
    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;
    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    @Size(min = 2, max = 100)
    private String firstName;
    @Size(min = 2, max = 100)
    private String secondName;

    @Builder
    public Customer(Timestamp createdDate, Timestamp lastModifiedDate, String firstName, String secondName) {
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public void setAddresses(Map<ContactTypeE, MAddress> addresses) {
        addresses.keySet().stream().forEach(key -> addAddress(key, addresses.get(key)));
    }

    public void addAddress(ContactTypeE type, MAddress address) {
        if (this.addresses == null) {
            this.addresses = new HashMap<>();
        }
        address.setCustomer(this);
        this.addresses.put(type, address);
    }

    public void setEmails(Map<ContactTypeE, MEmail> emails) {
        emails.keySet().stream().forEach(key -> addEmail(key, emails.get(key)));
    }

    public void addEmail(ContactTypeE type, MEmail email) {
        if (this.emails == null) {
            this.emails = new HashMap<>();
        }
        email.setCustomer(this);
        this.emails.put(type, email);
    }

    public void setPhones(Map<ContactTypeE, MPhone> phones) {
        for (Map.Entry<ContactTypeE, MPhone> entry : phones.entrySet()) {
            this.addPhone(entry.getKey(), entry.getValue());
        }
    }

    public void addPhone(ContactTypeE type, MPhone phone) {
        if (this.phones == null) {
            this.phones = new HashMap<>();
        }
        phone.setCustomer(this);
        this.phones.put(type, phone);
    }

    @MapKeyEnumerated(EnumType.STRING)
    @OneToMany(targetEntity = MAddress.class,
               orphanRemoval = true,
               fetch = FetchType.LAZY,
               mappedBy = "customer",
               cascade = {CascadeType.ALL})
    @NotEmpty
    private Map<ContactTypeE, MAddress> addresses;


    @MapKeyEnumerated(EnumType.STRING)
    @OneToMany(targetEntity = MEmail.class,
               orphanRemoval = true,
               fetch = FetchType.LAZY,
               mappedBy = "customer",
               cascade = {CascadeType.ALL})
    @NotEmpty
    Map<ContactTypeE, MEmail> emails;

    @MapKeyEnumerated(EnumType.STRING)
    @OneToMany(targetEntity = MPhone.class,
               orphanRemoval = true,
               fetch = FetchType.LAZY,
               mappedBy = "customer",
               cascade = {CascadeType.ALL})
    @Size(min = 0, max = 10)
    Map<ContactTypeE, MPhone> phones;

}
