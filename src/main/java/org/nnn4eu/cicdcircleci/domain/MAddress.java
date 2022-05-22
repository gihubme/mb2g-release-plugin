package org.nnn4eu.cicdcircleci.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.nnn4eu.cicdcircleci.web.model.ContactTypeE;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MAddress {
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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "contactType")
    private ContactTypeE contactType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @NotBlank
    @Size(min = 2, max = 200)
    private String name;
    @NotBlank
    @Size(min = 2, max = 100)
    private String street;

    private String housNumber;
    @NotBlank
    @Size(min = 3, max = 100)
    private String zip;
    @NotBlank
    @Size(min = 2, max = 100)
    private String city;

    public String getFormattedAddress() {
        return this.street + " " + this.housNumber + ", " + this.zip + " " + this.city;
    }
}
