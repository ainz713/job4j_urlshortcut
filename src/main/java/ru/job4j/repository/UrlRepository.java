package ru.job4j.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.domain.Url;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UrlRepository extends CrudRepository<Url, Integer> {
    Optional<Url> findByCode(String code);
    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update Url u set u.stat = u.stat + 1 where u = ?1")
    int increaseStat(Url url);
}
