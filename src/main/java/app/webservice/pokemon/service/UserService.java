package app.webservice.pokemon.service;

import app.webservice.pokemon.exceptions.UserServiceException;
import app.webservice.pokemon.repository.UserRepository;
import app.webservice.pokemon.request.UserRequest;
import org.springframework.stereotype.Service;
import app.webservice.pokemon.model.User;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;
    private User loggedUser = null;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void login(UserRequest userRequest){
        Optional<User> user = repository.findByEmail(userRequest.getEmail());
        if (user.isPresent() && user.get().getPassword().equals(userRequest.getPassword())){
            System.out.println("logged as: " + user.get());
            loggedUser = user.get();
        }
        else {
            throw new UserServiceException("Wrong email or password");
        }
    }

    public void save(UserRequest userRequest){
        User user = new User(userRequest.getEmail(),userRequest.getPassword());
        if(repository.existsByEmail(user.getEmail())){
            throw new UserServiceException("User already exists");
        }
        else {
            repository.save(user);
        }
    }

    public String getStatus(){
        return !isLogged()? "not logged":"logged as " + loggedUser.getEmail();
    }

    public boolean isLogged(){
        return loggedUser != null;
    }
}
