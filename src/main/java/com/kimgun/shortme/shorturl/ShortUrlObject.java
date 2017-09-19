package com.kimgun.shortme.shorturl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ShortUrlObject {
    private int id;
    private String shortUrl;
    private String rawUrl;
    private int hitCounter;
    private int owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Object getEncodedRawUrl() throws UnsupportedEncodingException {
        return URLEncoder.encode(rawUrl, "UTF-8");
    }

    public String getRawUrl() {
        return rawUrl;
    }

    public void setRawUrl(String rawUrl) {
        this.rawUrl = rawUrl;
    }

    public int getHitCounter() {
        return hitCounter;
    }

    public void setHitCounter(int hitCounter) {
        this.hitCounter = hitCounter;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
