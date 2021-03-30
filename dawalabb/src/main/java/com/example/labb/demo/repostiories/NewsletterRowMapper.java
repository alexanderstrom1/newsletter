package com.example.labb.demo.repostiories;

import com.example.labb.demo.models.Newsletters;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewsletterRowMapper implements RowMapper<Newsletters> {

    @Override
    public Newsletters mapRow(ResultSet resultSet, int i) throws SQLException {
        Newsletters newsletters = new Newsletters();
        newsletters.setId(resultSet.getInt("id"));
        newsletters.setCompany(resultSet.getString("company"));
        newsletters.setText(resultSet.getString("text"));
        newsletters.setAdminId(resultSet.getInt("adminId"));
        return newsletters;
    }

}
