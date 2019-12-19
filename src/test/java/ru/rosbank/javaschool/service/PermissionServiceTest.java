package ru.rosbank.javaschool.service;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.rosbank.javaschool.dto.UserSaveRequestDto;
import ru.rosbank.javaschool.entity.UserEntity;
import ru.rosbank.javaschool.mapper.UserMapper;

import ru.rosbank.javaschool.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PermissionServiceTest {


    UserRepository repository = mock(UserRepository.class);
    UserMapper mapper = mock(UserMapper.class);
    PasswordEncoder encoder = mock(PasswordEncoder.class);


    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        mapper = mock(UserMapper.class);
        encoder = mock(PasswordEncoder.class);

    }


    @Test
    void loadUserByUsername() {

        val expected = new UserEntity(1, "User", "user", "123456",  "89999999999", List.of(new SimpleGrantedAuthority("ROLE_USER")), false, false, false, false);
        String username = "ivan";
        when(repository.findByUsername(username)).thenReturn(Optional.of(expected));

        val service = new UserService(repository, encoder, mapper);
        val actual = (UserEntity) service.loadUserByUsername(username);
        assertEquals(expected, actual);
    }

    @Test
    void save() {

        UserSaveRequestDto dto = new UserSaveRequestDto(0, "Ivan", "ivan", "secret",  null);
        val user = new UserEntity(0, "User", "user", "123456",  "89999999999", List.of(new SimpleGrantedAuthority("ROLE_USER")), false, false, false, false);
        val expected = new UserEntity(1, "User", "user", "123456",  "89999999999", List.of(new SimpleGrantedAuthority("ROLE_USER")), false, false, false, false);

        when(mapper.dtoToUserEntity(dto, encoder)).thenReturn(user);
        when(repository.save(user)).thenReturn(expected);

        val service = new UserService(repository, encoder, mapper);
        val actual = service.save(dto);
        assertEquals(expected, actual);
    }

}
