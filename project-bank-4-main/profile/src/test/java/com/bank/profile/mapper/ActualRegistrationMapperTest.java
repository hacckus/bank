package com.bank.profile.mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ActualRegistrationMapperTest {

    @InjectMocks
    private ActualRegistrationMapperImpl mapper;

    @Test
    @DisplayName("маппинг в entity")
    void test_toEntity_Success() {
        ActualRegistrationEntity actual = mapper.toEntity(getDto());
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
    @DisplayName("маппинг в dto")
    void test_toDto_Success() {
        ActualRegistrationDto actual = mapper.toDto(getEntity());

        assertEquals(getDto(), actual, "Wrong dto");
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null")
    void test_toDto_NullEntity() {
        assertNull(mapper.toDto(null),
                "Dto is not null");
    }

    @Test
    @DisplayName("слияние в entity")
    void test_mergeToEntity_Success() {
        ActualRegistrationEntity expected = getEntity();
        ActualRegistrationEntity actual = mapper.mergeToEntity(getDto(), getEntity());

        assertEquals(expected, actual, "Wrong entity");
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void test_mergeToEntity_NullDtoNullEntity() {
        assertNull(mapper.mergeToEntity(null, null),
                "Entity is not null");
    }

    @Test
    @DisplayName("маппинг в dtoList")
    void test_toDtoList_Success() {
        List<ActualRegistrationDto> expected = List.of(getDto(), getDto());
        List<ActualRegistrationDto> actual = mapper.toDtoList(List.of(getEntity(), getEntity()));

        assertEquals(expected, actual, "Wrong dto list");
    }

    @Test
    @DisplayName("маппинг в dtoList, на вход подан null")
    void test_toDtoList_NullList() {
        assertNull(mapper.toDtoList(null),
                "List<Dto> is not null");
    }

    private static ActualRegistrationEntity getEntity() {
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

    private static ActualRegistrationDto getDto() {
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

}