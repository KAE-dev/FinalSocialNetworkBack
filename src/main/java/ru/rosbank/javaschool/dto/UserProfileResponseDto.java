package ru.rosbank.javaschool.dto;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class UserProfileResponseDto {
    private int id;
    private String name;
    private String username;
    private String phoneNumber;
    private Collection<GrantedAuthority> authorities;
}
