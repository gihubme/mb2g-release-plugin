package org.nnn4eu.cicdcircleci.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MAddressDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime createdDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime lastModifiedDate;
}
