package com.kimgun.shortme;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
public class ShortMeController {

    private static final int RANDOM_LENGTH = 8;

    @Autowired
    private ShortUrlJDBCTemplate template;

    @RequestMapping(value = "/shortme", method = RequestMethod.POST)
    public ShortUrlObject generateShortUrl(@RequestHeader(value = "url") String rawUrl) throws UnsupportedEncodingException {
        ShortUrlObject shortUrlObject = template.getShortUrlObjectFromRawUrl(rawUrl);
        if (null == shortUrlObject) {
            shortUrlObject = new ShortUrlObject();
            shortUrlObject.setShortUrl(random());
            shortUrlObject.setRawUrl(rawUrl);
            shortUrlObject.setHitCounter(0);
            template.create(shortUrlObject);
            return shortUrlObject;
        } else {
            return shortUrlObject;
        }
    }

    @RequestMapping(value = "/shortme/{shortUrl}", method = RequestMethod.GET)
    public String getRawUrl(@PathVariable(value = "shortUrl") String shortUrl) throws UnsupportedEncodingException {
        ShortUrlObject shortUrlObject = template.getShortUrlObjectFromShortUrl(shortUrl);
        if (null == shortUrlObject) {
            return "NOT FOUND";
        } else {
            template.update(shortUrlObject.getId(), shortUrlObject.getHitCounter()+1);
            return shortUrlObject.getRawUrl();
        }
    }

    private String random() {
        return RandomStringUtils.randomAlphanumeric(RANDOM_LENGTH);
    }
}
