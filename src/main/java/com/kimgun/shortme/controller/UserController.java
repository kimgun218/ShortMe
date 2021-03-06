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

    /**
     * This method is used to add a new user.
     * <p>
     * If the username is duplicated, the service returns fail.
     * Otherwise, the user is added in the com.kimgun.shortme.User table.
     *
     * @param username is the username.
     * @param password is the password.
     * @return String of the result.
     * @throws UnsupportedEncodingException in case of unable to encode URL, it will be thrown.
     */
    //TODO encrypt the password before store in the table.
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

    /**
     * This method is used to login.
     *
     * If the user is authenticated, a sessionId is returned.
     * Otherwise it will return the error message.
     *
     * @param username is the username.
     * @param password is the password.
     * @return String of the sessionId.
     */
    @RequestMapping(value = "/session", method = RequestMethod.POST)
    public String login(@RequestHeader(value = "username") String username,
                        @RequestHeader(value = "password") String password) {
        UserObject userObject = template.getUserObjectAsLogin(username, password);
        if (null == userObject) {
            return "Invalid username or password.";
        } else {
            String sessionId = random();
            template.addSessionId(userObject.getId(), sessionId);
            return sessionId;
        }
    }

    /**
     * This method is used to logout.
     *
     * If logout is success, it will return success message.
     * Otherwise it will return an error with 401 HTTP status.
     *
     * @param sessionId is the session Id generated by Login service.
     * @return String of the result.
     */
    @RequestMapping(value = "/session", method = RequestMethod.DELETE)
    public String logout(@RequestHeader(value = "sessionId") String sessionId) {
        UserObject userObject = template.getUserObjectFromSessionId(sessionId);
        template.removeSessionId(userObject.getId());
        return "Logout completed.";
    }

    /**
     * This method is used to return the login status.
     *
     * If the user has been logged in, it returns active message.
     * Otherwise it will return not found message.
     *
     * @param sessionId is the session Id generated by Login service.
     * @return String of the result.
     */
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public String loginStatus(@RequestHeader(value = "sessionId") String sessionId) {
        try {
            return "The Session Id is active.";
        } catch (RuntimeException e) {
            return "Session Id " + sessionId + " not found.";
        }
    }

    /**
     * This method is used to random sessionId in RANDOM_LENGTH characters.
     *
     * @return String of the random result.
     */
    private String random() {
        return RandomStringUtils.randomAlphanumeric(RANDOM_LENGTH);
    }
}
