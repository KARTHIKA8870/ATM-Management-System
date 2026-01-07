package com.bank.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

import com.bank.db.DBConnection;
import com.bank.model.User;

public class TransactionHistoryFrame extends JFrame {

    public TransactionHistoryFrame(User user) {

        setTitle("Transaction History");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
            "ID", "Type", "Amount", "Details", "Date"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setRowHeight(25);;
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setGridColor(Color.LIGHT_GRAY);
        table.setSelectionBackground(new Color(200, 230, 255));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        try {
            Connection con = DBConnection.getConnection();
            String sql =
                "SELECT id, type, amount, details, txn_date " +
                "FROM transactions WHERE user_id = ? " +
                "ORDER BY txn_date DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("type"),
                    rs.getDouble("amount"),
                    rs.getString("details"),
                    rs.getTimestamp("txn_date")
                });
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading history");
            e.printStackTrace();
        }

        add(scrollPane);
        setVisible(true);
    }
}