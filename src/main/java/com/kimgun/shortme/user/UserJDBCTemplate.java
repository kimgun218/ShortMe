package com.kimgun.shortme.user;

import com.kimgun.shortme.UnauthorizedException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class UserJDBCTemplate implements UserDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(UserObject userObject) throws UnsupportedEncodingException {
        String SQL = "INSERT INTO User (username, password) VALUES (?, ?)";
        jdbcTemplateObject.update( SQL,
                userObject.getUsername(),
                userObject.getEncodedPassword());
        System.out.println("Created Record Username : " + userObject.getUsername());
        return;
    }

    @Override
    public UserObject getUserObjectAsLogin(String username, String password) {
        try {
            String SQL = "SELECT * FROM User WHERE username = ? AND password = ?";
            return jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{username, password}, new UserObjectMapper());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserObject getUserObjectFromUsername(String username) {
        try {
            String SQL = "SELECT * FROM User WHERE username = ?";
            return jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{username}, new UserObjectMapper());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserObject getUserObjectFromSessionId(String sessionId) {
        try {
            String SQL = "SELECT * FROM User WHERE sessionId = ?";
            return jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{sessionId}, new UserObjectMapper());
        } catch (Exception e) {
            throw new UnauthorizedException();
        }
    }

    @Override
    public List<UserObject> listUserObjects() {
        try {
            String SQL = "SELECT * FROM User";
            List <UserObject> userObjects = jdbcTemplateObject.query(SQL, new UserObjectMapper());
            return userObjects;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteUser(Integer id) {
        String SQL = "DELETE FROM ShortUrl where id = ?";
        jdbcTemplateObject.update(SQL, id);
        System.out.println("Deleted Record with ID = " + id );
        return;
    }

    @Override
    public void removeSessionId(Integer id) {
        String SQL = "UPDATE User SET sessionId = \"\" where id = ?";
        jdbcTemplateObject.update(SQL, id);
        System.out.println("Updated Record with ID = " + id );
        return;
    }

    @Override
    public void addSessionId(Integer id, String sessionId) {
        String SQL = "UPDATE User SET sessionId = ? where id = ?";
        jdbcTemplateObject.update(SQL, sessionId, id);
        System.out.println("Updated Record with ID = " + id );
        return;
    }
}
