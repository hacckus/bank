package com.bank.profile.service;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.ProfileMapper;
import com.bank.profile.mapper.ProfileMapperImpl;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.impl.ProfileServiceImp;
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
class ProfileServiceTest {

    @InjectMocks
    private ProfileServiceImp service;

    @Mock
    private ProfileRepository repository;

    @Mock
    private ProfileMapper mapper;

    private final ProfileMapper mapperImpl = new ProfileMapperImpl();

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void test_findById_Success() {
        ProfileEntity oldEntity = getOldEntity();
        ProfileDto oldEntityDto = mapperImpl.toDto(oldEntity);
        when(repository.findById(oldEntity.getId())).thenReturn(Optional.of(oldEntity));
        when(mapper.toDto(oldEntity)).thenReturn(oldEntityDto);

        ProfileDto actualResult = service.findById(oldEntity.getId());
        ProfileDto expectedResult = mapper.toDto(oldEntity);

        assertNotNull(actualResult,
                "ProfileServiceImp:findById() returned null");
        assertEquals(expectedResult, actualResult,
                "ProfileServiceImp:findById() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("поиск по id, негативный сценарий с несуществующим id")
    void test_findById_EntityNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.findById(0L),
                "ProfileServiceImp:findById() doesn't throw EntityNotFoundException");
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void test_save_Success() {
        ProfileEntity newEntity = getNewEntity();
        ProfileDto newEntityDto = mapperImpl.toDto(newEntity);
        when(repository.save(newEntity)).thenReturn(newEntity);
        when(mapper.toDto(newEntity)).thenReturn(newEntityDto);
        when(mapper.toEntity(newEntityDto)).thenReturn(newEntity);

        ProfileDto actualResult = service.save(newEntityDto);
        ProfileDto expectedResult = mapper.toDto(newEntity);

        assertNotNull(actualResult,
                "ProfileServiceImp:save() returned null");
        assertEquals(expectedResult, actualResult,
                "ProfileServiceImp:save() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("сохранение, негативный сценарий с null вместо dto")
    void test_save_Null() {
        assertNull(service.save(null),
                "ProfileServiceImp:save() returned not null");
    }

    @Test
    @DisplayName("изменение по id, позитивный сценарий")
    void test_update_Success() {
        ProfileEntity oldEntity = getOldEntity();
        ProfileEntity newEntity = getNewEntity();
        ProfileDto newEntityDto = mapperImpl.toDto(newEntity);
        when(repository.findById(newEntity.getId())).thenReturn(Optional.of(oldEntity));
        when(repository.save(newEntity)).thenReturn(newEntity);
        when(mapper.toDto(newEntity)).thenReturn(newEntityDto);
        when(mapper.mergeToEntity(newEntityDto, oldEntity))
                .thenReturn(mapperImpl.mergeToEntity(newEntityDto, oldEntity));

        ProfileDto actualResult = service.update(newEntity.getId(), newEntityDto);
        ProfileDto expectedResult = mapper.toDto(newEntity);

        assertNotNull(actualResult,
                "ProfileServiceImp:update() returned null");
        assertEquals(expectedResult, actualResult,
                "ProfileServiceImp:update() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("изменение по id , негативный сценарий c несуществующим id")
    void test_update_EntityNotFound() {
        ProfileEntity newEntity = getNewEntity();
        ProfileDto newEntityDto = mapperImpl.toDto(newEntity);
        assertThrows(EntityNotFoundException.class, () -> service.update(0L, newEntityDto),
                "ProfileServiceImp:update() doesn't throw EntityNotFoundException");
    }

    @Test
    @DisplayName("поиск всех по списку ids, позитивный сценарий")
    void test_findAllById_Success() {
        ProfileEntity oldEntity = getOldEntity();
        ProfileEntity newEntity = getNewEntity();
        List<Long> idsList = List.of(newEntity.getId(), oldEntity.getId());
        List<ProfileEntity> entities = List.of(oldEntity, newEntity);
        when(repository.findAllById(idsList)).thenReturn(entities);
        when(mapper.toDtoList(entities)).thenReturn(mapperImpl.toDtoList(entities));

        List<ProfileDto> actualResult = service.findAllById(idsList);
        List<ProfileDto> expectedResult = mapper.toDtoList(entities);

        assertNotNull(actualResult,
                "ProfileServiceImp:findAllById() returned null");
        assertEquals(expectedResult, actualResult,
                "ProfileServiceImp:findAllById() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("поиск всех по списку ids, негативный сценарий с null вместо списка")
    void test_findAllById_Null() {
        assertEquals(List.of(), service.findAllById(null),
                "ProfileServiceImp:findAllById() returned not null");
    }

    private static ProfileEntity getOldEntity() {
        return new ProfileEntity(
                1L,
                12345678L,
                "email1",
                "nameOnCard1",
                12345678L,
                12345678L,
                getPassportEntity(),
                getActualRegistrationEntity()
        );
    }

    private static ProfileEntity getNewEntity() {
        return new ProfileEntity(
                1L,
                12345678L,
                "email1",
                "nameOnCard1",
                12345678L,
                12345678L,
                getPassportEntity(),
                getActualRegistrationEntity()
        );
    }

    private static PassportEntity getPassportEntity() {
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

    private static ActualRegistrationEntity getActualRegistrationEntity() {
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