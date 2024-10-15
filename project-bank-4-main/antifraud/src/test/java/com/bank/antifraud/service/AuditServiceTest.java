package com.bank.antifraud.service;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;
import com.bank.antifraud.mappers.AuditMapper;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.service.impl.AuditServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuditServiceTest {
    @Mock
    private AuditRepository auditRepository;
    @Mock
    private AuditMapper auditMapper;
    @InjectMocks
    private AuditServiceImpl auditService;

    @DisplayName("Поиск по Id, позитивный сценарий")
    @Test
    void findByIdTest () {
        when(auditRepository.findById(getEntity().getId())).thenReturn(Optional.of(getEntity()));
        when(auditMapper.toDto(getEntity())).thenReturn(getDto());
        AuditDto actualDto = auditService.findById(getDto().getId());
        assertEquals(getDto(), actualDto);
    }

    @DisplayName("Поиск по Id, негативный сценарий")
    @Test
    void findByIdExceptionTest () {
        Long id = 1L;
        when(auditRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> auditService.findById(id));
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
