package com.kimgun.shortme.controller;

import com.kimgun.shortme.shorturl.ShortUrlJDBCTemplate;
import com.kimgun.shortme.shorturl.ShortUrlObject;
import com.kimgun.shortme.user.UserJDBCTemplate;
import com.kimgun.shortme.user.UserObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
public class ShortUrlController {

    private static final int RANDOM_LENGTH = 8;

    @Autowired
    private UserJDBCTemplate userTemplate;

    @Autowired
    private ShortUrlJDBCTemplate template;

    @RequestMapping(value = "/shortme", method = RequestMethod.PUT)
    public ShortUrlObject generateShortUrl(@RequestHeader(value = "sessionId") String sessionId,
                                           @RequestHeader(value = "url") String rawUrl) throws UnsupportedEncodingException {
        UserObject userObject = userTemplate.getUserObjectFromSessionId(sessionId);
        ShortUrlObject shortUrlObject = template.getShortUrlObjectFromRawUrl(rawUrl, userObject.getId());
        if (null == shortUrlObject) {
            shortUrlObject = new ShortUrlObject();
            shortUrlObject.setShortUrl(random());
            shortUrlObject.setRawUrl(rawUrl);
            shortUrlObject.setHitCounter(0);
            shortUrlObject.setOwner(userObject.getId());
            template.create(shortUrlObject);
            return template.getShortUrlObjectFromRawUrl(rawUrl, userObject.getId());
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
            template.update(shortUrlObject.getId(), shortUrlObject.getHitCounter() + 1);
            return shortUrlObject.getRawUrl();
        }
    }

    @RequestMapping(value = "/shortme", method = RequestMethod.POST)
    public List<ShortUrlObject> findShortUrlObjects(@RequestHeader(value = "sessionId") String sessionId) throws UnsupportedEncodingException {
        UserObject userObject = userTemplate.getUserObjectFromSessionId(sessionId);
        return template.listShortUrlObjects(userObject.getId());
    }

    private String random() {
        return RandomStringUtils.randomAlphanumeric(RANDOM_LENGTH);
    }
}
