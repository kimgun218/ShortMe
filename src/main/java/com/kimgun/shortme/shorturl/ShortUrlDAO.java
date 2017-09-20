package com.kimgun.shortme.shorturl;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ShortUrlDAO {
    
    /**
     * This is the method to be used to initialize
     * database resources ie. connection.
     */
    public void setDataSource(DataSource ds);

    /**
     * This is the method to be used to create
     * a record in the com.kimgun.shortme.ShortUrl table.
     */
    public void create(ShortUrlObject shortUrlObject) throws UnsupportedEncodingException;

    /**
     * This is the method to be used to list down
     * a record from the com.kimgun.shortme.ShortUrl table corresponding
     * to a passed ShortUrlObject id.
     * @param key is column in the table.
     * @param value is the value in the column.
     */
    public ShortUrlObject getShortUrlObject(String key, Object value);

    /**
     * This is the method to be used to list down
     * all the records from the com.kimgun.shortme.ShortUrl table.
     * @param owner is user Id from the com.kimgun.shortme.User table.
     */
    public List<ShortUrlObject> listShortUrlObjects(Integer owner);

    /**
     * This is the method to be used to delete
     * a record from the com.kimgun.shortme.ShortUrl table corresponding
     * to a passed ShortUrlObject id.
     */
    public void delete(Integer id);

    /**
     * This is the method to be used to update
     * a record into the com.kimgun.shortme.ShortUrl table.
     */
    public void update(Integer id, Integer hitCounter);
}
