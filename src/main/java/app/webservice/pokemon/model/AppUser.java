package app.webservice.pokemon.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String password;
    private int money;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Card,Integer> cards = new HashMap<>();

    public AppUser(String email, String password) {
        this.name = email;
        this.password = password;
        this.money = 1_000;
    }

    private AppUser() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return name;
    }

    public void addCards(Collection<Card> newCards){
        newCards.forEach(this::addCard);
    }

    public void addCard(Card card){
        if (cards.containsKey(card)){
            cards.put(card, cards.get(card)+1);
        }
        else {
            cards.put(card, 1);
        }
    }

    public void removeCard(Card card, int quantity){
        int newQuantity = cards.get(card) - quantity;
        if (newQuantity <= 0){
            cards.remove(card);
        }else {
            cards.put(card, newQuantity);
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void pay(int amount){
        money = money - amount;
    }

    public void add(int amount){
        money = money - amount;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
