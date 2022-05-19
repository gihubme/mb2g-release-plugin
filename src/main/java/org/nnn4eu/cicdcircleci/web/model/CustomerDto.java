package org.nnn4eu.cicdcircleci.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto implements Serializable {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 100)
    private String secondName;

    @NotEmpty
    private Map<ContactTypeE, MAddressDto> addresses;

    @NotEmpty
    private Map<ContactTypeE, MEmailDto> emails;

    @Size(min = 0, max = 10)
    private Map<ContactTypeE, MPhoneDto> phones;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime createdDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime lastModifiedDate;
}
