package com.kimgun.shortme.shorturl;

import org.springframework.jdbc.core.RowMapper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is used to map the result of the query with ShortUrlObject.
 */
public class ShortUrlObjectMapper implements RowMapper<ShortUrlObject> {

    @Override
    public ShortUrlObject mapRow(ResultSet resultSet, int i) throws SQLException {
        ShortUrlObject shortUrlObject = new ShortUrlObject();
        shortUrlObject.setId(resultSet.getInt("id"));
        shortUrlObject.setShortUrl(resultSet.getString("short_url"));
        shortUrlObject.setRawUrl(getDecodedUrl(resultSet.getString("raw_url")));
        shortUrlObject.setHitCounter(resultSet.getInt("hit_counter"));
        shortUrlObject.setOwner(resultSet.getInt("owner"));
        return shortUrlObject;
    }

    private String getDecodedUrl(String url)  {
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
