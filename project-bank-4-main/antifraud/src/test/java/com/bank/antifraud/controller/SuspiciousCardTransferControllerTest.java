package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.service.SuspiciousCardTransferService;
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
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(SuspiciousCardTransferController.class)
public class SuspiciousCardTransferControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SuspiciousCardTransferService suspiciousCardTransferService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ExceptionReturner exceptionReturner;

    @DisplayName("чтение по id, позитивный сценарий")
    @Test
    void readTest() throws Exception {
        Long id = 1L;
        SuspiciousCardTransferDto dto = new SuspiciousCardTransferDto();
        dto.setId(id);
        when(suspiciousCardTransferService.findById(id)).thenReturn(dto);
        mockMvc.perform(get("/suspicious/card/transfer/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @DisplayName("Чтение по id, негативный сценарий")
    @Test
    void readFindByIdThrowsNotFoundExceptionTest() throws Exception {
        when(suspiciousCardTransferService.findById(anyLong()))
                .thenThrow(exceptionReturner.getEntityNotFoundException("Not found"));
        mockMvc.perform(get("/suspicious/card/transfer/{id}",anyLong()))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Чтение по id всех пользователей, позитивный сценарий")
    @Test
    void readAllTest() throws Exception {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        SuspiciousCardTransferDto dto1 = new SuspiciousCardTransferDto();
        dto1.setId(1L);
        SuspiciousCardTransferDto dto2 = new SuspiciousCardTransferDto();
        dto2.setId(2L);
        List<SuspiciousCardTransferDto> dtos =  Arrays.asList(dto1, dto2);
        when(suspiciousCardTransferService.findAllById(ids)).thenReturn(dtos);
        mockMvc.perform(get("/suspicious/card/transfer").param("ids","1", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @DisplayName("Чтение по id всех пользователей, негативный сценарий")
    @Test
    void readAllFindAllByIdThrowsNotFoundExceptionTest() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L);
        when(suspiciousCardTransferService.findAllById(ids)).thenThrow(exceptionReturner
                .getEntityNotFoundException("SuspiciousAccountTransfer по данному id не существует"));

        mockMvc.perform(get("/suspicious/card/transfer").param("ids", "1", "2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("SuspiciousAccountTransfer по данному id не существует"));
    }

    @DisplayName("создание пользователя, позитивный сценарий")
    @Test
    void createTest() throws Exception {
        SuspiciousCardTransferDto dto = new SuspiciousCardTransferDto();
        when(suspiciousCardTransferService.save(dto)).thenReturn(dto);
        mockMvc.perform(post("/suspicious/card/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @DisplayName("создание пользователя, негативный сценарий")
    @Test
    void createSaveThrowsNotFoundException() throws Exception {
        SuspiciousCardTransferDto dto = new SuspiciousCardTransferDto();
        dto.setId(1L);
        when(suspiciousCardTransferService.save(any(SuspiciousCardTransferDto.class))).thenThrow(new RuntimeException());
        mockMvc.perform(post("/suspicious/card/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError());
    }

    @DisplayName("обновление пользователя, позитивный сценарий")
    @Test
    void updateTest() throws Exception {
        SuspiciousCardTransferDto dto1 = new SuspiciousCardTransferDto();
        dto1.setId(1L);
        when(suspiciousCardTransferService.update(1L,dto1)).thenReturn(dto1);
        mockMvc.perform(put("/suspicious/card/transfer/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto1)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto1)));
    }

    @DisplayName("обновление пользователя, негативный сценарий")
    @Test
    void updateThrowsNotFoundException() throws Exception {
        Long id = 1L;
        SuspiciousCardTransferDto dto = new SuspiciousCardTransferDto();
        dto.setId(id);
        when(suspiciousCardTransferService.update(2L,dto))
                .thenThrow(EntityNotFoundException.class);
        mockMvc.perform(put("/suspicious/card/transfer/{id}",2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}
