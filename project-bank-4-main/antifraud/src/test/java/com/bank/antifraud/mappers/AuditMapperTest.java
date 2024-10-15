package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Timestamp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class AuditMapperTest {
    @InjectMocks
    private AuditMapperImpl auditMapper;

    @DisplayName("перевод Entity в Dto, позитивный сценарий")
    @Test
    void toAuditDtoTest() {
        AuditDto actualDto = auditMapper.toDto(getEntity());
        assertEquals(getDto(), actualDto);
    }

    @DisplayName("перевод Entity в Dto,негативный сценарий")
    @Test
    void toAuditDtoThrowsNullException() {
        assertNull(auditMapper.toDto(null));
    }

    private static AuditDto getDto() {
        return new AuditDto(
                1L,
                "entity-type",
                "operationType",
                "createdBy",
                "modifiedBy",
                new Timestamp(1L),
                new Timestamp(1L),
                "newEntityJson",
                "entityJson"
        );
    }
    private static AuditEntity getEntity() {
        return new AuditEntity(
                1L,
                "entity-type",
                "operationType",
                "createdBy",
                "modifiedBy",
                new Timestamp(1L),
                new Timestamp(1L),
                "newEntityJson",
                "entityJson"
        );
    }
}
