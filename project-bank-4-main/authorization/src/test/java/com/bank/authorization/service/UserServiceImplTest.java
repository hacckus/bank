package com.bank.authorization.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import com.bank.authorization.mapper.UserMapper;
import com.bank.authorization.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("User serive")
public class UserServiceImplTest {

    @Mock
    private UserMapper mapper;
    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserServiceImpl userServiceImplForTests;

    static UserEntity userEntity;
    static UserDto userDto;
    static long id = 1L;
    static List<Long> ids;

    @BeforeAll
    public static void initData() {
        userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setPassword("123");
        userEntity.setRole("role");

        userDto = new UserDto();
        userDto.setId(id);
        userDto.setPassword("123");
        userEntity.setRole("role");

        ids = Stream.of(1L, 2L, 3L).toList();
    }

    @Test
    @DisplayName("Получение объекта user по id")
    public void findUserByIdShouldReturnCorrectUserDto() {
        Optional<UserEntity> optional = Optional.of(userEntity);

        when(repository.findById(id)).thenReturn(optional);
        when(mapper.toDTO(userEntity)).thenReturn(userDto);

        UserDto actualUserDto = this.userServiceImplForTests.findById(id);

        Assertions.assertNotNull(actualUserDto);
        Assertions.assertEquals(userDto.getId(), actualUserDto.getId());
        Assertions.assertEquals(userDto.getPassword(), actualUserDto.getPassword());
        Assertions.assertEquals(userDto.getRole(), actualUserDto.getRole());
    }

    @Test
    @DisplayName("Получение объекта user по id, негативный сценарий")
    public void shouldReturnCorrectErrorWhenExequtedMethodFail() {
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            this.userServiceImplForTests.findById(id);
        });

        String exceptionMessage = "Не был найден пользователь с ID " + id;
        String currentExceptionMessage = exception.getMessage();

        Assertions.assertEquals(exceptionMessage, currentExceptionMessage);
    }

    @Test
    @DisplayName("Сохранение объекта user по id")
    public void theMethodSaveShouldCorrectlyCalled() {
        when(this.mapper.toEntity(userDto)).thenReturn(userEntity);
        when(this.repository.save(userEntity)).thenReturn(userEntity);
        when(this.mapper.toDTO(userEntity)).thenReturn(userDto);

        UserDto actualUserDto = this.userServiceImplForTests.save(userDto);

        Assertions.assertNotNull(actualUserDto);
        Assertions.assertEquals(userDto.getPassword(), actualUserDto.getPassword());
        Assertions.assertEquals(userDto.getId(), actualUserDto.getId());
        Assertions.assertEquals(userDto.getRole(), actualUserDto.getRole());
    }

    @Test
    @DisplayName("Сохранение объекта user по id, негативный сценарий")
    public void theMethodSaveShouldTrowError() {
        Exception exception = Assertions.assertThrows(NullPointerException.class, () -> {
            this.userServiceImplForTests.save(null);
        });

        Assertions.assertEquals(NullPointerException.class, exception.getClass());
    }

    @Test
    @DisplayName("Обновление объекта user по id")
    public void theMethodUpdateShouldCorrectlyCalled() {
        Optional<UserEntity> optional = Optional.of(userEntity);
        when(this.repository.findById(id)).thenReturn(optional);
        when(this.mapper.mergeToEntity(userDto, userEntity)).thenReturn(userEntity);
        when(this.repository.save(userEntity)).thenReturn(userEntity);
        when(this.mapper.toDTO(userEntity)).thenReturn(userDto);

        UserDto actualUserDto = this.userServiceImplForTests.update(id, userDto);

        Assertions.assertNotNull(actualUserDto);
        Assertions.assertEquals(userDto.getPassword(), actualUserDto.getPassword());
        Assertions.assertEquals(userDto.getId(), actualUserDto.getId());
        Assertions.assertEquals(userDto.getRole(), actualUserDto.getRole());
    }

    @Test
    @DisplayName("Обновление объекта user по id, негативный сценарий")
    public void theMethodUpdateShouldTrowError() {
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            this.userServiceImplForTests.update(id, null);
        });

        String exceptionMessage = "Не был найден пользователь с ID " + id;
        String currentExceptionMessage = exception.getMessage();

        Assertions.assertEquals(exceptionMessage, currentExceptionMessage);
    }

    @Test
    @DisplayName("Получение списка объектов user по id")
    public void shouldReturnTheListOoUsers() {
        List<UserEntity> userEntities = ids.stream().map(
                (id) -> {
                    UserEntity currentUserEntity = new UserEntity(id, userEntity.getRole(), id,
                            userEntity.getPassword());
                    Optional<UserEntity> optional = Optional.of(currentUserEntity);
                    when(this.repository.findById(id)).thenReturn(optional);
                    return currentUserEntity;
                }).toList();
        List<UserDto> userDtos = ids.stream().map(
                (id) -> new UserDto(id, userDto.getRole(), userEntity.getPassword(), id)).toList();
        when(this.mapper.toDtoList(userEntities)).thenReturn(userDtos);

        List<UserDto> currenUserDtos = this.userServiceImplForTests.findAllByIds(ids);

        ids.forEach((id) -> verify(this.repository, times(1)).findById(id));
        Assertions.assertNotNull(currenUserDtos);
        Assertions.assertIterableEquals(
                userDtos.stream().map((userDto) -> userDto.getId()).toList(),
                currenUserDtos.stream().map((currentUserDto) -> currentUserDto.getId()).toList());
    }

    @Test
    @DisplayName("Получение списка объектов user по id, негативный сценарий")
    public void theMethodFindAllByIdsShouldReturnException() {
        Exception exception = Assertions.assertThrows(NullPointerException.class, () -> {
            this.userServiceImplForTests.findAllByIds(null);
        });

        Assertions.assertEquals(NullPointerException.class, exception.getClass());
    }

    @Test
    @DisplayName("Получение списка объектов user по id, негативный сценарий")
    public void theMethodFindAllByIdsShouldReturnEntityNotFoundException() {
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            this.userServiceImplForTests.findAllByIds(ids);
        });

        String exceptionMessage = "Не был найден пользователь с ID " + ids.get(0);
        String currentExceptionMessage = exception.getMessage();

        Assertions.assertEquals(exceptionMessage, currentExceptionMessage);

        Assertions.assertEquals(exceptionMessage, exception.getMessage());
    }
}
