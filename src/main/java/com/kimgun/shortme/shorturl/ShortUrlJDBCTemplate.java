package com.kimgun.shortme.shorturl;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class ShortUrlJDBCTemplate implements ShortUrlDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(ShortUrlObject shortUrlObject) throws UnsupportedEncodingException {
        String SQL = "INSERT INTO ShortUrl (short_url, raw_url, hit_counter, owner) VALUES (?, ?, ?, ?)";
        jdbcTemplateObject.update(SQL,
                shortUrlObject.getShortUrl(),
                shortUrlObject.getEncodedRawUrl(),
                shortUrlObject.getHitCounter(),
                shortUrlObject.getOwner());
        System.out.println("Created Record RawUrl : " + shortUrlObject.getRawUrl());
        return;
    }

    public ShortUrlObject getShortUrlObjectFromShortUrl(String shortUrl) {
        return getShortUrlObject("short_url", shortUrl);
    }

    public ShortUrlObject getShortUrlObjectFromRawUrl(String rawUrl, Integer owner) throws UnsupportedEncodingException {
        String encodedRawUrl = URLEncoder.encode(rawUrl, "UTF-8");
        try {
            String SQL = "SELECT * FROM ShortUrl WHERE raw_url = ? AND owner = ?";
            ShortUrlObject shortUrlObjects = jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{encodedRawUrl, owner}, new ShortUrlObjectMapper());
            return shortUrlObjects;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ShortUrlObject getShortUrlObject(String key, Object value) {
        try {
            String SQL = "SELECT * FROM ShortUrl WHERE " + key + " = ?";
            return jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{value}, new ShortUrlObjectMapper());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ShortUrlObject> listShortUrlObjects(Integer owner) {
        try {
            String SQL = "SELECT * FROM ShortUrl WHERE owner = ?";
            List<ShortUrlObject> shortUrlObjects = jdbcTemplateObject.query(SQL,
                    new Object[]{owner}, new ShortUrlObjectMapper());
            return shortUrlObjects;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM ShortUrl where id = ?";
        jdbcTemplateObject.update(SQL, id);
        System.out.println("Deleted Record with ID = " + id);
        return;
    }

    @Override
    public void update(Integer id, Integer hitCounter) {
        String SQL = "UPDATE ShortUrl SET hit_counter = ? where id = ?";
        jdbcTemplateObject.update(SQL, hitCounter, id);
        System.out.println("Updated Record with ID = " + id);
        return;
    }
}
