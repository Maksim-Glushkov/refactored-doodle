package com.example.demo.service.impl;

import com.example.demo.domain.UrlModel;
import com.example.demo.dto.LongUrlRequestDto;
import com.example.demo.repository.UrlRepository;
import com.example.demo.service.interfaces.UrlShortenerService;
import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@AllArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private final UrlRepository urlRepository;
    private static final int MILLIS_IN_MIN = 600000;

    @Override
    public RedirectView localRedirect(String shortUrl) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(getLongUrlbyShort(shortUrl));
        return redirectView;
    }

    @Override
    public String createUrl(LongUrlRequestDto urlDto) {

        String longUrl = urlDto.getUrl();
        try {

            URI uri = new URI(longUrl);

            String shortUrl = Hashing.murmur3_32().hashString(uri.toASCIIString(), StandardCharsets.UTF_8).toString();

            createAndSaveUrlModel(longUrl, shortUrl);

            return shortUrl;
        } catch (URISyntaxException | NullPointerException e) {
            throw new RuntimeException("Invalid URL");
        }
    }

    @Transactional
    @Override
    public String checkTimer(String shortUrl) {

        long current = System.currentTimeMillis();


        if (urlRepository.existsByShortUrl(shortUrl)) {
            if (urlRepository.findFirstByShortUrl(shortUrl).getTimer() > current - MILLIS_IN_MIN) {
                return urlRepository.findFirstByShortUrl(shortUrl).getLongUrl();
            } else
                urlRepository.deleteById(urlRepository.findFirstByShortUrl(shortUrl).getId());
            return "your short URL is dead";
        } else return "This short URL has not been created";
    }

    private String getLongUrlbyShort(String shortUrl) {
        return urlRepository.findFirstByShortUrl(shortUrl).getLongUrl();
    }

    /**
     * Создание сущности Url и сохранение в базе.
     *
     * @param longUrl  длинный url
     * @param shortUrl короткий url
     */
    private void createAndSaveUrlModel(String longUrl, String shortUrl) {
        UrlModel urlModel = UrlModel.builder()
                .shortUrl(shortUrl)
                .longUrl(longUrl)
                .timer(System.currentTimeMillis())
                .build();
        urlRepository.save(urlModel);
    }
}
