package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
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
public class SuspiciousPhoneTransferMapperTest {

    @InjectMocks
    SuspiciousPhoneTransferMapperImpl suspiciousPhoneTransferMapper;

    @DisplayName("перевод Entity в Dto, позитивный сценарий")
    @Test
    void toDtoTest() {
        SuspiciousPhoneTransferDto actualDto = suspiciousPhoneTransferMapper.toDto(getEntity());
        assertEquals(actualDto, getDto());
    }

    @DisplayName("перевод Entity в Dto, негативный сценарий")
    @Test
    void toDtoThrowsNullException() {
        assertNull(suspiciousPhoneTransferMapper.toDto(null));
    }

    @DisplayName("перевод Dto в Entity, позитивный сценарий")
    @Test
    void toEntityTest() {
        SuspiciousPhoneTransferEntity actualEntity = suspiciousPhoneTransferMapper.toEntity(getDto());
        actualEntity.setId(1L);
        assertEquals(actualEntity, getEntity());
    }

    @DisplayName("перевод Dto в Entity, негативный сценарий")
    @Test
    void toEntityThrowsNullException() {
        assertNull(suspiciousPhoneTransferMapper.toEntity(null));
    }

    @DisplayName("перевод список Entity в список Dto, позитивный сценарий")
    @Test
    void toListDtoTest() {
        List<SuspiciousPhoneTransferEntity> entityList = Arrays.asList(getEntity(), getEntity());
        List<SuspiciousPhoneTransferDto>dtoList  = Arrays.asList(getDto(), getDto());
        List<SuspiciousPhoneTransferDto> actualDtoList = suspiciousPhoneTransferMapper.toListDto(entityList);
        assertEquals(actualDtoList, dtoList);
    }

    @DisplayName("перевод список Entity в список Dto, негативный сценарий")
    @Test
    void toListDtoThrowsNullException() {
        assertNull(suspiciousPhoneTransferMapper.toListDto(null));
    }

    @DisplayName("слияние в Entity, позитивный сценарий")
    @Test
    void mergeToEntityTest()  {
        SuspiciousPhoneTransferEntity actualEntity = suspiciousPhoneTransferMapper.mergeToEntity(getDto(), getEntity());
        actualEntity.setId(1L);
        assertEquals(actualEntity, getEntity());
    }

    @DisplayName("слияние в Entity, негативный сценарий")
    @Test
    void mergeToEntityThrowsNullException() {
        assertNull(suspiciousPhoneTransferMapper.mergeToEntity(null,null));
        assertThrows(NullPointerException.class,()->suspiciousPhoneTransferMapper
                .mergeToEntity(getDto(),null));
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
