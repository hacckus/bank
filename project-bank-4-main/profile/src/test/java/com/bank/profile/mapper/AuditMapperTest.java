package com.bank.profile.mapper;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AuditMapperTest {

    @InjectMocks
    private AuditMapperImpl mapper;

    @Test
    @DisplayName("маппинг в dto")
    void test_toDto_Success() {
        AuditDto actual = mapper.toDto(getEntity());

        assertEquals(getDto(), actual, "Wrong dto");
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null")
    void test_toDto_NullEntity() {
        assertNull(mapper.toDto(null),
                "Dto is not null");
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан пустой entity")
    void test_toDto_EmptyEntity() {
        assertEquals(new AuditDto(), mapper.toDto(new AuditEntity()),
                "Wrong dto");
    }

    private static AuditEntity getEntity() {
        return new AuditEntity(
                1L,
                "entityType1",
                "operationType1",
                "createdBy1",
                "modifiedBy1",
                Timestamp.valueOf(
                        LocalDateTime.of(
                                LocalDate.of(2000, 1, 1),
                                LocalTime.of(1, 1, 1)
                        )
                ),
                Timestamp.valueOf(
                        LocalDateTime.of(
                                LocalDate.of(2000, 1, 1),
                                LocalTime.of(1, 1, 1)
                        )
                ),
                "newEntityJson1",
                "EntityJson");
    }

    private static AuditDto getDto() {
        return new AuditDto(
                1L,
                "entityType1",
                "operationType1",
                "createdBy1",
                "modifiedBy1",
                Timestamp.valueOf(
                        LocalDateTime.of(
                                LocalDate.of(2000, 1, 1),
                                LocalTime.of(1, 1, 1)
                        )
                ),
                Timestamp.valueOf(
                        LocalDateTime.of(
                                LocalDate.of(2000, 1, 1),
                                LocalTime.of(1, 1, 1)
                        )
                ),
                "newEntityJson1",
                "EntityJson");
    }
}