package com.url_shortener.repository;

import com.url_shortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Boolean existsByAlias(String alias);
    Optional<Url> findUrlByAlias(String alias);
}
