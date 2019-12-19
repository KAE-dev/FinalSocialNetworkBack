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


class UserServiceTest {

    private UserRepository repository;
    private UserMapper mapper;
    private PasswordEncoder encoder ;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        mapper = mock(UserMapper.class);
        encoder = mock(PasswordEncoder.class);
    }

    @Test
    void loadUserByUsername() {

        val user = new UserEntity(1, "User", "user", "123456", "88888888888", List.of(new SimpleGrantedAuthority("ROLE_USER")), false, false, false, false);
        String username = "user";
        when(repository.findByUsername(username)).thenReturn(Optional.of(user));

        val service = new UserService(repository,encoder, mapper);
        val actual = (UserEntity) service.loadUserByUsername(username);
        assertEquals(user, actual);
    }

    @Test
    void save() {
        val dto = new UserSaveRequestDto(0, "User", "user", "123456", "88888888888");
        val user = new UserEntity(0, "User", "user", "123456", "88888888888", List.of(new SimpleGrantedAuthority("ROLE_USER")), false, false, false, false);
        val expected = new UserEntity(1, "User", "user", "123456", "88888888888", List.of(new SimpleGrantedAuthority("ROLE_USER")), true, true, true, true);
        when(mapper.dtoToUserEntity(dto, encoder)).thenReturn(user);
        when(repository.save(user)).thenReturn(expected);

        val service = new UserService(repository,encoder, mapper);
        val actual = service.save(dto);
        assertEquals(expected, actual);
    }
}
