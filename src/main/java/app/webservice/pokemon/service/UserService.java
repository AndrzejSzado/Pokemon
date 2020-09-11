package app.webservice.pokemon.service;

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
            System.out.println("zalogowany na: " + user.get());
            loggedUser = user.get();
        }
        else {
            //TODO error
        }
    }

    public void save(UserRequest userRequest){
        User user = new User(userRequest.getEmail(),userRequest.getPassword());
        repository.save(user);
    }

    public String getStatus(){
        return isLogged()? "niezalogowany":"zalogowany jako " + loggedUser.getEmail();
    }

    public boolean isLogged(){
        return loggedUser == null;
    }
}
