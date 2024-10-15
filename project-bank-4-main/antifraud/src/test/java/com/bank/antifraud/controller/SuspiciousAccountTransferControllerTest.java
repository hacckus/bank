package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
import com.bank.antifraud.service.common.ExceptionReturner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.any;

@WebMvcTest(SuspiciousAccountTransferController.class)
public class SuspiciousAccountTransferControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SuspiciousAccountTransferService suspiciousAccountTransferService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ExceptionReturner exceptionReturner;

    @DisplayName("чтение по id, позитивный сценарий")
    @Test
    void readTest() throws Exception {
        Long id = 1L;
        SuspiciousAccountTransferDto suspiciousAccountTransferDto = new SuspiciousAccountTransferDto();
        suspiciousAccountTransferDto.setId(id);
        String suspiciousAccountTransferJson = objectMapper.writeValueAsString(suspiciousAccountTransferDto);
        when(suspiciousAccountTransferService.findById(id)).thenReturn(suspiciousAccountTransferDto);
        mockMvc.perform(get("/suspicious/account/transfer/{id}",id))
                .andExpect(status().isOk())
                .andExpect(content().json(suspiciousAccountTransferJson));
    }

    @DisplayName("Чтение по id, негативный сценарий")
    @Test
    void readFindByIdThrowsNotFoundExceptionTest() throws Exception {
        when(suspiciousAccountTransferService.findById(anyLong()))
                .thenThrow(exceptionReturner.getEntityNotFoundException("Not found"));
        mockMvc.perform(get("/suspicious/account/transfer/{id}",anyLong()))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Чтение по id всех пользователей, позитивный сценарий")
    @Test
    void readAllTest() throws Exception {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        SuspiciousAccountTransferDto dto1 = new SuspiciousAccountTransferDto();
        dto1.setId(ids.get(0));
        SuspiciousAccountTransferDto dto2 = new SuspiciousAccountTransferDto();
        dto2.setId(ids.get(1));
        List<SuspiciousAccountTransferDto> dtos = Arrays.asList(dto1, dto2);
        when(suspiciousAccountTransferService.findAllById(ids)).thenReturn(dtos);
        mockMvc.perform(get("/suspicious/account/transfer").param("ids","1","2"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @DisplayName("Чтение по id всех пользователей, негативный сценарий")
    @Test
    void readAllFindAllByIdThrowsNotFoundExceptionTest() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L);
        when(suspiciousAccountTransferService.findAllById(ids)).thenThrow(exceptionReturner
                .getEntityNotFoundException("SuspiciousAccountTransfer по данному id не существует"));
        mockMvc.perform(get("/suspicious/account/transfer").param("ids", "1", "2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("SuspiciousAccountTransfer по данному id не существует"));
    }

    @DisplayName("создание пользователя, позитивный сценарий")
    @Test
    void createTest() throws Exception {
        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();
        dto.setId(1L);
        when(suspiciousAccountTransferService.save(any(SuspiciousAccountTransferDto.class))).thenReturn(dto);
        mockMvc.perform(post("/suspicious/account/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @DisplayName("создание пользователя, негативный сценарий")
    @Test
    void createSaveThrowsNotFoundExceptionTest() throws Exception {
        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();
        dto.setId(1L);
        when(suspiciousAccountTransferService.save(any(SuspiciousAccountTransferDto.class)))
                .thenThrow(new EntityNotFoundException("Not found"));
        mockMvc.perform(post("/suspicious/account/transfer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Not found"));
    }

    @DisplayName("обновление пользователя, позитивный сценарий")
    @Test
    void updateTest() throws Exception {
        Long id = 1L;
        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();
        dto.setId(id);
        when(suspiciousAccountTransferService.update(id,dto)).thenReturn(dto);
        mockMvc.perform(put("/suspicious/account/transfer/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @DisplayName("обновление пользователя, негативный сценарий")
    @Test
    void updateThrowsNotFoundExceptionTest() throws Exception {
        Long id = 1L;
        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();
        dto.setId(id);
        when(suspiciousAccountTransferService.update(2L,dto))
                .thenThrow(new EntityNotFoundException("SuspiciousAccountTransfer not found"));
        mockMvc.perform(put("/suspicious/account/transfer/{id}",2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}
