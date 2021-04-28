package com.example.demo.repository;

import com.example.demo.domain.UrlModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<UrlModel, Long> {

    UrlModel findFirstByShortUrl(String shortUrl);

    void deleteById(int id);

    boolean existsByShortUrl(String shortUrl);

}
