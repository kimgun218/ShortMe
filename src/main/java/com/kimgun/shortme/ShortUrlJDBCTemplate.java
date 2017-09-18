package com.kimgun.shortme;

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
        String SQL = "INSERT INTO ShortUrl (short_url, raw_url, hit_counter) VALUES (?, ?, ?)";
        jdbcTemplateObject.update( SQL,
                shortUrlObject.getShortUrl(),
                shortUrlObject.getEncodedRawUrl(),
                shortUrlObject.getHitCounter());
        System.out.println("Created Record RawUrl : " + shortUrlObject.getRawUrl());
        return;
    }

    public ShortUrlObject getShortUrlObjectFromId(Integer id) {
        return getShortUrlObject("id", id);
    }

    public ShortUrlObject getShortUrlObjectFromShortUrl(String shortUrl) {
        return getShortUrlObject("short_url", shortUrl);
    }

    public ShortUrlObject getShortUrlObjectFromRawUrl(String rawUrl) throws UnsupportedEncodingException {
        return getShortUrlObject("raw_url", URLEncoder.encode(rawUrl, "UTF-8"));
    }

    public ShortUrlObject getShortUrlObject(String key, Object value) {
        try {
            String SQL = "SELECT * FROM ShortUrl where " + key + " = ?";
            return jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{value}, new ShortUrlObjectMapper());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ShortUrlObject> listShortUrlObjects() {
        try {
            String SQL = "SELECT * FROM ShortUrl";
            List <ShortUrlObject> shortUrlObjects = jdbcTemplateObject.query(SQL, new ShortUrlObjectMapper());
            return shortUrlObjects;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM ShortUrl where id = ?";
        jdbcTemplateObject.update(SQL, id);
        System.out.println("Deleted Record with ID = " + id );
        return;
    }

    @Override
    public void update(Integer id, Integer hitCounter) {
        String SQL = "UPDATE ShortUrl SET hit_counter = ? where id = ?";
        jdbcTemplateObject.update(SQL, hitCounter, id);
        System.out.println("Updated Record with ID = " + id );
        return;
    }
}
