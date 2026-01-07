package com.bank.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import com.bank.db.DBConnection;
import com.bank.model.User;

public class BankService {

    // DEPOSIT
	public static void deposit(User user, double amount)
	{
	    try {
	        Connection con = DBConnection.getConnection();

	        String sql = "UPDATE users SET balance = balance + ? WHERE id = ?";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setDouble(1, amount);
	        ps.setInt(2, user.getId());
	        ps.executeUpdate();

	        user.setBalance(user.getBalance() + amount);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
    // WITHDRAW
	public static void withdraw(User user, double amount) 
	{
	    try {
	        Connection con = DBConnection.getConnection();
            //Balance update
	        String sql1= "UPDATE users SET balance = balance - ? WHERE id = ?";
	        PreparedStatement ps1 = con.prepareStatement(sql1);
	        ps1.setDouble(1, amount);
	        ps1.setInt(2, user.getId());
	        ps1.executeUpdate();

	        //Transaction history INSERT 
	        String sql2="INSERT INTO transactions(User_id, type, amount, details) VALUES (?, ?, ?, ?)";
	        PreparedStatement ps2=con.prepareStatement(sql2);
	        ps2.setInt(1, user.getId());
	        ps2.setString(2,  "WITHDRAW");;
	        ps2.setDouble(3, amount);
	        ps2.setString(4,  "Cash Withdraw");
	        ps2.executeUpdate();
	        
	        //Object balance update
	        user.setBalance(user.getBalance() - amount);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

    // TRANSFER
	public static boolean transfer(User sender, String accNo, double amount) {
	    try {
	        Connection con = DBConnection.getConnection();

	        // Check receiver
	        String checkSql = "SELECT * FROM users WHERE account_no = ?";
	        PreparedStatement checkPs = con.prepareStatement(checkSql);
	        checkPs.setString(1, accNo);
	        ResultSet rs = checkPs.executeQuery();

	        if (!rs.next()) {
	            return false; // receiver not found
	        }

	        int receiverId = rs.getInt("id");

	        // Deduct sender
	        String debitSql = "UPDATE users SET balance = balance - ? WHERE id = ?";
	        PreparedStatement debitPs = con.prepareStatement(debitSql);
	        debitPs.setDouble(1, amount);
	        debitPs.setInt(2, sender.getId());
	        debitPs.executeUpdate();

	        // Credit receiver
	        String creditSql = "UPDATE users SET balance = balance + ? WHERE id = ?";
	        PreparedStatement creditPs = con.prepareStatement(creditSql);
	        creditPs.setDouble(1, amount);
	        creditPs.setInt(2, receiverId);
	        creditPs.executeUpdate();

	        sender.setBalance(sender.getBalance() - amount);
	        return true;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
}

	
	
	