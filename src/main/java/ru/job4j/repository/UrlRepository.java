package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.domain.Url;

import java.util.Optional;

public interface UrlRepository extends CrudRepository<Url, Integer> {
    Optional<Url> findByCode(String code);
}
