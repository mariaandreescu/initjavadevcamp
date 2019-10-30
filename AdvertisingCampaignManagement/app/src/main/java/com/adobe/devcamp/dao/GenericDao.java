/*
 ************************************************************************
  * ADOBE CONFIDENTIAL
  * ___________________
  *
  * Copyright 2018 Adobe Systems Incorporated
  * All Rights Reserved.
  *
  * NOTICE:  All information contained herein is, and remains
  * the property of Adobe Systems Incorporated and its suppliers,
  * if any.  The intellectual and technical concepts contained
  * herein are proprietary to Adobe Systems Incorporated and its
  * suppliers and are protected by all applicable intellectual property laws,
  * including trade secret and copyright laws.
  * Dissemination of this information or reproduction of this material
  * is strictly forbidden unless prior written permission is obtained
  * from Adobe Systems Incorporated.
  ***********************************************************************
 */

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

public final class GenericDao<T> {
    private final Logger logger = LoggerFactory.getLogger(GenericDao.class);

    private final Connection connection;
    private static final Map<Class, String> TABLES = new HashMap<>();

    static{
        TABLES.put(User.class, "users");
        TABLES.put(Advertiser.class, "advertisers");
        TABLES.put(Publisher.class, "publishers");
        TABLES.put(Campaign.class, "campaigns");
    }

    public GenericDao(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    public Map<Integer, String> selectAll(Class<T> clazz) {
        final Map<Integer, String> all = new HashMap<>();
        final String query = "SELECT * FROM " + TABLES.get(clazz);

        try (final Statement stmt = connection.createStatement()){
            final ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                all.put(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            logger.error("Query {} failed because {}", query, e.getMessage());
        }
        return all;
    }

    public String selectById(Class<T> clazz, int id) {
        final String query = "SELECT json FROM " + TABLES.get(clazz) + " WHERE id=" + id;
        try (final Statement stmt = connection.createStatement()) {
            final ResultSet rs = stmt.executeQuery(query);
            if(rs.next()) {
                return rs.getString(1);
            }
        } catch(SQLException e) {
            logger.error("Query {} failed because {}", query, e.getMessage());
        }

        return null;
    }
}
