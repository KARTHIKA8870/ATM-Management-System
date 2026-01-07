package com.bank.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.bank.db.DBConnection;
import com.bank.model.User;

public class LoginService {

    public static User loginWithPin(String username, String pin) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE username = ? AND pin = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, pin);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setBalance(rs.getDouble("balance"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}