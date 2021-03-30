package com.example.labb.demo.repostiories;

import com.example.labb.demo.models.Subscriber;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriberRowMapper implements RowMapper{
    @Override
    public Subscriber mapRow(ResultSet resultSet, int i)throws SQLException{
        Subscriber subscriber = new Subscriber();
        subscriber.setId(resultSet.getInt("Id"));
        subscriber.setEmail(resultSet.getString("Email"));
        subscriber.setName(resultSet.getString("Name"));
        subscriber.setCompany(resultSet.getString("Company"));
        return subscriber;
    }


}
