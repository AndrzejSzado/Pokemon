package app.webservice.pokemon.controller;

import app.webservice.pokemon.service.UserService;
import app.webservice.pokemon.view_model.SessionViewModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Component
public class SessionDataUpdater {
    private UserService userService;
    private SessionViewModel sessionViewModel;

    public SessionViewModel updateSessionData(){
        boolean logged = userService.isLogged();
        if (logged){
            sessionViewModel.setMoney(userService.getLoggedUserOrThrow().getMoney());
        }
        return sessionViewModel;
    }
}
