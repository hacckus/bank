package com.bank.profile.controller;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.service.impl.RegistrationServiceImp;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.stream.Collectors;

@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RegistrationServiceImp service;

    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void test_read_Success() throws Exception {
        RegistrationDto expectedDto = getOldDto();
        String expectedJson = jsonMapper.writeValueAsString(expectedDto);
        when(service.findById(expectedDto.getId())).thenReturn(expectedDto);

        this.mockMvc.perform(
                        get("/registration/read/{id}", expectedDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @DisplayName("чтение по id, негативный сценарий c несуществующим id")
    void test_read_EntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("notfound")).when(service).findById(0L);

        this.mockMvc.perform(
                        get("/registration/read/{id}", 0))
                .andExpect(status().is(404))
                .andExpect(content().string("notfound"));
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void test_create_Success() throws Exception {
        RegistrationDto expectedDto = getNewDto();
        String expectedJson = jsonMapper.writeValueAsString(expectedDto);

        when(service.save(expectedDto)).thenReturn(expectedDto);

        this.mockMvc.perform(
                        post("/registration/create")
                                .content(expectedJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @DisplayName("создание, негативный сценарий с неккоректными входными данными")
    void test_create_BadRequest() throws Exception {
        RegistrationDto expectedDto = getNewDto();

        when(service.save(expectedDto)).thenReturn(expectedDto);

        this.mockMvc.perform(
                        post("/registration/create")
                                .content(expectedDto.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("изменение по id, позитивный сценарий")
    void test_update_Success() throws Exception {
        RegistrationDto expectedDto = getNewDto();
        String expectedJson = jsonMapper.writeValueAsString(expectedDto);

        when(service.update(expectedDto.getId(), expectedDto)).thenReturn(expectedDto);


        this.mockMvc.perform(
                        put("/registration/update/{id}", expectedDto.getId())
                                .content(expectedJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

    }

    @Test
    @DisplayName("изменение по id , негативный сценарий c несуществующим id")
    void test_update_EntityNotFound() throws Exception {
        RegistrationDto expectedDto = getNewDto();
        String expectedJson = jsonMapper.writeValueAsString(expectedDto);

        doThrow(EntityNotFoundException.class).when(service).update(0L, expectedDto);

        this.mockMvc.perform(
                        put("/registration/update/{id}", 0)
                                .content(expectedJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    @DisplayName("изменение по id, негативный сценарий c неккоректным методом запроса (post)")
    void test_update_BadRequestMethod() throws Exception {
        RegistrationDto expectedDto = getNewDto();

        this.mockMvc.perform(
                        post("/registration/update/{id}", 0)
                                .content(jsonMapper.writeValueAsString(expectedDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(405));
    }

    @Test
    @DisplayName("чтение всех по списку ids, позитивный сценарий")
    void test_readAllById_Success() throws Exception {
        List<RegistrationDto> expectedDto = List.of(getOldDto(), getNewDto());
        String expectedDtoJson = jsonMapper.writeValueAsString(expectedDto);
        List<Long> expectedIds = List.of(getOldDto().getId(), getNewDto().getId());
        String expectedIdsParam = expectedIds.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        when(service.findAllById(expectedIds)).thenReturn(expectedDto);

        this.mockMvc.perform(
                        get("/registration/read/all")
                                .param("ids", expectedIdsParam))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedDtoJson));
    }

    @Test
    @DisplayName("чтение всех по списку ids, негативный сценарий c пустым списком ids")
    void test_readAllById_EntityNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(service).findAllById(List.of());

        this.mockMvc.perform(
                        get("/registration/read/all")
                                .param("ids", ""))
                .andExpect(status().is(404));
    }

    @Test
    @DisplayName("переход по несуществующему URL, негативный сценарий")
    void test_PathNotFound() throws Exception {
        this.mockMvc.perform(
                        get("/registration/"))
                .andExpect(status().is(404));
    }

    private static RegistrationDto getOldDto() {
        return new RegistrationDto(
                1L,
                "Country1",
                "Region1",
                "City1",
                "District1",
                "Locality1",
                "Street1",
                "HouseNumber1",
                "HouseBlock1",
                "FlatNumber1",
                1L);
    }

    private static RegistrationDto getNewDto() {
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