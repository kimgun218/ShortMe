package com.kimgun.shortme.user;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is used to map the result of the query with UserObject.
 */
public class UserObjectMapper implements RowMapper<UserObject> {
    @Override
    public UserObject mapRow(ResultSet resultSet, int i) throws SQLException {
        UserObject userObject = new UserObject();
        userObject.setId(resultSet.getInt("id"));
        userObject.setUsername(resultSet.getString("username"));
        userObject.setPassword(decrypeBase64(resultSet.getString("password")));
        userObject.setSessionId(resultSet.getString("sessionId"));
        return userObject;
    }

    private String decrypeBase64(String value)  {
        return value;
    }
}
