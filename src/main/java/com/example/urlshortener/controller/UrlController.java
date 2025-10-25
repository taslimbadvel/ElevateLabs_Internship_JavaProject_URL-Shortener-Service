package com.example.urlshortener.controller;

import com.example.urlshortener.model.UrlMapping;
import com.example.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.hibernate.validator.constraints.URL;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    // Input record with validation
    public static record ShortenRequest(
            @NotBlank(message = "URL cannot be blank")
            @URL(message = "Invalid URL format")
            String longUrl
    ) {}

    // Response record for short URL
    public static record ShortenResponse(String shortUrl) {}

    // Response record for stats
    public static record StatsResponse(String longUrl, String shortUrl, Long clicks, java.time.Instant createdAt) {}

    //  Shorten a URL
    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shorten(@Valid @RequestBody ShortenRequest req) {
        String shortUrl = urlService.shortenUrl(req.longUrl());
        return ResponseEntity.ok(new ShortenResponse(shortUrl));
    }

    // \Redirect short URL to long URL
    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable("code") String code) {
        return urlService.getByShortCode(code)
                .map(mapping -> {
                    urlService.incrementClicks(mapping);
                    return ResponseEntity.status(302)
                            .location(URI.create(mapping.getLongUrl().trim()))
                            .build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    //  Get stats for a short URL
    @GetMapping("/{code}/stats")
    public ResponseEntity<StatsResponse> stats(@PathVariable("code") String code) {
        return urlService.getByShortCode(code)
                .map(m -> ResponseEntity.ok(new StatsResponse(
                        m.getLongUrl(),
                        String.format("%s/%s", "http://localhost:8080", code),
                        m.getClicks(),
                        m.getCreatedAt()
                )))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // List all URLs (for dev/testing)
    @GetMapping("/links")
    public ResponseEntity<List<UrlMapping>> listAll() {
        return ResponseEntity.ok(urlService.getAllMappings());
    }
}
