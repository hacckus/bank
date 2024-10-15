package com.bank.antifraud.controller;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.service.AuditService;
import com.bank.antifraud.service.common.ExceptionReturner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(AuditController.class)
public class AuditControllerTest {
    @MockBean
    private AuditService auditService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ExceptionReturner exceptionReturner;

    @DisplayName("поиск по id, позитивный сценарий")
    @Test
    void readFindByIdTest() throws Exception {
        Long id = 1L;
        AuditDto auditDto = new AuditDto();
        auditDto.setId(id);
        String auditDtoJson = objectMapper.writeValueAsString(auditDto);
        when(auditService.findById(id)).thenReturn(auditDto);
        mockMvc.perform(get("/audit/{id}",id))
                .andExpect(status().isOk())
                .andExpect(content().json(auditDtoJson));
    }

    @DisplayName("поиск по id, негативный сценарий")
    @Test
    void readFindByIdTrowsNotFoundException() throws Exception {
        when(auditService.findById(anyLong())).thenThrow(exceptionReturner
                .getEntityNotFoundException("Not found"));
        mockMvc.perform(get("/audit/{id}",anyLong()))
                .andExpect(status().isNotFound());
    }
}
