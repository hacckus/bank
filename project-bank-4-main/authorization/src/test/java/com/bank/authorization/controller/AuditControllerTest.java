package com.bank.authorization.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.bank.authorization.dto.AuditDto;
import com.bank.authorization.service.AuditService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = AuditController.class)
@DisplayName("Audit controller")
public class AuditControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AuditService service;

    @Test
    @DisplayName("GET запрос для получения одной сущности, позитивный сценарий")
    public void theAuditDtoShouldBeFind() throws Exception {
        AuditDto auditDto = new AuditDto();
        auditDto.setId(1L);
        auditDto.setCreatedBy("Jhon");
        String auditDtoAsString = objectMapper.writeValueAsString(auditDto);

        when(this.service.findById(auditDto.getId())).thenReturn(auditDto);

        mockMvc.perform(get("/audit/{id}", auditDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(auditDtoAsString));
    }

    @Test
    @DisplayName("GET запрос для получения одной сущности, негативный сценрий (код 404)")
    public void theMethodReadShouldAnswerWithStatusCode404() throws Exception {
        when(this.service.findById(anyLong())).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/audit/{id}", anyLong()))
                .andExpect(status().isNotFound());
    }
}
