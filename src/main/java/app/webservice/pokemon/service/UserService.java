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

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
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
        return !isLogged()? "not logged":"logged as " + getLoggedUser().get().getUsername();
    }

    public boolean isLogged(){
        return getLoggedUser().isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

//    public User getLoggedUserOrThrow() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (principal instanceof User) {
//            return (User)principal;
//        } else {
//            throw new UserServiceException("User not logged");
//        }
//    }
    public Optional<User> getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return Optional.of((User)principal);
        } else {
            return Optional.empty();
        }
    }
}
