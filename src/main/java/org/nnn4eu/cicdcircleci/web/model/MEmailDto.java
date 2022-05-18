package org.nnn4eu.cicdcircleci.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nnn4eu.cicdcircleci.shared.AppConstant;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MEmailDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email is not valid", regexp = AppConstant.emailRegex)
    @Size(min = 3, max = 100)
    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime createdDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime lastModifiedDate;
}
