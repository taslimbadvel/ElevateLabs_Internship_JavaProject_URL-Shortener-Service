package com.example.urlshortener.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "url_mapping", indexes = {
        @Index(columnList = "shortCode"),
        @Index(columnList = "longUrl")
})
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ shortCode can be null initially (we generate it after saving)
    @Column(unique = true)
    private String shortCode;

    @Column(nullable = false, length = 2000)
    private String longUrl;

    @Column(nullable = false)
    private Long clicks = 0L;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public UrlMapping() {}

    public UrlMapping(String longUrl) {
        this.longUrl = longUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getShortCode() { return shortCode; }
    public void setShortCode(String shortCode) { this.shortCode = shortCode; }

    public String getLongUrl() { return longUrl; }
    public void setLongUrl(String longUrl) { this.longUrl = longUrl; }

    public Long getClicks() { return clicks; }
    public void setClicks(Long clicks) { this.clicks = clicks; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
