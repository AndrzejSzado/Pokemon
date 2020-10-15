package app.webservice.pokemon.repository;

import app.webservice.pokemon.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findByName(String name);

    boolean existsByName(String name);
}
