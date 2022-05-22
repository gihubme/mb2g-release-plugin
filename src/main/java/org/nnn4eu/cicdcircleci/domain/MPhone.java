package org.nnn4eu.cicdcircleci.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.nnn4eu.cicdcircleci.web.model.ContactTypeE;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MPhone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @GeneratedValue(generator = "phone-sequence-gen", strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(name = "phone-sequence-gen", sequenceName = "phone-sequence-gen", allocationSize = 100,
//                       initialValue = 9) //context doesn't load!!

//    @GenericGenerator(
//            name = "phone-sequence-gen",
//            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
//            parameters = {
//                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "phone_sequence"),
//                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "10"),
//                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "50")
//            }
//    )//maven verify doesn't work
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

    @Size(min = 7, max = 16)
    private String phone;
}
