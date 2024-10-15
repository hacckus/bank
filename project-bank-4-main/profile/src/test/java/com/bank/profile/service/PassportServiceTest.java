package com.bank.profile.service;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.PassportMapper;
import com.bank.profile.mapper.PassportMapperImpl;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.service.impl.PassportServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PassportServiceTest {

    @InjectMocks
    private PassportServiceImp service;

    @Mock
    private PassportRepository repository;

    @Mock
    private PassportMapper mapper;

    private final PassportMapper mapperImpl = new PassportMapperImpl();

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void test_findById_Success() {
        PassportEntity oldEntity = getOldEntity();
        PassportDto oldEntityDto = mapperImpl.toDto(oldEntity);
        when(repository.findById(oldEntity.getId())).thenReturn(Optional.of(oldEntity));
        when(mapper.toDto(oldEntity)).thenReturn(oldEntityDto);

        PassportDto actualResult = service.findById(oldEntity.getId());
        PassportDto expectedResult = mapper.toDto(oldEntity);

        assertNotNull(actualResult,
                "PassportServiceImp:findById() returned null");
        assertEquals(expectedResult, actualResult,
                "PassportServiceImp:findById() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("поиск по id, негативный сценарий с несуществующим id")
    void test_findById_EntityNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.findById(0L),
                "PassportServiceImp:findById() doesn't throw EntityNotFoundException");
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void test_save_Success() {
        PassportEntity newEntity = getNewEntity();
        PassportDto newEntityDto = mapperImpl.toDto(newEntity);
        when(repository.save(newEntity)).thenReturn(newEntity);
        when(mapper.toDto(newEntity)).thenReturn(newEntityDto);
        when(mapper.toEntity(newEntityDto)).thenReturn(newEntity);

        PassportDto actualResult = service.save(newEntityDto);
        PassportDto expectedResult = mapper.toDto(newEntity);

        assertNotNull(actualResult,
                "PassportServiceImp:save() returned null");
        assertEquals(expectedResult, actualResult,
                "PassportServiceImp:save() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("сохранение, негативный сценарий с null вместо dto")
    void test_save_Null() {
        assertNull(service.save(null),
                "PassportServiceImp:save() returned not null");
    }

    @Test
    @DisplayName("изменение по id, позитивный сценарий")
    void test_update_Success() {
        PassportEntity oldEntity = getOldEntity();
        PassportEntity newEntity = getNewEntity();
        PassportDto newEntityDto = mapperImpl.toDto(newEntity);
        when(repository.findById(newEntity.getId())).thenReturn(Optional.of(oldEntity));
        when(repository.save(newEntity)).thenReturn(newEntity);
        when(mapper.toDto(newEntity)).thenReturn(newEntityDto);
        when(mapper.mergeToEntity(newEntityDto, oldEntity))
                .thenReturn(mapperImpl.mergeToEntity(newEntityDto, oldEntity));

        PassportDto actualResult = service.update(newEntity.getId(), newEntityDto);
        PassportDto expectedResult = mapper.toDto(newEntity);

        assertNotNull(actualResult,
                "PassportServiceImp:update() returned null");
        assertEquals(expectedResult, actualResult,
                "PassportServiceImp:update() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("изменение по id , негативный сценарий c несуществующим id")
    void test_update_EntityNotFound() {
        PassportEntity newEntity = getNewEntity();
        PassportDto newEntityDto = mapperImpl.toDto(newEntity);
        assertThrows(EntityNotFoundException.class, () -> service.update(0L, newEntityDto),
                "PassportServiceImp:update() doesn't throw EntityNotFoundException");
    }

    @Test
    @DisplayName("поиск всех по списку ids, позитивный сценарий")
    void test_findAllById_Success() {
        PassportEntity oldEntity = getOldEntity();
        PassportEntity newEntity = getNewEntity();
        List<Long> idsList = List.of(newEntity.getId(), oldEntity.getId());
        List<PassportEntity> entities = List.of(oldEntity, newEntity);
        when(repository.findAllById(idsList)).thenReturn(entities);
        when(mapper.toDtoList(entities)).thenReturn(mapperImpl.toDtoList(entities));

        List<PassportDto> actualResult = service.findAllById(idsList);
        List<PassportDto> expectedResult = mapper.toDtoList(entities);

        assertNotNull(actualResult,
                "PassportServiceImp:findAllById() returned null");
        assertEquals(expectedResult, actualResult,
                "PassportServiceImp:findAllById() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("поиск всех по списку ids, негативный сценарий с null вместо списка")
    void test_findAllById_Null() {
        assertEquals(List.of(), service.findAllById(null),
                "PassportServiceImp:findAllById() returned not null");
    }

    private static PassportEntity getOldEntity() {
        return new PassportEntity(
                1L,
                1234,
                56789L,
                "lastName1",
                "firstName1",
                "middleName1",
                "male",
                LocalDate.of(2000, 1, 1),
                "birthPlace1",
                "issuedBy1",
                LocalDate.of(20000, 1, 2),
                123456,
                LocalDate.of(20000, 1, 3),
                getRegistrationEntity()
        );
    }

    private static PassportEntity getNewEntity() {
        return new PassportEntity(
                1L,
                12345,
                567890L,
                "lastName2",
                "firstName2",
                "middleName2",
                "female",
                LocalDate.of(2000, 1, 2),
                "birthPlace2",
                "issuedBy2",
                LocalDate.of(20000, 1, 3),
                1234567,
                LocalDate.of(20000, 1, 4),
                getRegistrationEntity()
        );
    }

    private static RegistrationEntity getRegistrationEntity() {
        return new RegistrationEntity(
                1L,
                "Country2",
                "Region2",
                "City2",
                "District2",
                "Locality2",
                "Street2",
                "HouseNumber2",
                "HouseBlock2",
                "FlatNumber2",
                1L);
    }

}