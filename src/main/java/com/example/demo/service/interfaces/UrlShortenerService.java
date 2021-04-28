package com.example.demo.service.interfaces;

import org.springframework.web.servlet.view.RedirectView;

public interface UrlShortenerService {
    /**
     * @param shortUrl короткая ссылка по которой идет поиск в базе
     * @return "длинную" строку, из котороый была
     * создана короткая, при этом проверяет "жива" ли ссылка.
     */
    public String checkTimer(String shortUrl);

    /**
     * @param shortUrl короткая ссылка по которой идет поиск в базе
     * @return объект класса RedirectView, который переводит
     * на "длинную" ссылку.
     */
    public RedirectView localRedirect(String shortUrl);

    /**
     * @param longUrl строка из которой создается короткая ссылка
     *                должна начинаться с http/https
     * @return "укороченную" ссылку
     */
    String createUrl(String longUrl);
}
