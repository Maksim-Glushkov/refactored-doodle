package com.example.demo.service.impl;

import com.example.demo.domain.UrlModel;
import com.example.demo.repository.UrlRepository;
import com.example.demo.service.interfaces.UrlShortenerService;
import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
@Slf4j
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final UrlRepository urlRepository;

    @Override
    public RedirectView localRedirect(String shortUrl) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(urlRepository.findFirstByShortUrl(shortUrl).getLongUrl());
        return redirectView;
    }

    @Override
    public String createUrl(String longUrl) {
        UrlValidator urlValidator = new UrlValidator(
                new String[]{"http", "https"});
        if (urlValidator.isValid(longUrl)) {
            String shortUrl = Hashing.murmur3_32().hashString(longUrl, StandardCharsets.UTF_8).toString();
            createAndSaveUrlModel(longUrl, shortUrl);
            return "http://localhost:8080/api/" + shortUrl;
        }
        throw new RuntimeException("Invalid URL");
    }

    @Transactional
    @Override
    public String checkTimer(String shortUrl) {
        long current = System.currentTimeMillis();
        int MILLIS_IN_MIN = 600000;
        if (urlRepository.existsByShortUrl(shortUrl)) {
            if (urlRepository.findFirstByShortUrl(shortUrl).getTimer() > current - MILLIS_IN_MIN) {
                return urlRepository.findFirstByShortUrl(shortUrl).getLongUrl();
            } else
                urlRepository.deleteById(urlRepository.findFirstByShortUrl(shortUrl).getId());
            return "your short URL is dead";
        } else return "This short URL has not been created";
    }

    /**
     * Создание сущности Url и сохранение в базе.
     *
     * @param longUrl  длинный url
     * @param shortUrl короткий url
     */
    private void createAndSaveUrlModel(String longUrl, String shortUrl) {
        UrlModel urlModel = new UrlModel();
        urlModel.setShortUrl(shortUrl);
        urlModel.setLongUrl(longUrl);
        urlModel.setTimer(System.currentTimeMillis());
        urlRepository.save(urlModel);
    }
}
