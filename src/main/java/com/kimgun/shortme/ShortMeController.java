package com.kimgun.shortme;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ShortMeController {

    private static final int RANDOM_LENGTH = 8;

    private List<ShortUrlObject> shortUrlObjectList = new ArrayList<ShortUrlObject>();

    @RequestMapping(value = "/shortme", method = RequestMethod.POST)
    public ShortUrlObject generateShortUrl(@RequestHeader(value = "url") String rawUrl) {
        ShortUrlObject shortUrlObject = getShortUrlObjectFromRawUrl(rawUrl);
        if (null == shortUrlObject) {
            shortUrlObject = new ShortUrlObject();
            shortUrlObject.setShortUrl(random());
            shortUrlObject.setRawUrl(rawUrl);
            shortUrlObject.setHitCounter(0);
            shortUrlObjectList.add(shortUrlObject);
            return shortUrlObject;
        } else {
            return shortUrlObject;
        }
    }

    private ShortUrlObject getShortUrlObjectFromRawUrl(String rawUrl) {
        for (ShortUrlObject object : shortUrlObjectList) {
            if (rawUrl.equals(object.getRawUrl())) {
                return object;
            }
        }
        return null;
    }

    @RequestMapping(value = "/shortme/{shortUrl}", method = RequestMethod.GET)
    public String getRawUrl(@PathVariable(value = "shortUrl") String shortUrl) {
        for (ShortUrlObject object : shortUrlObjectList) {
            if (shortUrl.equals(object.getShortUrl())) {
                object.setHitCounter(object.getHitCounter()+1);
                return object.getRawUrl();
            }
        }
        return "NOT FOUND";
    }


    private String random() {
        return RandomStringUtils.randomAlphanumeric(RANDOM_LENGTH);
    }
}
