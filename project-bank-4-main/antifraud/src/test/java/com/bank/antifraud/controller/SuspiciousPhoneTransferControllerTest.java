package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
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
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(SuspiciousPhoneTransferController.class)
public class SuspiciousPhoneTransferControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SuspiciousPhoneTransferService suspiciousPhoneTransferService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ExceptionReturner exceptionReturner;

    @DisplayName("чтение по id, позитивный сценарий")
    @Test
    void readTest() throws Exception {
        SuspiciousPhoneTransferDto suspiciousPhoneTransferDto = new SuspiciousPhoneTransferDto();
        Long id =1L;
        suspiciousPhoneTransferDto.setId(id);
        when(suspiciousPhoneTransferService.findById(id)).thenReturn(suspiciousPhoneTransferDto);
        mockMvc.perform(get("/suspicious/phone/transfer/{id}",id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(suspiciousPhoneTransferDto)));
    }

    @DisplayName("Чтение по id, негативный сценарий")
    @Test
    void readFindByIdThrowsNotFoundExceptionTest() throws Exception {
        when(suspiciousPhoneTransferService.findById(anyLong()))
                .thenThrow(exceptionReturner.getEntityNotFoundException("Not found"));
        mockMvc.perform(get("/suspicious/phone/transfer/{id}",anyLong()))
                .andExpect(status().isNotFound());

    }

    @DisplayName("Чтение по id всех пользователей, позитивный сценарий")
    @Test
    void readAllTest() throws Exception {
        Long id1 =1L;
        Long id2 =2L;
        List<Long> ids = Arrays.asList(id1,id2);
        SuspiciousPhoneTransferDto suspiciousPhoneTransferDto1 = new SuspiciousPhoneTransferDto();
        suspiciousPhoneTransferDto1.setId(ids.get(0));
        SuspiciousPhoneTransferDto suspiciousPhoneTransferDto2 = new SuspiciousPhoneTransferDto();
        suspiciousPhoneTransferDto2.setId(ids.get(1));
        List <SuspiciousPhoneTransferDto> dto = Arrays.asList(suspiciousPhoneTransferDto1,suspiciousPhoneTransferDto2);
        when(suspiciousPhoneTransferService.findAllById(ids)).thenReturn(dto);
        mockMvc.perform(get("/suspicious/phone/transfer")
                        .param("ids","1", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @DisplayName("Чтение по id всех пользователей, негативный сценарий")
    @Test
    void readAllFindAllByIdThrowsNotFoundExceptionTest() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L);
        when(suspiciousPhoneTransferService.findAllById(ids)).thenThrow(exceptionReturner
                .getEntityNotFoundException("SuspiciousPhoneTransfer по данному id не существует"));
        mockMvc.perform(get("/suspicious/phone/transfer").param("ids", "1", "2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("SuspiciousPhoneTransfer по данному id не существует"));
    }

    @DisplayName("создание пользователя, позитивный сценарий")
    @Test
    void createTest() throws Exception {
        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto();
        when(suspiciousPhoneTransferService.save(dto)).thenReturn(dto);
        mockMvc.perform(post("/suspicious/phone/transfer/create")
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
        when(suspiciousPhoneTransferService.save(any(SuspiciousPhoneTransferDto.class)))
                .thenThrow(new RuntimeException());
        mockMvc.perform(post("/suspicious/phone/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError());
    }

    @DisplayName("обновление пользователя, позитивный сценарий")
    @Test
    void updateTest() throws Exception {
        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto();
        Long id =1L;
        dto.setId(id);
        when(suspiciousPhoneTransferService.update(id,dto)).thenReturn(dto);
        mockMvc.perform(put("/suspicious/phone/transfer/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @DisplayName("обновление пользователя, негативный сценарий")
    @Test
    void updateThrowsNotFoundException() throws Exception {
        Long id = 1L;
        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto();
        dto.setId(id);
        when(suspiciousPhoneTransferService.update(id,dto))
                .thenThrow(new EntityNotFoundException("Not found"));
        mockMvc.perform(put("/suspicious/phone/transfer/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Not found"));
    }
}
