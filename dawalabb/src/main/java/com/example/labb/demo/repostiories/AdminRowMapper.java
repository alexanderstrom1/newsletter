package com.example.labb.demo.repostiories;

import com.example.labb.demo.models.Admin;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRowMapper implements RowMapper {
    @Override
    public Admin mapRow(ResultSet resultSet, int i) throws SQLException {
        Admin admin = new Admin();
        admin.setId(resultSet.getInt("id"));
        admin.setName(resultSet.getString("name"));
        admin.setEmail(resultSet.getString("email"));
        return admin;
    }//End overide
}
