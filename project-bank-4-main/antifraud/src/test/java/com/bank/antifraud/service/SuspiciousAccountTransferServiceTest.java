package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.mappers.SuspiciousAccountTransferMapper;
import com.bank.antifraud.repository.SuspiciousAccountTransferRepository;
import com.bank.antifraud.service.impl.SuspiciousAccountTransferServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;

@ExtendWith(MockitoExtension.class)
public class SuspiciousAccountTransferServiceTest {

    @Mock
    private SuspiciousAccountTransferRepository suspiciousAccountTransferRepository;
    @Mock
    private SuspiciousAccountTransferMapper suspiciousAccountTransferMapper;
    @InjectMocks
    private SuspiciousAccountTransferServiceImpl suspiciousAccountTransferService;

    @DisplayName("Сохранение пользователя, позитивный сценарий")
    @Test
    void saveTest () {
        when(suspiciousAccountTransferMapper.toEntity(getDto())).thenReturn(getEntity());
        when(suspiciousAccountTransferRepository.save(getEntity())).thenReturn(getEntity());
        when(suspiciousAccountTransferMapper.toDto(getEntity())).thenReturn(getDto());
        SuspiciousAccountTransferDto actualDto = suspiciousAccountTransferService.save(getDto());
        assertEquals(getDto(), actualDto);
    }

    @DisplayName("Сохранение пользователя, негативный сценарий")
    @Test
    void saveThrowsNullException () {
        assertNull(suspiciousAccountTransferService.save(null));
    }

    @DisplayName("Поиск пользователя по Id, позитивный сценарий")
    @Test
    void findByIdTest () {
        when(suspiciousAccountTransferRepository.findById(getEntity().getId())).thenReturn(Optional.of(getEntity()));
        when(suspiciousAccountTransferMapper.toDto(getEntity())).thenReturn(getDto());
        SuspiciousAccountTransferDto actualDto = suspiciousAccountTransferService.findById(getEntity().getId());
        assertEquals(getDto(), actualDto);
    }

    @DisplayName("Поиск пользователя по Id, негативный сценарий")
    @Test
    void findByIdThrowsNullException () {
        assertThrows(NullPointerException.class,()->suspiciousAccountTransferService.findById(anyLong()));
    }
    @DisplayName("Обновление пользователя, позитивный сценарий")
    @Test
    void updateTest () {
       when(suspiciousAccountTransferRepository.findById(getEntity().getId())).thenReturn(Optional.of(getEntity()));
       when(suspiciousAccountTransferMapper.mergeToEntity(getDto(),getEntity())).thenReturn(getEntity());
       when(suspiciousAccountTransferRepository.save(getEntity())).thenReturn(getEntity());
       when(suspiciousAccountTransferMapper.toDto(getEntity())).thenReturn(getDto());
       SuspiciousAccountTransferDto actualDto = suspiciousAccountTransferService.update(getEntity().getId(), getDto());
       assertEquals(getDto(), actualDto);
    }

    @DisplayName("Обновление пользователя, негативный сценарий")
    @Test
    void updateThrowsNullException () {
        assertThrows(NullPointerException.class,()->suspiciousAccountTransferService.update(null,getDto()));
    }

    @DisplayName("Поиск всех пользователей по Id, позитивный сценарий")
    @Test
    void findAllByIdTest () {
        List<Long> ids = Arrays.asList(1L, 2L);
        when(suspiciousAccountTransferRepository.findById(ids.get(0))).thenReturn(Optional.of(getEntity()));
        when(suspiciousAccountTransferRepository.findById(ids.get(1))).thenReturn(Optional.of(getEntity()));
        when(suspiciousAccountTransferMapper.toListDto(anyList())).thenReturn(Arrays.asList(getDto(),getDto()));
        List<SuspiciousAccountTransferDto> actualDtos = suspiciousAccountTransferService.findAllById(ids);
        assertEquals(2, actualDtos.size());
        assertEquals(getDto(), actualDtos.get(0));
        assertEquals(getDto(), actualDtos.get(1));
    }

    @DisplayName("Поиск всех пользователей по Id, негативный сценарий")
    @Test
    void findAllThrowsNullException () {
        assertThrows(NullPointerException.class,()->suspiciousAccountTransferService.findAllById(null));
    }

    private static SuspiciousAccountTransferDto getDto() {
        return new SuspiciousAccountTransferDto(
                1L,
                2L,
                true,
                true,
                "because",
                "because"
        );
    }

    private static SuspiciousAccountTransferEntity getEntity() {
        return new SuspiciousAccountTransferEntity(
                1L,
                2L,
                true,
                true,
                "because",
                "because"
        );
    }
}
