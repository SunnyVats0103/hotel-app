package dev.nishtha.core.repositories;

import dev.nishtha.core.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);

    boolean existsByEmail(String username);

    UserEntity findByEmail(String username);
}
