package ru.rosbank.javaschool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rosbank.javaschool.dto.UserSaveRequestDto;
import ru.rosbank.javaschool.entity.UserEntity;
import ru.rosbank.javaschool.mapper.UserMapper;
import ru.rosbank.javaschool.repository.UserRepository;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public UserEntity save(UserSaveRequestDto dto) {
        return repository.save(mapper.dtoToUserEntity(dto, encoder));
    }


}
