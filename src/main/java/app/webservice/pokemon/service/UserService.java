package app.webservice.pokemon.service;

import app.webservice.pokemon.exceptions.UserServiceException;
import app.webservice.pokemon.model.AppUser;
import app.webservice.pokemon.repository.UserRepository;
import app.webservice.pokemon.request.UserRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository repository;
    private PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public void register(UserRequest userRequest){
        AppUser appUser = new AppUser(userRequest.getEmail(),encoder.encode(userRequest.getPassword()));
        if(repository.existsByName(appUser.getUsername())){
            throw new UserServiceException("User already exists");
        }
        else {
            save(appUser);
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

    public Optional<AppUser> getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AppUser) {
            return Optional.of((AppUser)principal);
        } else {
            return Optional.empty();
        }
    }

    public AppUser getLoggedUserOrThrow() {
        return getLoggedUser().orElseThrow();
    }

    public void save(AppUser appUser){
        repository.save(appUser);
    }
}
