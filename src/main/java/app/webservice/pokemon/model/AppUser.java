package app.webservice.pokemon.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Card,Integer> cards = new HashMap<>();

    public AppUser(String email, String password) {
        this.name = email;
        this.password = password;
    }

    private AppUser() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
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

    public Map<Card, Integer> getCards() {
        return cards;
    }

    public int getId() {
        return id;
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
