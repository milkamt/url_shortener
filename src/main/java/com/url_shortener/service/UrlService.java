package com.url_shortener.service;

import com.url_shortener.dto.UrlDto;

import java.util.List;

public interface UrlService {
    Boolean aliasAlreadyExists(String alias);
    String getUrl(String alias);
    void saveUrl(UrlDto url);
    void incrementHitCount(String alias);
    List<String> getAllAliases();
}