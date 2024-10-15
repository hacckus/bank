package com.bank.profile.controller;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.service.impl.PassportServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PassportController.class)
class PassportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PassportServiceImp service;

    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void test_read_Success() throws Exception {
        PassportDto expectedDto = getOldDto();
        String expectedJson = jsonMapper.writeValueAsString(expectedDto);
        when(service.findById(expectedDto.getId())).thenReturn(expectedDto);

        this.mockMvc.perform(
                        get("/passport/read/{id}", expectedDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @DisplayName("чтение по id, негативный сценарий c несуществующим id")
    void test_read_EntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("notfound")).when(service).findById(0L);

        this.mockMvc.perform(
                        get("/passport/read/{id}", 0))
                .andExpect(status().is(404))
                .andExpect(content().string("notfound"));
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void test_create_Success() throws Exception {
        PassportDto expectedDto = getNewDto();
        String expectedJson = jsonMapper.writeValueAsString(expectedDto);

        when(service.save(expectedDto)).thenReturn(expectedDto);

        this.mockMvc.perform(
                        post("/passport/create")
                                .content(expectedJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @DisplayName("создание, негативный сценарий с неккоректными входными данными")
    void test_create_BadRequest() throws Exception {
        PassportDto expectedDto = getNewDto();

        when(service.save(expectedDto)).thenReturn(expectedDto);

        this.mockMvc.perform(
                        post("/passport/create")
                                .content(expectedDto.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("изменение по id, позитивный сценарий")
    void test_update_Success() throws Exception {
        PassportDto expectedDto = getNewDto();
        String expectedJson = jsonMapper.writeValueAsString(expectedDto);

        when(service.update(expectedDto.getId(), expectedDto)).thenReturn(expectedDto);


        this.mockMvc.perform(
                        put("/passport/update/{id}", expectedDto.getId())
                                .content(expectedJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

    }

    @Test
    @DisplayName("изменение по id , негативный сценарий c несуществующим id")
    void test_update_EntityNotFound() throws Exception {
        PassportDto expectedDto = getNewDto();
        String expectedJson = jsonMapper.writeValueAsString(expectedDto);

        doThrow(EntityNotFoundException.class).when(service).update(0L, expectedDto);

        this.mockMvc.perform(
                        put("/passport/update/{id}", 0)
                                .content(expectedJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    @DisplayName("изменение по id, негативный сценарий c неккоректным методом запроса (post)")
    void test_update_BadRequestMethod() throws Exception {
        PassportDto expectedDto = getNewDto();

        this.mockMvc.perform(
                        post("/passport/update/{id}", 0)
                                .content(jsonMapper.writeValueAsString(expectedDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(405));
    }

    @Test
    @DisplayName("чтение всех по списку ids, позитивный сценарий")
    void test_readAllById_Success() throws Exception {
        List<PassportDto> expectedDto = List.of(getOldDto(), getNewDto());
        String expectedDtoJson = jsonMapper.writeValueAsString(expectedDto);
        List<Long> expectedIds = List.of(getOldDto().getId(), getNewDto().getId());
        String expectedIdsParam = expectedIds.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        when(service.findAllById(expectedIds)).thenReturn(expectedDto);

        this.mockMvc.perform(
                        get("/passport/read/all")
                                .param("ids", expectedIdsParam))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedDtoJson));
    }

    @Test
    @DisplayName("чтение всех по списку ids, негативный сценарий c пустым списком ids")
    void test_readAllById_EntityNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(service).findAllById(List.of());

        this.mockMvc.perform(
                        get("/passport/read/all")
                                .param("ids", ""))
                .andExpect(status().is(404));
    }

    @Test
    @DisplayName("переход по несуществующему URL, негативный сценарий")
    void test_PathNotFound() throws Exception {
        this.mockMvc.perform(
                        get("/passport/"))
                .andExpect(status().is(404));
    }

    private static PassportDto getOldDto() {
        return new PassportDto(
                1L,
                1234,
                56789L,
                "lastName1",
                "firstName1",
                "middleName1",
                "male",
                LocalDate.of(2000, 1, 1),
                "birthPlace1",
                "issuedBy1",
                LocalDate.of(20000, 1, 2),
                123456,
                LocalDate.of(20000, 1, 3),
                getRegistrationDto()
        );
    }

    private static PassportDto getNewDto() {
        return new PassportDto(
                1L,
                12345,
                567890L,
                "lastName2",
                "firstName2",
                "middleName2",
                "female",
                LocalDate.of(2000, 1, 2),
                "birthPlace2",
                "issuedBy2",
                LocalDate.of(20000, 1, 3),
                1234567,
                LocalDate.of(20000, 1, 4),
                getRegistrationDto()
        );
    }

    private static RegistrationDto getRegistrationDto() {
        return new RegistrationDto(
                1L,
                "Country2",
                "Region2",
                "City2",
                "District2",
                "Locality2",
                "Street2",
                "HouseNumber2",
                "HouseBlock2",
                "FlatNumber2",
                1L);
    }

}