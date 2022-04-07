package com.example.demo.controller;

import com.example.demo.dto.LongUrlRequestDto;
import com.example.demo.service.interfaces.UrlShortenerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import static com.example.demo.constants.ApiPaths.MAIN_API;


@RestController
@RequestMapping(MAIN_API)
@AllArgsConstructor
public class MainController {
    private final UrlShortenerService shortenerService;

    @GetMapping("/getLong")
    public ResponseEntity<String> getUrl(@RequestBody String shortUrl) {
        String resultUrl = shortenerService.checkTimer(shortUrl);
        return ResponseEntity.ok(resultUrl);
    }

    @GetMapping("/{shortUrl}")
    public RedirectView localRedirect(@PathVariable String shortUrl) {
        return shortenerService.localRedirect(shortUrl);
    }


    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody LongUrlRequestDto longUrl) {
        String createdShortUrl =
                shortenerService.createUrl(longUrl);
        return ResponseEntity.ok(createdShortUrl);
    }

    @GetMapping("/debug")
    public ResponseEntity<String> debug(){
        return ResponseEntity.ok("working");
    }
}

