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

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    public final static int DAILY_MONEY = 500;
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

    public boolean hasUserLoggedToday(){
        return getLoggedUserOrThrow().getLastLoggedDate().isEqual(LocalDate.now());
    }

    public void save(AppUser appUser){
        repository.save(appUser);
    }

    public void updateLastLoginDate(){
        AppUser user = getLoggedUserOrThrow();
        user.add(DAILY_MONEY);
        user.updateLastLoggedDate();
        save(user);
    }

    public String getUsername(int id){
        return repository.findById(id).orElseThrow().getUsername();
    }

    public AppUser getUserById(int id){
        return repository.findById(id).orElseThrow();
    }

}
