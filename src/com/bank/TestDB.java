package com.bank;

import java.sql.Connection;
import com.bank.db.DBConnection;

public class TestDB {
    public static void main(String[] args) {
        Connection con = DBConnection.getConnection();
        if (con != null) {
            System.out.println("DB Connected Successfully");
        } else {
            System.out.println("DB Connection Failed");
        }
    }
}
