package com.bank.profile.service;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.mapper.ActualRegistrationMapperImpl;
import com.bank.profile.repository.ActualRegistrationRepository;

import com.bank.profile.service.impl.ActualRegistrationServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ActualRegistrationServiceTest {

    @InjectMocks
    private ActualRegistrationServiceImp service;

    @Mock
    private ActualRegistrationRepository repository;

    @Mock
    private ActualRegistrationMapper mapper;

    private final ActualRegistrationMapper mapperImpl = new ActualRegistrationMapperImpl();

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void test_findById_Success() {
        ActualRegistrationEntity oldEntity = getOldEntity();
        ActualRegistrationDto oldEntityDto = mapperImpl.toDto(oldEntity);
        when(repository.findById(oldEntity.getId())).thenReturn(Optional.of(oldEntity));
        when(mapper.toDto(oldEntity)).thenReturn(oldEntityDto);

        ActualRegistrationDto actualResult = service.findById(oldEntity.getId());
        ActualRegistrationDto expectedResult = mapper.toDto(oldEntity);

        assertNotNull(actualResult,
                "ActualRegistrationServiceImp:findById() returned null");
        assertEquals(expectedResult, actualResult,
                "ActualRegistrationServiceImp:findById() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("поиск по id, негативный сценарий с несуществующим id")
    void test_findById_EntityNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.findById(0L),
                "ActualRegistrationServiceImp:findById() doesn't throw EntityNotFoundException");
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void test_save_Success() {
        ActualRegistrationEntity newEntity = getNewEntity();
        ActualRegistrationDto newEntityDto = mapperImpl.toDto(newEntity);
        when(repository.save(newEntity)).thenReturn(newEntity);
        when(mapper.toDto(newEntity)).thenReturn(newEntityDto);
        when(mapper.toEntity(newEntityDto)).thenReturn(newEntity);

        ActualRegistrationDto actualResult = service.save(newEntityDto);
        ActualRegistrationDto expectedResult = mapper.toDto(newEntity);

        assertNotNull(actualResult,
                "ActualRegistrationServiceImp:save() returned null");
        assertEquals(expectedResult, actualResult,
                "ActualRegistrationServiceImp:save() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("сохранение, негативный сценарий с null вместо dto")
    void test_save_Null() {
        assertNull(service.save(null),
                "ActualRegistrationServiceImp:save() returned not null");
    }

    @Test
    @DisplayName("изменение по id, позитивный сценарий")
    void test_update_Success() {
        ActualRegistrationEntity oldEntity = getOldEntity();
        ActualRegistrationEntity newEntity = getNewEntity();
        ActualRegistrationDto newEntityDto = mapperImpl.toDto(newEntity);
        when(repository.findById(newEntity.getId())).thenReturn(Optional.of(oldEntity));
        when(repository.save(newEntity)).thenReturn(newEntity);
        when(mapper.toDto(newEntity)).thenReturn(newEntityDto);
        when(mapper.mergeToEntity(newEntityDto, oldEntity))
                .thenReturn(mapperImpl.mergeToEntity(newEntityDto, oldEntity));

        ActualRegistrationDto actualResult = service.update(newEntity.getId(), newEntityDto);
        ActualRegistrationDto expectedResult = mapper.toDto(newEntity);

        assertNotNull(actualResult,
                "ActualRegistrationServiceImp:update() returned null");
        assertEquals(expectedResult, actualResult,
                "ActualRegistrationServiceImp:update() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("изменение по id , негативный сценарий c несуществующим id")
    void test_update_EntityNotFound() {
        ActualRegistrationEntity newEntity = getNewEntity();
        ActualRegistrationDto newEntityDto = mapperImpl.toDto(newEntity);
        assertThrows(EntityNotFoundException.class, () -> service.update(0L, newEntityDto),
                "ActualRegistrationServiceImp:update() doesn't throw EntityNotFoundException");
    }

    @Test
    @DisplayName("поиск всех по списку ids, позитивный сценарий")
    void test_findAllById_Success() {
        ActualRegistrationEntity oldEntity = getOldEntity();
        ActualRegistrationEntity newEntity = getNewEntity();
        List<Long> idsList = List.of(newEntity.getId(), oldEntity.getId());
        List<ActualRegistrationEntity> entities = List.of(oldEntity, newEntity);
        when(repository.findAllById(idsList)).thenReturn(entities);
        when(mapper.toDtoList(entities)).thenReturn(mapperImpl.toDtoList(entities));

        List<ActualRegistrationDto> actualResult = service.findAllById(idsList);
        List<ActualRegistrationDto> expectedResult = mapper.toDtoList(entities);

        assertNotNull(actualResult,
                "ActualRegistrationServiceImp:findAllById() returned null");
        assertEquals(expectedResult, actualResult,
                "ActualRegistrationServiceImp:findAllById() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("поиск всех по списку ids, негативный сценарий с null вместо списка")
    void test_findAllById_Null() {
        assertEquals(List.of(), service.findAllById(null),
                "ActualRegistrationServiceImp:findAllById() returned not null");
    }

    private static ActualRegistrationEntity getOldEntity() {
        return new ActualRegistrationEntity(
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

    private static ActualRegistrationEntity getNewEntity() {
        return new ActualRegistrationEntity(
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