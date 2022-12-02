package com.url_shortener.service;

import com.url_shortener.model.Url;

public interface UrlService {
    String generateSecretCode();
    Boolean aliasAlreadyExists(String alias);
    String getUrl(String alias);
    void saveUrl(Url url);
    String deleteBySecretCode(Long id, String secretCode);
    void incrementHitCount(String alias);
}