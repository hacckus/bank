package com.bank.profile.service;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.mapper.AuditMapper;
import com.bank.profile.mapper.AuditMapperImpl;
import com.bank.profile.repository.AuditRepository;
import com.bank.profile.service.impl.AuditServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @InjectMocks
    private AuditServiceImpl service;

    @Mock
    private AuditRepository repository;

    @Mock
    private AuditMapper mapper;

    private final AuditMapper mapperImpl = new AuditMapperImpl();

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void test_findById_Success() {
        AuditEntity oldEntity = getOldEntity();
        AuditDto oldEntityDto = mapperImpl.toDto(oldEntity);
        when(repository.findById(oldEntity.getId())).thenReturn(Optional.of(oldEntity));
        when(mapper.toDto(oldEntity)).thenReturn(oldEntityDto);

        AuditDto actualResult = service.findById(oldEntity.getId());
        AuditDto expectedResult = mapper.toDto(oldEntity);

        assertNotNull(actualResult,
                "AuditServiceImp:findById() returned null");
        assertEquals(expectedResult, actualResult,
                "AuditServiceImp:findById() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("поиск по id, негативный сценарий с несуществующим id")
    void test_findById_EntityNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.findById(0L),
                "AuditServiceImp:findById() doesn't throw EntityNotFoundException");
    }

    private static AuditEntity getOldEntity() {
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

}