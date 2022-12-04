package com.url_shortener.service;

import com.url_shortener.dto.UrlDto;
import com.url_shortener.model.Url;
import com.url_shortener.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService{
    private final UrlRepository urlRepository;

    private String generateSecretCode() {
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

        return url.map(Url::getUrl).orElse(null);
    }

    @Override
    public void saveUrl(UrlDto url) {
        urlRepository.save(
                new Url(
                        url.getUrl(),
                        url.getAlias(),
                        generateSecretCode()));
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

    @Override
    public List<String> getAllAliases() {
        List<Url> urls = urlRepository.findAll();
        return urls.stream()
                .map(Url::getAlias)
                .collect(Collectors.toList());
    }
}
