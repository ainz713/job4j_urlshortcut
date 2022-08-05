package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.Generator;
import ru.job4j.domain.Person;
import ru.job4j.domain.Url;
import ru.job4j.repository.PersonRepository;
import ru.job4j.repository.UrlRepository;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/url")
public class UrlController {
    private final UrlRepository urlRepository;
    private final PersonRepository pr;

    public UrlController(final UrlRepository urlRepository, final PersonRepository pr) {
        this.urlRepository = urlRepository;
        this.pr = pr;
    }

    @GetMapping("/")
    public List<Url> findAll(Principal principal) {
        return pr.findByLogin(principal.getName()).getUrls();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Url> findById(@PathVariable int id) {
        var url = this.urlRepository.findById(id);
        return new ResponseEntity<>(
                url.orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Url is not found. Please, check id.")),
                HttpStatus.OK);
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<String> findByCode(@PathVariable String code) {
        var url = this.urlRepository.findByCode(code);
        Url e;
        if (url.isPresent()) {
            e = url.get();
            e.setStat(e.getStat() + 1);
            this.urlRepository.save(e);
            return new ResponseEntity<>(
                    e.getText(),
                    HttpStatus.MOVED_PERMANENTLY
            );
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/convert")
    public ResponseEntity<Url> convert(@RequestBody Url url, Principal principal) {
        url.setCode(new Generator().randomString());
        Person p = pr.findByLogin(principal.getName());
        p.getUrls().add(url);
        return new ResponseEntity<>(
                this.urlRepository.save(url),
                HttpStatus.OK
        );
    }

    @GetMapping("/statistic")
    public Map<String, Integer> statistic(Principal principal) {
        List<Url> list = pr.findByLogin(principal.getName()).getUrls();
        Map<String, Integer> m = new HashMap<>();
        for (Url e
                :list) {
            m.put(e.getText(), e.getStat());
        }
        return m;
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@Valid @RequestBody Url url) {
        this.urlRepository.save(url);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id, Principal principal) {
        Url url = new Url();
        url.setId(id);
        pr.findByLogin(principal.getName()).getUrls().remove(url);
        this.urlRepository.delete(url);
        return ResponseEntity.ok().build();
    }
}
