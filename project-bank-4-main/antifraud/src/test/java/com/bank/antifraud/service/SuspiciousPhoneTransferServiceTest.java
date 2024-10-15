package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.mappers.SuspiciousPhoneTransferMapper;
import com.bank.antifraud.repository.SuspiciousPhoneTransferRepository;
import com.bank.antifraud.service.impl.SuspiciousPhoneTransferServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyList;

@ExtendWith(MockitoExtension.class)
public class SuspiciousPhoneTransferServiceTest {
    @Mock
    private SuspiciousPhoneTransferRepository suspiciousPhoneTransferRepository;
    @Mock
    private SuspiciousPhoneTransferMapper suspiciousPhoneTransferMapper;
    @InjectMocks
    private SuspiciousPhoneTransferServiceImpl suspiciousPhoneTransferService;

    @DisplayName("Сохранение пользователя, позитивный сценарий")
    @Test
    void saveTest () {
        when(suspiciousPhoneTransferMapper.toEntity(getDto())).thenReturn(getEntity());
        when(suspiciousPhoneTransferRepository.save(getEntity())).thenReturn(getEntity());
        when(suspiciousPhoneTransferMapper.toDto(getEntity())).thenReturn(getDto());
        SuspiciousPhoneTransferDto actualDto = suspiciousPhoneTransferService.save(getDto());
        assertEquals(getDto(), actualDto);
    }

    @DisplayName("Сохранение пользователя, негативный сценарий")
    @Test
    void saveThrowsNullException () {
        assertNull(suspiciousPhoneTransferService.save(null));
    }

    @DisplayName("Поиск пользователя по Id, позитивный сценарий")
    @Test
    void findByIdTest () {
        when(suspiciousPhoneTransferRepository.findById(getEntity().getId())).thenReturn(Optional.of(getEntity()));
        when(suspiciousPhoneTransferMapper.toDto(getEntity())).thenReturn(getDto());
        SuspiciousPhoneTransferDto actualDto = suspiciousPhoneTransferService.findById(getEntity().getId());
        assertEquals(getDto(), actualDto);
    }

    @DisplayName("Поиск пользователя по Id, негативный сценарий")
    @Test
    void findByIdThrowsNullException () {
        assertThrows(NullPointerException.class,()->suspiciousPhoneTransferService.findById(anyLong()));
    }

    @DisplayName("Обновление пользователя, позитивный сценарий")
    @Test
    void updateTest () {
        when(suspiciousPhoneTransferRepository.findById(getEntity().getId())).thenReturn(Optional.of(getEntity()));
        when(suspiciousPhoneTransferMapper.mergeToEntity(getDto(),getEntity())).thenReturn(getEntity());
        when(suspiciousPhoneTransferRepository.save(getEntity())).thenReturn(getEntity());
        when(suspiciousPhoneTransferMapper.toDto(getEntity())).thenReturn(getDto());
        SuspiciousPhoneTransferDto actualDto = suspiciousPhoneTransferService.update(getEntity().getId(), getDto());
        assertEquals(getDto(), actualDto);
    }

    @DisplayName("Обновление пользователя, негативный сценарий")
    @Test
    void updateThrowsNullException () {
        assertThrows(NullPointerException.class,()->suspiciousPhoneTransferService.update(null,getDto()));
    }

    @DisplayName("Поиск всех пользователей по Id, позитивный сценарий")
    @Test
    void findAllByIdTest () {
        List<Long> ids = Arrays.asList(1L, 2L);
        when(suspiciousPhoneTransferRepository.findById(ids.get(0))).thenReturn(Optional.of(getEntity()));
        when(suspiciousPhoneTransferRepository.findById(ids.get(1))).thenReturn(Optional.of(getEntity()));
        when(suspiciousPhoneTransferMapper.toListDto(anyList())).thenReturn(Arrays.asList(getDto(),getDto()));
        List<SuspiciousPhoneTransferDto> actualDtos = suspiciousPhoneTransferService.findAllById(ids);
        assertEquals(2, actualDtos.size());
        assertEquals(getDto(), actualDtos.get(0));
        assertEquals(getDto(), actualDtos.get(1));
    }

    @DisplayName("Поиск всех пользователей по Id, негативный сценарий")
    @Test
    void findAllThrowsNullException () {
        assertThrows(NullPointerException.class,()->suspiciousPhoneTransferService.findAllById(null));
    }

    private static SuspiciousPhoneTransferDto getDto() {
        return new SuspiciousPhoneTransferDto(
                1L,
                2L,
                true,
                true,
                "because",
                "because"
        );
    }

    private static SuspiciousPhoneTransferEntity getEntity() {
        return new SuspiciousPhoneTransferEntity(
                1L,
                2L,
                true,
                true,
                "because",
                "because"
        );
    }
}
