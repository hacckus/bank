package com.bank.profile.mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.entity.RegistrationEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProfileMapperTest {

    @InjectMocks
    private ProfileMapperImpl mapper;

    @Test
    @DisplayName("маппинг в entity")
    void test_toEntity_Success() {
        ProfileEntity actual = mapper.toEntity(getDto());
        actual.setId(1L);

        assertEquals(getEntity(), actual, "Wrong entity");
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null")
    void test_toEntity_NullDto() {
        assertNull(mapper.toEntity(null),
                "Entity is not null");
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан пустой dto")
    void test_toEntity_EmptyDto() {
        assertEquals(new ProfileEntity(), mapper.toEntity(new ProfileDto()),
                "Wrong entity");
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан dto с passportDto c registrationDto=null")
    void test_toEntity_WithPassportSetNullRegistrationDto() {
        ProfileDto dto = getDto();
        PassportDto passportDto = getPassportDto();
        passportDto.setRegistration(null);
        dto.setPassport(passportDto);

        ProfileEntity expected = getEntity();
        PassportEntity passportEntity = getPassportEntity();
        passportEntity.setRegistration(null);
        expected.setPassport(passportEntity);

        ProfileEntity actual = mapper.toEntity(dto);
        actual.setId(1L);

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("маппинг в dto")
    void test_toDto_Success() {
        ProfileDto actual = mapper.toDto(getEntity());

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
        assertEquals(new ProfileDto(), mapper.toDto(new ProfileEntity()),
                "Wrong dto");
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан entity c passportEntity c registrationEntity=null")
    void test_toDto_WithPassportSetNullRegistrationEntity() {
        ProfileEntity entity = getEntity();
        PassportEntity passportEntity = getPassportEntity();
        passportEntity.setRegistration(null);
        entity.setPassport(passportEntity);
        ProfileDto expected = getDto();
        PassportDto passportDto = getPassportDto();
        passportDto.setRegistration(null);
        expected.setPassport(passportDto);

        assertEquals(expected, mapper.toDto(entity), "Wrong dto");
    }

    @Test
    @DisplayName("слияние в entity")
    void test_mergeToEntity_Success() {
        ProfileEntity expected = getEntity();
        ProfileEntity actual = mapper.mergeToEntity(getDto(), getEntity());

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void test_mergeToEntity_NullDtoNullEntity() {
        assertNull(mapper.mergeToEntity(null, null),
                "Entity is not null");
    }

    @Test
    @DisplayName("слияние в entity, на вход подан пустой dto")
    void test_mergeToEntity_EmptyDtoEmptyEntity() {
        ProfileEntity expected = new ProfileEntity();
        ProfileEntity actual = mapper.mergeToEntity(new ProfileDto(), new ProfileEntity());

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("слияние в entity, на вход подан пустой dto c существующим passportDto")
    void test_mergeToEntity_EmptyDtoEmptyEntityWithPassportSet() {
        ProfileEntity expected = new ProfileEntity();
        ProfileDto dto = new ProfileDto();
        expected.setPassport(getPassportEntity());
        dto.setPassport(getPassportDto());

        ProfileEntity actual = mapper.mergeToEntity(dto, new ProfileEntity());

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("слияние в entity, на вход подан пустой dto c существующим actualRegistrationDto")
    void test_mergeToEntity_EmptyDtoEmptyEntityWithActualRegistrationSet() {
        ProfileEntity expected = new ProfileEntity();
        ProfileDto dto = new ProfileDto();
        expected.setActualRegistration(getActualRegistrationEntity());
        dto.setActualRegistration(getActualRegistrationDto());

        ProfileEntity actual = mapper.mergeToEntity(dto, new ProfileEntity());

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("слияние в entity, на вход подан пустой dto c существующим passportDto c registrationDto=null")
    void test_mergeToEntity_EmptyDtoEmptyEntityWithPassportSetNullRegistration() {
        ProfileEntity expected = new ProfileEntity();
        ProfileDto dto = new ProfileDto();
        PassportEntity passportEntity = getPassportEntity();
        PassportDto passportDto = getPassportDto();
        passportEntity.setRegistration(null);
        passportDto.setRegistration(null);
        expected.setPassport(passportEntity);
        dto.setPassport(passportDto);

        ProfileEntity actual = mapper.mergeToEntity(dto, new ProfileEntity());

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("маппинг в dtoList")
    void test_toDtoList_Success() {
        List<ProfileDto> expected = List.of(getDto(), getDto());
        List<ProfileDto> actual = mapper.toDtoList(List.of(getEntity(), getEntity()));

        assertEquals(expected, actual, "Wrong dto list");
    }

    @Test
    @DisplayName("маппинг в dtoList, на вход подан null")
    void test_toDtoList_NullList() {
        assertNull(mapper.toDtoList(null),
                "List<Dto> is not null");
    }

    @Test
    @DisplayName("маппинг в dtoList, на вход подан список с пустыми entities")
    void test_toDtoList_ListOfEmptyEntities() {
        List<ProfileDto> expected = List.of(new ProfileDto(), new ProfileDto());
        List<ProfileDto> actual = mapper.toDtoList(List.of(new ProfileEntity(), new ProfileEntity()));

        assertEquals(expected, actual, "Wrong dto list");
    }

    private static ProfileEntity getEntity() {
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

    private static ProfileDto getDto() {
        return new ProfileDto(
                1L,
                12345678L,
                "email1",
                "nameOnCard1",
                12345678L,
                12345678L,
                getPassportDto(),
                getActualRegistrationDto()
        );
    }

    private static PassportEntity getPassportEntity() {
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

    private static PassportDto getPassportDto() {
        return new PassportDto(
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
                getRegistrationDto()
        );
    }

    private static ActualRegistrationEntity getActualRegistrationEntity() {
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

    private static ActualRegistrationDto getActualRegistrationDto() {
        return new ActualRegistrationDto(
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

    private static RegistrationEntity getRegistrationEntity() {
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

    private static RegistrationDto getRegistrationDto() {
        return new RegistrationDto(
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
}