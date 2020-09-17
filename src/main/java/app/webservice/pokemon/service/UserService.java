package app.webservice.pokemon.service;

import app.webservice.pokemon.exceptions.UserServiceException;
import app.webservice.pokemon.repository.UserRepository;
import app.webservice.pokemon.request.UserRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import app.webservice.pokemon.model.User;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository repository;
    private PasswordEncoder encoder;
    private User loggedUser = null;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public void login(UserRequest userRequest){
        Optional<User> user = repository.findByName(userRequest.getEmail());
        if (user.isPresent() && user.get().getPassword().equals(userRequest.getPassword())){
            System.out.println("logged as: " + user.get());
            loggedUser = user.get();
        }
        else {
            throw new UserServiceException("Wrong email or password");
        }
    }

    public void save(UserRequest userRequest){
        User user = new User(userRequest.getEmail(),encoder.encode(userRequest.getPassword()));
        if(repository.existsByName(user.getUsername())){
            throw new UserServiceException("User already exists");
        }
        else {
            repository.save(user);
        }
    }

    public String getStatus(){
        return !isLogged()? "not logged":"logged as " + loggedUser.getUsername();
    }

    public boolean isLogged(){
        return loggedUser != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User) {
            return (User)principal;
        } else {
            throw new UserServiceException("User not logged");
        }
    }
}
