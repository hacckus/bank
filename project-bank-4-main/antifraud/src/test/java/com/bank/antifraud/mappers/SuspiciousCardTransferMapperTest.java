package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SuspiciousCardTransferMapperTest {
    @InjectMocks
    SuspiciousCardTransferMapperImpl suspiciousCardTransferMapper;

    @DisplayName("перевод Entity в Dto, позитивный сценарий")
    @Test
    void toDtoTest() {
        SuspiciousCardTransferDto actualDto = suspiciousCardTransferMapper.toDto(getEntity());
        assertEquals(actualDto, getDto());
    }

    @DisplayName("перевод Entity в Dto, негативный сценарий")
    @Test
    void toDtoThrowsNullException() {
        assertNull(suspiciousCardTransferMapper.toDto(null));
    }

    @DisplayName("перевод Dto в Entity, позитивный сценарий")
    @Test
    void toEntityTest() {
        SuspiciousCardTransferEntity actualEntity = suspiciousCardTransferMapper.toEntity(getDto());
        actualEntity.setId(1L);
        assertEquals(actualEntity, getEntity());
    }

    @DisplayName("перевод Dto в Entity, негативный сценарий")
    @Test
    void toEntityThrowsNullException() {
        assertNull(suspiciousCardTransferMapper.toEntity(null));
    }

    @DisplayName("перевод список Entity в список Dto, позитивный сценарий")
    @Test
    void toListDtoTest() {
        List<SuspiciousCardTransferEntity> entityList = Arrays.asList(getEntity(), getEntity());
        List<SuspiciousCardTransferDto>dtoList  = Arrays.asList(getDto(), getDto());
        List<SuspiciousCardTransferDto> actualDtoList = suspiciousCardTransferMapper.toListDto(entityList);
        assertEquals(actualDtoList, dtoList);
    }

    @DisplayName("перевод список Entity в список Dto, негативный сценарий")
    @Test
    void toListDtoThrowsNullException() {
        assertNull(suspiciousCardTransferMapper.toListDto(null));
    }

    @DisplayName("слияние в Entity, позитивный сценарий")
    @Test
    void mergeToEntityTest()  {
        SuspiciousCardTransferEntity actualEntity = suspiciousCardTransferMapper.mergeToEntity(getDto(), getEntity());
        actualEntity.setId(1L);
        assertEquals(actualEntity, getEntity());
    }

    @DisplayName("слияние в Entity, негативный сценарий")
    @Test
    void mergeToEntityThrowsNullException() {
        assertNull(suspiciousCardTransferMapper.mergeToEntity(null,null));
        assertThrows(NullPointerException.class,()->suspiciousCardTransferMapper
                .mergeToEntity(getDto(),null));
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
