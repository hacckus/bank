package com.bank.profile.mapper;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.PassportEntity;
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
class PassportMapperTest {

    @InjectMocks
    private PassportMapperImpl mapper;

    @Test
    @DisplayName("маппинг в entity")
    void test_toEntity_Success() {
        PassportEntity actual = mapper.toEntity(getDto());
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
        assertEquals(new PassportEntity(), mapper.toEntity(new PassportDto()),
                "Wrong entity");
    }

    @Test
    @DisplayName("маппинг в dto")
    void test_toDto_Success() {
        PassportDto actual = mapper.toDto(getEntity());

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
        assertEquals(new PassportDto(), mapper.toDto(new PassportEntity()),
                "Wrong dto");
    }

    @Test
    @DisplayName("слияние в entity")
    void test_mergeToEntity_Success() {
        PassportEntity expected = getEntity();
        PassportEntity actual = mapper.mergeToEntity(getDto(), getEntity());

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
        PassportEntity expected = new PassportEntity();
        PassportEntity actual = mapper.mergeToEntity(new PassportDto(), new PassportEntity());

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("слияние в entity, на вход подан пустой dto c существующим registrationDto")
    void test_mergeToEntity_EmptyDtoEmptyEntitySetRegistrationDto() {
        PassportEntity expected = new PassportEntity();
        PassportDto dto = new PassportDto();
        expected.setRegistration(getRegistrationEntity());
        dto.setRegistration(getRegistrationDto());

        PassportEntity actual = mapper.mergeToEntity(dto, new PassportEntity());

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("маппинг в dtoList")
    void test_toDtoList_Success() {
        List<PassportDto> expected = List.of(getDto(), getDto());
        List<PassportDto> actual = mapper.toDtoList(List.of(getEntity(), getEntity()));

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
        List<PassportDto> expected = List.of(new PassportDto(), new PassportDto());
        List<PassportDto> actual = mapper.toDtoList(List.of(new PassportEntity(), new PassportEntity()));

        assertEquals(expected, actual, "Wrong dto list");
    }

    private static PassportEntity getEntity() {
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

    private static PassportDto getDto() {
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

    private static RegistrationDto getRegistrationDto() {
        return new RegistrationDto(
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