package com.kimgun.shortme.controller;

import com.kimgun.shortme.user.UserJDBCTemplate;
import com.kimgun.shortme.user.UserObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class UserController {

    private static final int RANDOM_LENGTH = 50;

    @Autowired
    private UserJDBCTemplate template;

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String addNewUser(@RequestHeader(value = "username") String username,
                             @RequestHeader(value = "password") String password) throws UnsupportedEncodingException {
        UserObject existingUser = template.getUserObjectFromUsername(username);
        if (null == existingUser) {
            UserObject userObject = new UserObject();
            userObject.setUsername(username);
            userObject.setPassword(password);
            template.create(userObject);
            return "Successful, " + username + " is created.";
        } else {
            return "Fail, " + username + " is duplicated.";
        }
    }

    @RequestMapping(value = "/session", method = RequestMethod.POST)
    public String login(@RequestHeader(value = "username") String username,
                        @RequestHeader(value = "password") String password) throws UnsupportedEncodingException {
        UserObject userObject = template.getUserObjectAsLogin(username, password);
        if (null == userObject) {
            return "Invalid username or password.";
        } else {
            String sessionId = random();
            template.addSessionId(userObject.getId(), sessionId);
            return sessionId;
        }
    }

    @RequestMapping(value = "/session", method = RequestMethod.DELETE)
    public String logout(@RequestHeader(value = "sessionId") String sessionId) throws UnsupportedEncodingException {
        UserObject userObject = template.getUserObjectFromSessionId(sessionId);
        if (null == userObject) {
            return "Session Id " + sessionId + " not found.";
        } else {
            template.removeSessionId(userObject.getId());
            return "Logout completed.";
        }
    }

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public String loginStatus(@RequestHeader(value = "sessionId") String sessionId) throws UnsupportedEncodingException {
        UserObject userObject = template.getUserObjectFromSessionId(sessionId);
        if (null == userObject) {
            return "Session Id " + sessionId + " not found.";
        } else {
            return "The Session Id is active.";
        }
    }

    private String random() {
        return RandomStringUtils.randomAlphanumeric(RANDOM_LENGTH);
    }
}
