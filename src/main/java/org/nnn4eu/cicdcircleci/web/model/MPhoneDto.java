package org.nnn4eu.cicdcircleci.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nnn4eu.cicdcircleci.shared.AppConstant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MPhoneDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long version;
    @NotBlank
    @Pattern(
            message = "National number can have two optional groups followed by mandatory group. Groups can be separated by spaces. 1st group should have - 1 to 4 digits, 2nd - 3 to 4 and last must have 4 digits",
            regexp = AppConstant.nationalNumber)
    private String nationalNumber;

    @NotBlank
    @Pattern(
            message = "Country code should be between 1 to 3 digits prefixed with '+' symbol, followed by space or no space",
            regexp = AppConstant.countryCode)
    private String countryCode;

    @NotNull
    private ContactTypeE contactType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime createdDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime lastModifiedDate;
}

