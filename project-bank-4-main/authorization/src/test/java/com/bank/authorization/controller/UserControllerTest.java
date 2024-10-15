package com.bank.authorization.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@WebMvcTest(controllers = UserController.class)
@DisplayName("User controller")
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService service;

    private long id;
    private UserDto userDto;
    private String userDtoAsJson;

    @BeforeEach
    public void initData() throws Exception {
        id = 1L;
        userDto = new UserDto(id, "role", "Jhon", id);
        userDtoAsJson = objectMapper.writeValueAsString(userDto);
    }

    @Test
    @DisplayName("Post запрос для создания User, позитивный сценарий")
    public void shouldReturnValidResponseEntityWhenMethodCreateWasCalled() throws Exception {
        when(this.service.save(userDto)).thenReturn(userDto);

        mockMvc.perform(post("/create")
                .content(userDtoAsJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(content().json(userDtoAsJson));
    }

    @Test
    @DisplayName("Post запрос для создания User, негативный сценарий (код 500)")
    public void theMethodCreateShouldReturn500StatusCodeIfPasswordPassedAsNull() throws Exception {
        JsonNode jsonNode = objectMapper.readTree(userDtoAsJson);
        ObjectNode currObject = (ObjectNode) jsonNode;
        currObject.set("password", null);
        String userDtoWithoutPassword = objectMapper.writeValueAsString(currObject);

        userDto.setPassword(null);
        when(this.service.save(userDto)).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/create")
                .content(userDtoWithoutPassword)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("GET запрос для получения одного объекта User, позитивный сценарий")
    public void shouldReturnValidResponseEntityWhenMethodReadWasCalled() throws Exception {
        String expextedString = objectMapper.writeValueAsString(userDto);
        when(this.service.findById(id)).thenReturn(userDto);

        mockMvc.perform(get("/read/{id}", userDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.role").value(userDto.getRole()))
                .andExpect(content().json(expextedString));
    }

    @Test
    @DisplayName("GET запрос для получения одного объекта User, негативный сценарий (Код 404)")
    public void methodReadShouldReturnNotFoundExeption() throws Exception {
        when(this.service.findById(anyLong())).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/read/{id}", anyLong())).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT запрос для обновления одного объекта User, позитивный сценарий")
    public void shouldReturnValidResponseEntityWhenMethodUpdateWasCalled() throws Exception {
        UserDto updatedUserDto = new UserDto(userDto.getId(), userDto.getRole(), userDto.getPassword(),
                userDto.getProfileId());
        updatedUserDto.setPassword("new");
        String updatedUserDtoAsJson = objectMapper.writeValueAsString(updatedUserDto);
        when(this.service.update(id, userDto)).thenReturn(updatedUserDto);

        mockMvc.perform(put("/{id}/update", userDto.getId())
                .content(userDtoAsJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value(updatedUserDto.getPassword()))
                .andExpect(jsonPath("$.id").value(updatedUserDto.getId()))
                .andExpect(content().json(updatedUserDtoAsJson));
    }

    @Test
    @DisplayName("PUT запрос для обновления одного объекта User, негативный сценарий (Код 404)")
    public void methodUpdateShouldReturnNotFoundExpetion() throws Exception {
        when(this.service.update(id, userDto)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(put("/{id}/update", userDto.getId())
                .content(userDtoAsJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET запрос для получения нескольких объектов User, позитивный сценарий")
    public void shouldReturnValidResponseEntityWhenMethodReadAllWasCalled() throws Exception {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<UserDto> userDtos = ids.stream().map((id) -> new UserDto(id, "Jhon", "Doe", id)).toList();
        String idsAsString = ids.stream()
                .map((id) -> id.toString())
                .collect(Collectors.joining(","));
        String userDtosAsJson = objectMapper.writeValueAsString(userDtos);
        when(this.service.findAllByIds(ids)).thenReturn(userDtos);

        mockMvc.perform(get("/read/all")
                .param("ids", idsAsString)).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[1].id").value(userDtos.get(1).getId()))
                .andExpect(content().json(userDtosAsJson));
    }

    @Test
    @DisplayName("GET запрос для получения нескольких объектов User, негативный сценарий (Код 400)")
    public void theMethodReadAllShouldReturn400StatusCodeIfPassTotheServerInQueryNotANumber() throws Exception {
        List<String> fakeIds = List.of("A", "B", "C");
        String idsAsString = fakeIds.stream()
                .map((id) -> fakeIds.toString())
                .collect(Collectors.joining(","));

        mockMvc.perform(get("/read/all").param("ids", idsAsString)).andExpect(status().isBadRequest());
    }
}
