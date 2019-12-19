package ru.rosbank.javaschool.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.rosbank.javaschool.dto.UserProfileResponseDto;
import ru.rosbank.javaschool.dto.UserSaveRequestDto;
import ru.rosbank.javaschool.entity.UserEntity;
import ru.rosbank.javaschool.mapper.UserMapper;
import ru.rosbank.javaschool.service.UserService;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class RestUserController {
    private final UserService service;
    private final UserMapper mapper;

    @GetMapping("/me")
    public UserProfileResponseDto me(@AuthenticationPrincipal UserEntity entity) {
        return mapper.entityToUserProfileResponseDto(entity);
    }


    @PostMapping
    public UserProfileResponseDto save(@RequestBody UserSaveRequestDto dto) {
        return mapper.entityToUserProfileResponseDto(service.save(dto));
    }
}
