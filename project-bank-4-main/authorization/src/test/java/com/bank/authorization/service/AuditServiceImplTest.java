package com.bank.authorization.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bank.authorization.dto.AuditDto;
import com.bank.authorization.entity.AuditEntity;
import com.bank.authorization.mapper.AuditMapper;
import com.bank.authorization.repository.AuditRepository;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Audit service")
public class AuditServiceImplTest {

    @Mock
    private AuditRepository repository;

    @Mock
    private AuditMapper mapper;

    @InjectMocks
    private AuditServiceImpl auditServiceForTests;

    static AuditEntity auditEntity;
    static AuditDto auditDto;
    static long id = 1L;

    @BeforeAll
    public static void initData() {
        auditEntity = new AuditEntity();
        auditEntity.setId(id);
        auditEntity.setCreatedBy("Jhon Doe");

        auditDto = new AuditDto();
        auditDto.setCreatedBy("Jhon Doe");
        auditDto.setId(id);
    }

    @Test
    @DisplayName("Получение объекта Audit по id")
    void getUserByIdWithoutExceptions() {
        Optional<AuditEntity> optional = Optional.of(auditEntity);

        when(repository.findById(id)).thenReturn(optional);
        when(mapper.toDto(auditEntity)).thenReturn(auditDto);

        AuditDto actuaAuditDto = this.auditServiceForTests.findById(id);

        Assertions.assertNotNull(actuaAuditDto);
        Assertions.assertEquals(auditDto.getId(), actuaAuditDto.getId());
        Assertions.assertEquals(auditDto.getCreatedBy(), actuaAuditDto.getCreatedBy());
    }

    @Test
    @DisplayName("Получение объекта Audit по id, негативный сценарий")
    void getExceptionInMethodGetUserById() {
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            auditServiceForTests.findById(id);
        });

        String exceptionMessage = "Не найден аудит с ID  " + id;
        String currentExceptionMessage = exception.getMessage();

        Assertions.assertEquals(exceptionMessage, currentExceptionMessage);
    }
}
