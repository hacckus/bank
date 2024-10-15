package com.bank.profile.mapper;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
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
class AccountDetailsIdMapperTest {

    @InjectMocks
    private AccountDetailsIdMapperImpl mapper;

    @Test
    @DisplayName("маппинг в entity")
    void test_toEntity_Success() {
        AccountDetailsIdEntity actual = mapper.toEntity(getDto());
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
        assertEquals(new AccountDetailsIdEntity(), mapper.toEntity(new AccountDetailsIdDto()),
                "Wrong entity");
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан dto c passportDto=null")
    void test_toEntity_WithProfileSetNullPassportDto() {
        ProfileDto profileDto = getProfileDto();
        profileDto.setPassport(null);
        AccountDetailsIdDto dto = getDto();
        dto.setProfile(profileDto);

        ProfileEntity profileEntity = getProfileEntity();
        profileEntity.setPassport(null);
        AccountDetailsIdEntity expected = getEntity();
        expected.setProfile(profileEntity);

        AccountDetailsIdEntity actual = mapper.toEntity(dto);
        actual.setId(1L);

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан dto c actualRegistrationDto=null")
    void test_toEntity_WithProfileSetNullActualRegistrationDto() {
        ProfileDto profileDto = getProfileDto();
        profileDto.setActualRegistration(null);
        AccountDetailsIdDto dto = getDto();
        dto.setProfile(profileDto);

        ProfileEntity profileEntity = getProfileEntity();
        profileEntity.setActualRegistration(null);
        AccountDetailsIdEntity expected = getEntity();
        expected.setProfile(profileEntity);

        AccountDetailsIdEntity actual = mapper.toEntity(dto);
        actual.setId(1L);

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан dto c passportDto c registrationDto=null")
    void test_toEntity_WithProfileSetWithPassportSetNullRegistration() {
        PassportDto passportDto = getPassportDto();
        passportDto.setRegistration(null);
        ProfileDto profileDto = getProfileDto();
        profileDto.setPassport(passportDto);
        AccountDetailsIdDto dto = getDto();
        dto.setProfile(profileDto);
        PassportEntity passportEntity = getPassportEntity();
        passportEntity.setRegistration(null);
        ProfileEntity profileEntity = getProfileEntity();
        profileEntity.setPassport(passportEntity);
        AccountDetailsIdEntity expected = getEntity();
        expected.setProfile(profileEntity);

        AccountDetailsIdEntity actual = mapper.toEntity(dto);
        actual.setId(1L);

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("маппинг в dto")
    void test_toDto_Success() {
        AccountDetailsIdDto actual = mapper.toDto(getEntity());

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
        assertEquals(new AccountDetailsIdDto(), mapper.toDto(new AccountDetailsIdEntity()),
                "Wrong entity");
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан entity c passportEntity=null")
    void test_toDto_WithProfileSetNullPassportEntity() {
        ProfileEntity profileEntity = getProfileEntity();
        profileEntity.setPassport(null);
        AccountDetailsIdEntity entity = getEntity();
        entity.setProfile(profileEntity);
        ProfileDto profileDto = getProfileDto();
        profileDto.setPassport(null);
        AccountDetailsIdDto expected = getDto();
        expected.setProfile(profileDto);

        assertEquals(expected, mapper.toDto(entity), "Wrong dto");
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан entity c actualRegistrationEntity=null")
    void test_toDto_WithProfileSetNullActualRegistrationEntity() {
        ProfileEntity profileEntity = getProfileEntity();
        profileEntity.setActualRegistration(null);
        AccountDetailsIdEntity entity = getEntity();
        entity.setProfile(profileEntity);
        ProfileDto profileDto = getProfileDto();
        profileDto.setActualRegistration(null);
        AccountDetailsIdDto expected = getDto();
        expected.setProfile(profileDto);

        assertEquals(expected, mapper.toDto(entity), "Wrong dto");
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан entity c passportEntity c registrationEntity=null")
    void test_toDto_WithProfileSetWithPassportSetNullRegistrationEntity() {
        PassportEntity passportEntity = getPassportEntity();
        passportEntity.setRegistration(null);
        ProfileEntity profileEntity = getProfileEntity();
        profileEntity.setPassport(passportEntity);
        AccountDetailsIdEntity entity = getEntity();
        entity.setProfile(profileEntity);
        PassportDto passportDto = getPassportDto();
        passportDto.setRegistration(null);
        ProfileDto profileDto = getProfileDto();
        profileDto.setPassport(passportDto);
        AccountDetailsIdDto expected = getDto();
        expected.setProfile(profileDto);

        assertEquals(expected, mapper.toDto(entity), "Wrong dto");
    }

    @Test
    @DisplayName("слияние в entity")
    void test_mergeToEntity_Success() {
        AccountDetailsIdEntity expected = getEntity();
        AccountDetailsIdEntity actual = mapper.mergeToEntity(getDto(), getEntity());

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
        AccountDetailsIdEntity expected = new AccountDetailsIdEntity();
        AccountDetailsIdEntity actual = mapper.mergeToEntity(new AccountDetailsIdDto(), new AccountDetailsIdEntity());

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("слияние в entity, на вход подан пустой dto c существующим profileDto")
    void test_mergeToEntity_EmptyDtoEmptyEntityWithProfileSet() {
        AccountDetailsIdEntity expected = new AccountDetailsIdEntity();
        AccountDetailsIdDto dto = new AccountDetailsIdDto();
        expected.setProfile(getProfileEntity());
        dto.setProfile(getProfileDto());

        AccountDetailsIdEntity actual = mapper.mergeToEntity(dto, new AccountDetailsIdEntity());

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("слияние в entity, на вход подан пустой dto c profileDto c passportDto=null")
    void test_mergeToEntity_EmptyDtoEmptyEntityWithProfileSetNullPassport() {
        AccountDetailsIdEntity expected = new AccountDetailsIdEntity();
        AccountDetailsIdDto dto = new AccountDetailsIdDto();
        ProfileEntity profileEntity = getProfileEntity();
        ProfileDto profileDto = getProfileDto();
        profileEntity.setPassport(null);
        profileDto.setPassport(null);
        expected.setProfile(profileEntity);
        dto.setProfile(profileDto);

        AccountDetailsIdEntity actual = mapper.mergeToEntity(dto, new AccountDetailsIdEntity());

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("слияние в entity, на вход подан пустой dto c profileDto c actualRegistrationDto=null")
    void test_mergeToEntity_EmptyDtoEmptyEntityWithProfileSetNullActualRegistration() {
        AccountDetailsIdEntity expected = new AccountDetailsIdEntity();
        AccountDetailsIdDto dto = new AccountDetailsIdDto();
        ProfileEntity profileEntity = getProfileEntity();
        ProfileDto profileDto = getProfileDto();
        profileEntity.setActualRegistration(null);
        profileDto.setActualRegistration(null);
        expected.setProfile(profileEntity);
        dto.setProfile(profileDto);

        AccountDetailsIdEntity actual = mapper.mergeToEntity(dto, new AccountDetailsIdEntity());

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("слияние в entity, на вход подан пустой dto c profileDto c passportDto c registrationDto=null")
    void test_mergeToEntity_EmptyDtoEmptyEntityWithProfileSetWithPassportSetNullRegistration() {
        AccountDetailsIdEntity expected = new AccountDetailsIdEntity();
        AccountDetailsIdDto dto = new AccountDetailsIdDto();
        ProfileEntity profileEntity = getProfileEntity();
        ProfileDto profileDto = getProfileDto();
        PassportEntity passportEntity = profileEntity.getPassport();
        passportEntity.setRegistration(null);
        PassportDto passportDto = profileDto.getPassport();
        passportDto.setRegistration(null);
        profileEntity.setPassport(passportEntity);
        profileDto.setPassport(passportDto);
        expected.setProfile(profileEntity);
        dto.setProfile(profileDto);

        AccountDetailsIdEntity actual = mapper.mergeToEntity(dto, new AccountDetailsIdEntity());

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("маппинг в dtoList")
    void test_toDtoList_Success() {
        List<AccountDetailsIdDto> expected = List.of(getDto(), getDto());
        List<AccountDetailsIdDto> actual = mapper.toDtoList(List.of(getEntity(), getEntity()));

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
        List<AccountDetailsIdDto> expected = List.of(new AccountDetailsIdDto(), new AccountDetailsIdDto());
        List<AccountDetailsIdDto> actual = mapper.toDtoList(
                List.of(new AccountDetailsIdEntity(), new AccountDetailsIdEntity()));

        assertEquals(expected, actual, "Wrong dto list");
    }

    private static AccountDetailsIdEntity getEntity() {
        return new AccountDetailsIdEntity(
                1L,
                1L,
                getProfileEntity()
        );
    }

    private static AccountDetailsIdDto getDto() {
        return new AccountDetailsIdDto(
                1L,
                1L,
                getProfileDto()
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

    private static ProfileDto getProfileDto() {
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