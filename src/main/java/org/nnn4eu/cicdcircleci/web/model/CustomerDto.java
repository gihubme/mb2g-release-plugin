package org.nnn4eu.cicdcircleci.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long version;

    @NotBlank
    @Size(min = 2, max = 100)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 100)
    private String secondName;

    @NotEmpty
    private Map<ContactTypeE, @Valid MAddressDto> addresses;

    @NotEmpty
    private Map<ContactTypeE, @Valid MEmailDto> emails;

    @Size(min = 0, max = 10)
    private Map<ContactTypeE, @Valid MPhoneDto> phones;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime createdDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime lastModifiedDate;
}
