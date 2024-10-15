package com.bank.profile.service;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.RegistrationMapper;
import com.bank.profile.mapper.RegistrationMapperImpl;
import com.bank.profile.repository.RegistrationRepository;

import com.bank.profile.service.impl.RegistrationServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @InjectMocks
    private RegistrationServiceImp service;

    @Mock
    private RegistrationRepository repository;

    @Mock
    private RegistrationMapper mapper;

    private final RegistrationMapper mapperImpl = new RegistrationMapperImpl();

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void test_findById_Success() {
        RegistrationEntity oldEntity = getOldEntity();
        RegistrationDto oldEntityDto = mapperImpl.toDto(oldEntity);
        when(repository.findById(oldEntity.getId())).thenReturn(Optional.of(oldEntity));
        when(mapper.toDto(oldEntity)).thenReturn(oldEntityDto);

        RegistrationDto actualResult = service.findById(oldEntity.getId());
        RegistrationDto expectedResult = mapper.toDto(oldEntity);

        assertNotNull(actualResult,
                "RegistrationServiceImp:findById() returned null");
        assertEquals(expectedResult, actualResult,
                "RegistrationServiceImp:findById() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("поиск по id, негативный сценарий с несуществующим id")
    void test_findById_EntityNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.findById(0L),
                "RegistrationServiceImp:findById() doesn't throw EntityNotFoundException");
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void test_save_Success() {
        RegistrationEntity newEntity = getNewEntity();
        RegistrationDto newEntityDto = mapperImpl.toDto(newEntity);
        when(repository.save(newEntity)).thenReturn(newEntity);
        when(mapper.toDto(newEntity)).thenReturn(newEntityDto);
        when(mapper.toEntity(newEntityDto)).thenReturn(newEntity);

        RegistrationDto actualResult = service.save(newEntityDto);
        RegistrationDto expectedResult = mapper.toDto(newEntity);

        assertNotNull(actualResult,
                "RegistrationServiceImp:save() returned null");
        assertEquals(expectedResult, actualResult,
                "RegistrationServiceImp:save() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("сохранение, негативный сценарий с null вместо dto")
    void test_save_Null() {
        assertNull(service.save(null),
                "RegistrationServiceImp:save() returned not null");
    }

    @Test
    @DisplayName("изменение по id, позитивный сценарий")
    void test_update_Success() {
        RegistrationEntity oldEntity = getOldEntity();
        RegistrationEntity newEntity = getNewEntity();
        RegistrationDto newEntityDto = mapperImpl.toDto(newEntity);
        when(repository.findById(newEntity.getId())).thenReturn(Optional.of(oldEntity));
        when(repository.save(newEntity)).thenReturn(newEntity);
        when(mapper.toDto(newEntity)).thenReturn(newEntityDto);
        when(mapper.mergeToEntity(newEntityDto, oldEntity))
                .thenReturn(mapperImpl.mergeToEntity(newEntityDto, oldEntity));

        RegistrationDto actualResult = service.update(newEntity.getId(), newEntityDto);
        RegistrationDto expectedResult = mapper.toDto(newEntity);

        assertNotNull(actualResult,
                "RegistrationServiceImp:update() returned null");
        assertEquals(expectedResult, actualResult,
                "RegistrationServiceImp:update() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("изменение по id , негативный сценарий c несуществующим id")
    void test_update_EntityNotFound() {
        RegistrationEntity newEntity = getNewEntity();
        RegistrationDto newEntityDto = mapperImpl.toDto(newEntity);
        assertThrows(EntityNotFoundException.class, () -> service.update(0L, newEntityDto),
                "RegistrationServiceImp:update() doesn't throw EntityNotFoundException");
    }

    @Test
    @DisplayName("поиск всех по списку ids, позитивный сценарий")
    void test_findAllById_Success() {
        RegistrationEntity oldEntity = getOldEntity();
        RegistrationEntity newEntity = getNewEntity();
        List<Long> idsList = List.of(newEntity.getId(), oldEntity.getId());
        List<RegistrationEntity> entities = List.of(oldEntity, newEntity);
        when(repository.findAllById(idsList)).thenReturn(entities);
        when(mapper.toDtoList(entities)).thenReturn(mapperImpl.toDtoList(entities));

        List<RegistrationDto> actualResult = service.findAllById(idsList);
        List<RegistrationDto> expectedResult = mapper.toDtoList(entities);

        assertNotNull(actualResult,
                "RegistrationServiceImp:findAllById() returned null");
        assertEquals(expectedResult, actualResult,
                "RegistrationServiceImp:findAllById() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("поиск всех по списку ids, негативный сценарий с null вместо списка")
    void test_findAllById_Null() {
        assertEquals(List.of(), service.findAllById(null),
                "RegistrationServiceImp:findAllById() returned not null");
    }

    private static RegistrationEntity getOldEntity() {
        return new RegistrationEntity(
                1L,
                "Country1",
                "Region1",
                "City1",
                "District1",
                "Locality1",
                "Street1",
                "HouseNumber1",
                "HouseBlock1",
                "FlatNumber1",
                1L);
    }

    private static RegistrationEntity getNewEntity() {
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