package com.bank.profile.service;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.mapper.AccountDetailsIdMapperImpl;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.impl.AccountDetailsIdServiceImp;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class AccountDetailsIdServiceTest {

    @InjectMocks
    private AccountDetailsIdServiceImp service;

    @Mock
    private AccountDetailsIdRepository repository;

    @Mock
    private AccountDetailsIdMapper mapper;

    private final AccountDetailsIdMapper mapperImpl = new AccountDetailsIdMapperImpl();

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void test_findById_Success() {
        AccountDetailsIdEntity oldEntity = getOldEntity();
        AccountDetailsIdDto oldEntityDto = mapperImpl.toDto(oldEntity);
        when(repository.findById(oldEntity.getId())).thenReturn(Optional.of(oldEntity));
        when(mapper.toDto(oldEntity)).thenReturn(oldEntityDto);

        AccountDetailsIdDto actualResult = service.findById(oldEntity.getId());
        AccountDetailsIdDto expectedResult = mapper.toDto(oldEntity);

        assertNotNull(actualResult,
                "AccountDetailsIdServiceImp:findById() returned null");
        assertEquals(expectedResult, actualResult,
                "AccountDetailsIdServiceImp:findById() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("поиск по id, негативный сценарий с несуществующим id")
    void test_findById_EntityNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.findById(0L),
                "AccountDetailsIdServiceImp:findById() doesn't throw EntityNotFoundException");
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void test_save_Success() {
        AccountDetailsIdEntity newEntity = getNewEntity();
        AccountDetailsIdDto newEntityDto = mapperImpl.toDto(newEntity);
        when(repository.save(newEntity)).thenReturn(newEntity);
        when(mapper.toDto(newEntity)).thenReturn(newEntityDto);
        when(mapper.toEntity(newEntityDto)).thenReturn(newEntity);

        AccountDetailsIdDto actualResult = service.save(newEntityDto);
        AccountDetailsIdDto expectedResult = mapper.toDto(newEntity);

        assertNotNull(actualResult,
                "AccountDetailsIdServiceImp:save() returned null");
        assertEquals(expectedResult, actualResult,
                "AccountDetailsIdServiceImp:save() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("сохранение, негативный сценарий с null вместо dto")
    void test_save_Null() {
        assertNull(service.save(null),
                "AccountDetailsIdServiceImp:save() returned not null");
    }

    @Test
    @DisplayName("изменение по id, позитивный сценарий")
    void test_update_Success() {
        AccountDetailsIdEntity oldEntity = getOldEntity();
        AccountDetailsIdEntity newEntity = getNewEntity();
        AccountDetailsIdDto newEntityDto = mapperImpl.toDto(newEntity);
        when(repository.findById(newEntity.getId())).thenReturn(Optional.of(oldEntity));
        when(repository.save(newEntity)).thenReturn(newEntity);
        when(mapper.toDto(newEntity)).thenReturn(newEntityDto);
        when(mapper.mergeToEntity(newEntityDto, oldEntity))
                .thenReturn(mapperImpl.mergeToEntity(newEntityDto, oldEntity));

        AccountDetailsIdDto actualResult = service.update(newEntity.getId(), newEntityDto);
        AccountDetailsIdDto expectedResult = mapper.toDto(newEntity);

        assertNotNull(actualResult,
                "AccountDetailsIdServiceImp:update() returned null");
        assertEquals(expectedResult, actualResult,
                "AccountDetailsIdServiceImp:update() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("изменение по id , негативный сценарий c несуществующим id")
    void test_update_EntityNotFound() {
        AccountDetailsIdEntity newEntity = getNewEntity();
        AccountDetailsIdDto newEntityDto = mapperImpl.toDto(newEntity);
        assertThrows(EntityNotFoundException.class, () -> service.update(0L, newEntityDto),
                "AccountDetailsIdServiceImp:update() doesn't throw EntityNotFoundException");
    }

    @Test
    @DisplayName("поиск всех по списку ids, позитивный сценарий")
    void test_findAllById_Success() {
        AccountDetailsIdEntity oldEntity = getOldEntity();
        AccountDetailsIdEntity newEntity = getNewEntity();
        List<Long> idsList = List.of(newEntity.getId(), oldEntity.getId());
        List<AccountDetailsIdEntity> entities = List.of(oldEntity, newEntity);

        when(repository.findAllById(idsList)).thenReturn(entities);
        when(mapper.toDtoList(entities)).thenReturn(mapperImpl.toDtoList(entities));

        List<AccountDetailsIdDto> actualResult = service.findAllById(idsList);
        List<AccountDetailsIdDto> expectedResult = mapper.toDtoList(entities);

        assertNotNull(actualResult,
                "AccountDetailsIdServiceImp:findAllById() returned null");
        assertEquals(expectedResult, actualResult,
                "AccountDetailsIdServiceImp:findAllById() returned"
                        + actualResult
                        + "that isn't equal to expected result"
                        + expectedResult);
    }

    @Test
    @DisplayName("поиск всех по списку ids, негативный сценарий с null вместо списка")
    void test_findAllById_Null() {
        assertEquals(List.of(), service.findAllById(null),
                "AccountDetailsIdServiceImp:findAllById() returned not null");
    }

    private static AccountDetailsIdEntity getOldEntity() {
        return new AccountDetailsIdEntity(
                1L,
                1L,
                getProfileEntity()
        );
    }

    private static AccountDetailsIdEntity getNewEntity() {
        return new AccountDetailsIdEntity(
                1L,
                2L,
                getProfileEntity()
        );
    }

    private static ProfileEntity getProfileEntity() {
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