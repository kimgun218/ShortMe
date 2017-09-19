package com.kimgun.shortme.user;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserDAO {
    
    /**
     * This is the method to be used to initialize
     * database resources ie. connection.
     */
    public void setDataSource(DataSource ds);

    /**
     * This is the method to be used to create
     * a record in the com.kimgun.shortme.User table.
     */
    public void create(UserObject userObject) throws UnsupportedEncodingException;

    /**
     * This is the method to be used to get a user
     * a record in the com.kimgun.shortme.User table.
     */
    public UserObject getUserObjectAsLogin(String username, String password);

    /**
     * This is the method to be used to get a user from Username
     * a record in the com.kimgun.shortme.User table.
     */
    public UserObject getUserObjectFromUsername(String username);

    /**
     * This is the method to be used to get a user from SessionId
     * a record in the com.kimgun.shortme.User table.
     */
    public UserObject getUserObjectFromSessionId(String sessionId);

    /**
     * This is the method to be used to list down
     * all the records from the com.kimgun.shortme.User table.
     */
    public List<UserObject> listUserObjects();

    /**
     * This is the method to be used to delete a User
     * a record from the com.kimgun.shortme.User table corresponding
     * to a passed UserObject id.
     */
    public void deleteUser(Integer id);

    /**
     * This is the method to be used to remove SessionId in a user
     * a record from the com.kimgun.shortme.User table corresponding
     * to a passed UserObject id.
     */
    public void removeSessionId(Integer id);

    /**
     * This is the method to be used to add SessionId in a user
     * a record into the com.kimgun.shortme.User table.
     */
    public void addSessionId(Integer id, String sessionId);
}
