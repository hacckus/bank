package com.bank.authorization.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;

import java.util.List;

@DisplayName("User mapper")
public class UserMapperTest {

    private final UserMapper userMapperForTest = new UserMapperImpl();

    static UserEntity userEntity;
    static UserDto userDto;
    static long id = 1L;

    @BeforeAll
    public static void initData() {
        userEntity = new UserEntity(id, "Jhon", id, "Doe");
        userDto = new UserDto(id, "Jhon", "Doe", id);
    }

    @Test
    @DisplayName("Маппинг в DTO")
    void shouldConvertUserEntityToDto() {
        UserDto currentUserDto = userMapperForTest.toDTO(userEntity);

        Assertions.assertNotNull(currentUserDto);
        Assertions.assertEquals(userEntity.getId(), currentUserDto.getId());
        Assertions.assertEquals(userEntity.getPassword(), currentUserDto.getPassword());
    }

    @Test
    @DisplayName("Маппинг в DTO (На вход подан null)")
    void shouldReturnNullIfEntityNotPassedInMethodToDto() {
        UserDto userDto = this.userMapperForTest.toDTO(null);

        Assertions.assertNull(userDto);
    }

    @Test
    @DisplayName("Конвертация объекта DTO в объект Entity")
    void shouldConvertUserDtoToEntity() {
        UserEntity currentUserEntity = userMapperForTest.toEntity(userDto);

        Assertions.assertNotNull(currentUserEntity);
        Assertions.assertEquals(userDto.getProfileId(), currentUserEntity.getProfileId());
        Assertions.assertEquals(userDto.getRole(), currentUserEntity.getRole());
    }

    @Test
    @DisplayName("Конвертация объекта DTO в объект Entity (На вход подан null)")
    void shouldReturnNullIfEntityNotPassedInMethodToEntity() {
        UserEntity userEntity = this.userMapperForTest.toEntity(null);

        Assertions.assertNull(userEntity);
    }

    @Test
    @DisplayName("Конвертация списка объектов Entity в список объектов DTO")
    void shouldConvertListOfUserEntityToListOfDto() {
        List<UserEntity> userEntityes = List.of(1L, 2L, 3L)
                .stream().map(
                        (id) -> new UserEntity(id, userEntity.getRole(), id, userEntity.getPassword()))
                .toList();

        List<UserDto> currenUserDtos = userMapperForTest.toDtoList(userEntityes);

        Assertions.assertNotNull(currenUserDtos);
        Assertions.assertEquals(userEntityes.get(1).getId(), currenUserDtos.get(1).getId());
        Assertions.assertEquals(userEntityes.get(2).getPassword(), currenUserDtos.get(2).getPassword());
        Assertions.assertNotEquals(userEntityes.get(0), currenUserDtos.get(0));
    }

    @Test
    @DisplayName("Конвертация списка объектов Entity в список объектов DTO (на вход подан null)")
    void shouldReturnNullIfEntityNotPassedInMethodToDtoList() {
        List<UserDto> userDtos = this.userMapperForTest.toDtoList(null);

        Assertions.assertNull(userDtos);
    }

    @Test
    @DisplayName("Слияние объекта DTO c Entity")
    void shouldMergeUserDtoToUserEntity() {
        userDto.setRole("Aurelius");
        userEntity.setPassword("Marcus");

        UserEntity currentMergedUserEntity = userMapperForTest.mergeToEntity(userDto, userEntity);

        Assertions.assertNotNull(currentMergedUserEntity);
        Assertions.assertEquals(userDto.getPassword(), currentMergedUserEntity.getPassword());
        Assertions.assertEquals(currentMergedUserEntity.getRole(), userDto.getRole());
    }

    @Test
    @DisplayName("Слияние объекта DTO c Entity (На вход подан null)")
    void shouldReturnNullIfEntityNotPassedInMethodMergToEntity() {
        UserEntity userEntity = this.userMapperForTest.mergeToEntity(null, null);

        Assertions.assertNull(userEntity);
    }
}
