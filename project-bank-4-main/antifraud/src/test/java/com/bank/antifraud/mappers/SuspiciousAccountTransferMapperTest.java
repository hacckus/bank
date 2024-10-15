package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class SuspiciousAccountTransferMapperTest {
    @InjectMocks
    SuspiciousAccountTransferMapperImpl suspiciousAccountTransferMapper;

    @DisplayName("перевод Entity в Dto, позитивный сценарий")
    @Test
    void toDtoTest() {
        SuspiciousAccountTransferDto actualDto = suspiciousAccountTransferMapper.toDto(getEntity());
        assertEquals(actualDto, getDto());
    }

    @DisplayName("перевод Entity в Dto, негативный сценарий")
    @Test
    void toDtoThrowsNullException() {
        assertNull(suspiciousAccountTransferMapper.toDto(null));
    }

    @DisplayName("перевод Dto в Entity, позитивный сценарий")
    @Test
    void toEntityTest() {
        SuspiciousAccountTransferEntity actualEntity = suspiciousAccountTransferMapper.toEntity(getDto());
        actualEntity.setId(1L);
        assertEquals(actualEntity, getEntity());
    }

    @DisplayName("перевод Dto в Entity, негативный сценарий")
    @Test
    void toEntityThrowsNullException() {
        assertNull(suspiciousAccountTransferMapper.toEntity(null));
    }

    @DisplayName("перевод список Entity в список Dto, позитивный сценарий")
    @Test
    void toListDtoTest() {
        List<SuspiciousAccountTransferEntity> entityList = Arrays.asList(getEntity(), getEntity());
        List<SuspiciousAccountTransferDto> dtoList = Arrays.asList(getDto(), getDto());
        List<SuspiciousAccountTransferDto> actualDtoList = suspiciousAccountTransferMapper.toListDto(entityList);
        assertEquals(actualDtoList, dtoList);
    }

    @DisplayName("перевод список Entity в список Dto, негативный сценарий")
    @Test
    void toListDtoThrowsNullException() {
        assertNull(suspiciousAccountTransferMapper.toListDto(null));
    }

    @DisplayName("слияние в Entity, позитивный сценарий")
    @Test
    void mergeToEntityTest() {
        SuspiciousAccountTransferEntity actualEntity = suspiciousAccountTransferMapper
                .mergeToEntity(getDto(), getEntity());
        actualEntity.setId(1L);
        assertEquals(actualEntity, getEntity());
    }

    @DisplayName("слияние в Entity, негативный сценарий")
    @Test
    void mergeToEntityThrowsNullException() {
        assertNull(suspiciousAccountTransferMapper
                .mergeToEntity(null, null));
        assertThrows(NullPointerException.class, () -> suspiciousAccountTransferMapper
                .mergeToEntity(getDto(), null));
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
