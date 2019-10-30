package com.adobe.devcamp.dao;

import com.adobe.devcamp.model.Advertiser;
import com.adobe.devcamp.model.Campaign;
import com.adobe.devcamp.model.Publisher;
import com.adobe.devcamp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public final class UserDao {
    private final Logger logger = LoggerFactory.getLogger(UserDao.class);
    private final Connection connection;
    private static final Map<Class, String> TABLES = new HashMap<>();

    static{
        TABLES.put(User.class, "users");
        TABLES.put(Advertiser.class, "advertisers");
        TABLES.put(Publisher.class, "publishers");
        TABLES.put(Campaign.class, "campaigns");
    }

    public UserDao(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    public Map<Integer, String> selectUsers(){
        Map<Integer, String> users = new HashMap<>();
        final String query = "SELECT * FROM " + TABLES.get(User.class);
        try (final Statement stmt = connection.createStatement()) {
            final ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                users.put(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            //logger
            logger.error("Query {} failed because {}", query,e.getMessage());
        } ;
        return users;
    }

}
