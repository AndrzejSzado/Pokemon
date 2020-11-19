package app.webservice.pokemon.controller;

import app.webservice.pokemon.view_model.SessionViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

@Controller
public abstract class BaseController {
    @Autowired
    private SessionDataUpdater sessionDataUpdater;

    public void updateSessionData(HttpSession session){
        SessionViewModel sessionViewModel = sessionDataUpdater.updateSessionData();
        session.setAttribute("myData", sessionViewModel);
    }
}
