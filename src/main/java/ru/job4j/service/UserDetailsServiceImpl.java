package ru.job4j.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonRepository pr;

    public UserDetailsServiceImpl(PersonRepository pr) {
        this.pr = pr;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Person user = pr.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(login);
        }
        return new User(user.getLogin(), user.getPassword(), Collections.emptyList());
    }
}
