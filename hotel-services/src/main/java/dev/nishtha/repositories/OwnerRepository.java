package dev.nishtha.repositories;

import dev.nishtha.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByName(String bootUserName);

    Optional<Owner> findOwnerByName(String userName);
}
