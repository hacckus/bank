package com.bank.profile.controller;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.service.impl.AuditServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuditController.class)
class AuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AuditServiceImpl service;

    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void test_read_Success() throws Exception {

        AuditDto expectedDto = getOldDto();
        String expectedJson = jsonMapper.writeValueAsString(expectedDto);
        when(service.findById(expectedDto.getId())).thenReturn(expectedDto);

        this.mockMvc.perform(
                        get("/audit/{id}", expectedDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @DisplayName("чтение по id, негативный сценарий c несуществующим id")
    void test_read_EntityNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(service).findById(0L);

        this.mockMvc.perform(
                        get("/audit/{id}", 0))
                .andExpect(status().is(404));
    }

    private static AuditDto getOldDto() {
        return new AuditDto(
                1L,
                "entityType1",
                "operationType1",
                "createdBy1",
                "modifiedBy1",
                Timestamp.valueOf(
                        LocalDateTime.of(
                                LocalDate.of(2000, 1, 1),
                                LocalTime.of(1, 1, 1)
                        )
                ),
                Timestamp.valueOf(
                        LocalDateTime.of(
                                LocalDate.of(2000, 1, 1),
                                LocalTime.of(1, 1, 1)
                        )
                ),
                "newEntityJson1",
                "EntityJson");
    }
}