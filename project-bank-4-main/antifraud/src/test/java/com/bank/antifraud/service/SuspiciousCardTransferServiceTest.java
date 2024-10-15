package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import com.bank.antifraud.mappers.SuspiciousCardTransferMapper;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.service.impl.SuspiciousCardTransferServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;

@ExtendWith(MockitoExtension.class)
public class SuspiciousCardTransferServiceTest {
    @Mock
    private SuspiciousCardTransferRepository suspiciousCardTransferRepository;
    @Mock
    private SuspiciousCardTransferMapper suspiciousCardTransferMapper;
    @InjectMocks
    private SuspiciousCardTransferServiceImpl suspiciousCardTransferService;

    @DisplayName("Сохранение пользователя, позитивный сценарий")
    @Test
    void saveTest () {
        when(suspiciousCardTransferMapper.toEntity(getDto())).thenReturn(getEntity());
        when(suspiciousCardTransferRepository.save(getEntity())).thenReturn(getEntity());
        when(suspiciousCardTransferMapper.toDto(getEntity())).thenReturn(getDto());
        SuspiciousCardTransferDto actualDto = suspiciousCardTransferService.save(getDto());
        assertEquals(getDto(), actualDto);
    }

    @DisplayName("Сохранение пользователя, негативный сценарий")
    @Test
    void saveThrowsNullException () {
        assertNull(suspiciousCardTransferService.save(null));
    }

    @DisplayName("Поиск пользователя по Id, позитивный сценарий")
    @Test
    void findByIdTest () {
        when(suspiciousCardTransferRepository.findById(getEntity().getId())).thenReturn(Optional.of(getEntity()));
        when(suspiciousCardTransferMapper.toDto(getEntity())).thenReturn(getDto());
        SuspiciousCardTransferDto actualDto = suspiciousCardTransferService.findById(getEntity().getId());
        assertEquals(getDto(), actualDto);
    }

    @DisplayName("Поиск пользователя по Id, негативный сценарий")
    @Test
    void findByIdThrowsNullException () {
        assertThrows(NullPointerException.class,()->suspiciousCardTransferService.findById(anyLong()));
    }

    @DisplayName("Обновление пользователя, позитивный сценарий")
    @Test
    void updateTest () {
        when(suspiciousCardTransferRepository.findById(getEntity().getId())).thenReturn(Optional.of(getEntity()));
        when(suspiciousCardTransferMapper.mergeToEntity(getDto(),getEntity())).thenReturn(getEntity());
        when(suspiciousCardTransferRepository.save(getEntity())).thenReturn(getEntity());
        when(suspiciousCardTransferMapper.toDto(getEntity())).thenReturn(getDto());
        SuspiciousCardTransferDto actualDto = suspiciousCardTransferService.update(getEntity().getId(), getDto());
        assertEquals(getDto(), actualDto);
    }

    @DisplayName("Обновление пользователя, негативный сценарий")
    @Test
    void updateThrowsNullException () {
        assertThrows(NullPointerException.class,()->suspiciousCardTransferService.update(null,getDto()));
    }

    @DisplayName("Поиск всех пользователей по Id, позитивный сценарий")
    @Test
    void findAllByIdTest () {
        List<Long> ids = Arrays.asList(1L, 2L);
        when(suspiciousCardTransferRepository.findById(ids.get(0))).thenReturn(Optional.of(getEntity()));
        when(suspiciousCardTransferRepository.findById(ids.get(1))).thenReturn(Optional.of(getEntity()));
        when(suspiciousCardTransferMapper.toListDto(anyList())).thenReturn(Arrays.asList(getDto(),getDto()));
        List<SuspiciousCardTransferDto> actualDtos = suspiciousCardTransferService.findAllById(ids);
        assertEquals(2, actualDtos.size());
        assertEquals(getDto(), actualDtos.get(0));
        assertEquals(getDto(), actualDtos.get(1));
    }

    @DisplayName("Поиск всех пользователей по Id, негативный сценарий")
    @Test
    void findAllThrowsNullException () {
        assertThrows(NullPointerException.class,()->suspiciousCardTransferService.findAllById(null));
    }

    private static SuspiciousCardTransferDto getDto() {
        return new SuspiciousCardTransferDto(
                1L,
                2L,
                true,
                true,
                "because",
                "because"
        );
    }

    private static SuspiciousCardTransferEntity getEntity() {
        return new SuspiciousCardTransferEntity(
                1L,
                2L,
                true,
                true,
                "because",
                "because"
        );
    }
}
