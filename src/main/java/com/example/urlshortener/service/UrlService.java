package com.example.urlshortener.service;

import com.example.urlshortener.model.UrlMapping;
import com.example.urlshortener.repository.UrlMappingRepository;
import com.example.urlshortener.util.Base62;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
public class UrlService {

    private final UrlMappingRepository repo;

    @Value("${app.domain:http://localhost:8080}")
    private String appDomain;

    public UrlService(UrlMappingRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public String shortenUrl(String longUrl) {
        Optional<UrlMapping> existing = repo.findByLongUrl(longUrl);
        if (existing.isPresent()) {
            return buildShortUrl(existing.get().getShortCode());
        }

        // Step 1: Create mapping object
        UrlMapping mapping = new UrlMapping();
        mapping.setLongUrl(longUrl);
        mapping.setClicks(0L);

        // Step 2: Temporarily save to get the generated ID
        mapping = repo.saveAndFlush(mapping);

        // Step 3: Generate Base62 code from ID
        String code = Base62.encode(mapping.getId());
        mapping.setShortCode(code);

        // Step 4: Update record with short code
        repo.save(mapping);

        return buildShortUrl(code);
    }

    public Optional<UrlMapping> getByShortCode(String code) {
        return repo.findByShortCode(code); //
    }

    @Transactional
    public void incrementClicks(UrlMapping mapping) {
        mapping.setClicks(mapping.getClicks() + 1);
        repo.save(mapping);
    }

    private String buildShortUrl(String code) {
        return String.format("%s/%s", appDomain.replaceAll("/$", ""), code);
    }

    public List<UrlMapping> getAllMappings() {
        return repo.findAll();
    }
}
