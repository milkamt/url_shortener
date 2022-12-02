package com.url_shortener.service;

import com.url_shortener.model.Url;
import com.url_shortener.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService{
    private final UrlRepository urlRepository;

    @Override
    public String generateSecretCode() {
        Random random = new Random();
        int number = random.nextInt(9999);
        return String.format("%04d", number);
    }

    @Override
    public Boolean aliasAlreadyExists(String alias) {
        return urlRepository.existsByAlias(alias);
    }

    @Override
    public String getUrl(String alias) {
        var url = urlRepository.findUrlByAlias(alias);

        return url.isPresent() ? url.get().getUrl() : "Alias not found";
    }

    @Override
    public void saveUrl(Url url) {
        urlRepository.save(url);
    }

    @Override
    public String deleteBySecretCode(Long id, String secretCode) {
        var url = urlRepository.findById(id);
        if (url.isEmpty()) {
            return "not found";
        }
        if (!url.get().getSecretCode().equals(secretCode)) {
            return "secret code not found";
        }
        urlRepository.delete(url.get());
        return "deleted";
    }

    @Override
    public void incrementHitCount(String alias) {
        var url = urlRepository.findUrlByAlias(alias);

        url.ifPresent(val -> val.setHitCount(
                val.getHitCount() == null ? 1L : val.getHitCount() + 1L));
        url.ifPresent(urlRepository::save);
    }
}
