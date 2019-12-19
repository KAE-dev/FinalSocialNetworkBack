package ru.rosbank.javaschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.rosbank.javaschool.dto.UserProfileResponseDto;
import ru.rosbank.javaschool.dto.UserSaveRequestDto;
import ru.rosbank.javaschool.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserProfileResponseDto entityToUserProfileResponseDto(UserEntity entity);

    @Mappings({
            @Mapping(target = "password", expression = "java(encoder.encode(dto.getPassword()))"),
            @Mapping(target = "authorities",
                    expression = "java((java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority(\"ROLE_USER\"))))"),
            @Mapping(target = "accountNonExpired", constant = "true"),
            @Mapping(target = "accountNonLocked", constant = "true"),
            @Mapping(target = "credentialsNonExpired", constant = "true"),
            @Mapping(target = "enabled", constant = "true"),
    })
    UserEntity dtoToUserEntity(UserSaveRequestDto dto, PasswordEncoder encoder);
}
