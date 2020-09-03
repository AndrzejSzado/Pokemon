package app.webservice.pokemon.service;

import app.webservice.pokemon.repository.UserRepository;
import org.springframework.stereotype.Service;
import app.webservice.pokemon.model.User;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void save(User user){
        repository.save(user);
    }
}
