package app.webservice.pokemon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import app.webservice.pokemon.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
