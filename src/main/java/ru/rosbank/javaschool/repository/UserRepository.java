package ru.rosbank.javaschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rosbank.javaschool.entity.UserEntity;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);

}
