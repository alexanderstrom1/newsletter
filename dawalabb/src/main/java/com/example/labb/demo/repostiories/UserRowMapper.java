package com.example.labb.demo.repostiories;

//import javax.swing.tree.RowMapper;


import com.example.labb.demo.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper {
    @Override
    public User mapRow(ResultSet resultSet, int i)throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("Id"));
        user.setName(resultSet.getString("Name"));
        user.setEmail(resultSet.getString("Email"));
        return user;
    }//End overide

}//End class
