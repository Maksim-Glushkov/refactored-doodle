package com.example.demo.controller;

import com.example.demo.service.impl.UrlShortenerServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MainController {
    private final UrlShortenerServiceImpl urlServiceImpl;


    @GetMapping("/getlong/{shortUrl}")
    public ResponseEntity<String> getUrl(@PathVariable String shortUrl) {
        String resultUrl = urlServiceImpl.checkTimer(shortUrl);
        return ResponseEntity.ok(resultUrl);
    }

    @RequestMapping("/{shortUrl}")
    public RedirectView localRedirect(@PathVariable String shortUrl) {
        return urlServiceImpl.localRedirect(shortUrl);
    }


    @PostMapping()
    public ResponseEntity<String> create(@RequestBody String longUrl) {
        String createdShortUrl = urlServiceImpl.createUrl(longUrl);
        return ResponseEntity.ok(createdShortUrl);
    }
}

