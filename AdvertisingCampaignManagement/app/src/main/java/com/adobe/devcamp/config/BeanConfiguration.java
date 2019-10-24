package com.adobe.devcamp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.adobe.devcamp.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.adobe.devcamp.service.UserService;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class BeanConfiguration {

    @Bean
    public DataSource dataSource() {
        final MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/javadevcamp");
        dataSource.setUser("root");
        dataSource.setPassword("devcamp");
        return dataSource;
    }
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
    @Bean
    public UserDao userDao(DataSource dataSource) throws SQLException {
        return new UserDao(dataSource);
    }
    @Bean
    public UserService userService(UserDao userDao, ObjectMapper objectMapper) {
        return new UserService(userDao, objectMapper);
    }
}
