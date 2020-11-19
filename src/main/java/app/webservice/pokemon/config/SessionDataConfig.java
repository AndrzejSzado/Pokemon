package app.webservice.pokemon.config;

import app.webservice.pokemon.view_model.SessionViewModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class SessionDataConfig {

    @Bean
    @Scope(
            value = WebApplicationContext.SCOPE_SESSION,
            proxyMode = ScopedProxyMode.TARGET_CLASS)
    public SessionViewModel buildSessionViewModel() {
        return new SessionViewModel();
    }
}
