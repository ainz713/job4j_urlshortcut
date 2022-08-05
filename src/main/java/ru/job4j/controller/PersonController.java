package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.Generator;
import ru.job4j.domain.Registration;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class.getSimpleName());

    private final ObjectMapper objectMapper;

    private BCryptPasswordEncoder encoder;

    private final PersonRepository pr;

    public PersonController(ObjectMapper objectMapper, final PersonRepository pr, BCryptPasswordEncoder encoder) {
        this.objectMapper = objectMapper;
        this.pr = pr;
        this.encoder = encoder;
    }

    @GetMapping("/")
    public ResponseEntity<List<Person>> findAll() {
        List<Person> rsl = (List<Person>) this.pr.findAll();
        return new ResponseEntity<>(
                rsl,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        if (id > 5) {
            throw new IllegalArgumentException(
                    "Invalid id. Id must be < 5.");
        }
        var person = this.pr.findById(id);
        return new ResponseEntity<>(
                person.orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Person is not found. Please, check id.")),
                HttpStatus.OK);
    }

    @PostMapping("/registration")
    public Registration register(@Valid @RequestBody Person person) {
        Optional<Person> findSite = pr.findBySite(person.getSite());
        if (findSite.isPresent()) {
            return new Registration(
                    false, findSite.get().getLogin(), findSite.get().getPassword()
            );
        }
        String login = new Generator().randomString();
        person.setLogin(login);
        String password = new Generator().randomString();
        person.setPassword(encoder.encode(password));
        this.pr.save(person);
        return new Registration(true, login, password);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person p = new Person();
        p.setId(id);
        this.pr.delete(p);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }
}
