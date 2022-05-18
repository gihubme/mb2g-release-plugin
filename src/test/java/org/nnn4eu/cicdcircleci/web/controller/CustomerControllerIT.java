package org.nnn4eu.cicdcircleci.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nnn4eu.cicdcircleci.DataDtoGenerator;
import org.nnn4eu.cicdcircleci.service.CustomerService;
import org.nnn4eu.cicdcircleci.shared.Util;
import org.nnn4eu.cicdcircleci.web.model.ContactTypeE;
import org.nnn4eu.cicdcircleci.web.model.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.EnumMap;
import java.util.Random;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/*
Listen to HTTP requests: correct HTTP method and the request content type
Deserialise Input: Json to Dto
Validate Input: Dto validation
Business Logic calls: calling service
Serialise Output: Dto to Json
*/

@WebMvcTest(CustomerController.class)
class CustomerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
    }

    private static CustomerDto validDto;

    @BeforeEach
    void setUp() {
        validDto = DataDtoGenerator.generateCustomerDto();
    }

    @DisplayName("Get /getCustomer 200")
    @Test
    void getCustomer() throws Exception {
        given(customerService.getCustomerById(any(Long.class))).willReturn(validDto);

        // correct HTTP method and the correct request content type
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/" + validDto.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(validDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is(validDto.getFirstName())))
                .andReturn();

        // output serilization
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(validDto));
    }

    @DisplayName("Violations")
    @Test
    void whenInvalid_thenShouldGiveConstraintViolations() throws Exception {
        CustomerDto invalidDto = DataDtoGenerator.generateCustomerDto();
        invalidDto.setSecondName("B");
        invalidDto.setFirstName(null);
        invalidDto.setEmails(new EnumMap<>(ContactTypeE.class));
        given(customerService.getCustomerById(any(Long.class))).willReturn(invalidDto);

        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(invalidDto);
        assertThat(violations.size()).isEqualTo(3);

        // validation

        assertThat(violations)
                .anyMatch(Util.havingPropertyPath("firstName")
                        .and(Util.havingMessage("must not be blank")));
        assertThat(violations)
                .anyMatch(Util.havingPropertyPath("secondName")
                        .and(Util.havingMessage("size must be between 2 and 100")));

        assertThat(violations)
                .anyMatch(Util.havingPropertyPath("emails")
                        .and(Util.havingMessage(
                                "must not be empty")));
        /*assertThat(violations)
                .anyMatch(Util.havingPropertyPath("lastModifiedDate")
                        .and(Util.havingMessage(
                                "must be null")));
        assertThat(violations)
                .anyMatch(Util.havingPropertyPath("createdDate")
                        .and(Util.havingMessage(
                                "must be null")));*/

    }

    @DisplayName("POST/ customer created")
    @Test
    void handlePost() throws Exception {
        //given
        String dtoJson = objectMapper.writeValueAsString(validDto);
        Long idToIgnore = validDto.getId();
        validDto.setId(DataDtoGenerator.generateId(new Random()));

//        OffsetDateTime modifiedToIgnore = validDto.getLastModifiedDate();
//        OffsetDateTime createdToIgnore = validDto.getCreatedDate();
//        validDto.setCreatedDate(OffsetDateTime.now().minusHours(1L));
//        validDto.setLastModifiedDate(OffsetDateTime.now());

        given(customerService.saveNewCustomer(any())).willReturn(validDto);

        // correct HTTP method and the correct request content type
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/v1/customer/" + validDto.getId()))
                .andReturn();

        Assertions.assertThat(idToIgnore).isNotEqualTo(validDto.getId());

    }

    @DisplayName("POST/ throw violations")
    @Test
    void whenInvalid_thenThrowException() throws Exception {
        //given
        CustomerDto invalidDto = DataDtoGenerator.generateCustomerDto();
        invalidDto.setSecondName("B");
        invalidDto.setFirstName(null);
        invalidDto.setEmails(new EnumMap<>(ContactTypeE.class));

        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(invalidDto);
        assertThat(violations.size()).isEqualTo(3);

        ConstraintViolationException e = new ConstraintViolationException(violations);
        given(customerService.saveNewCustomer(invalidDto)).willThrow(e);

        String dtoJson = objectMapper.writeValueAsString(invalidDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())

                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[*].field",
                        Matchers.containsInAnyOrder("firstName", "secondName", "emails")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[*].message",
                        Matchers.containsInAnyOrder("must not be blank", "size must be between 2 and 100",
                                "must not be empty")))

                .andExpect(ResponseBodyMatchers.responseBody().containsError("firstName", "must not be blank"));

//                .andExpect(MockMvcResultMatchers.jsonPath("$[*]",
//                        Matchers.hasItem("Size.customerDto.secondName : size must be between 2 and 100")))

    }

    @DisplayName("PUT/ no content")
    @Test
    void handlePut() throws Exception {
        //given
        String dtoJson = objectMapper.writeValueAsString(validDto);
        given(customerService.updateCustomer(validDto.getId(), validDto)).willReturn(validDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customer/" + validDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        then(customerService).should().updateCustomer(any(), any());
    }

    @DisplayName("DELETE/ no content")
    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customer/" + validDto.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        then(customerService).should().deleteCustomer(any());
    }

}
