package com.bank.ui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.bank.db.DBConnection;
import com.bank.model.User;

public class DepositFrame extends JFrame {

    private JTextField amountField;
    private JButton depositBtn, cancelBtn;
    private User user;

    public DepositFrame(User user) {
        this.user = user;

        setTitle("Deposit Amount");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Deposit", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(0, 102, 204));
        add(title, BorderLayout.NORTH);

        // Center panel
        JPanel center = new JPanel(new GridLayout(2, 2, 10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        center.add(new JLabel("Enter Amount:"));
        amountField = new JTextField();
        center.add(amountField);

        add(center, BorderLayout.CENTER);

        // Buttons
        JPanel bottom = new JPanel();
        depositBtn = new JButton("Deposit");
        cancelBtn = new JButton("Cancel");

        depositBtn.setBackground(new Color(40, 167, 69)); // green
        depositBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setForeground(Color.WHITE);

        bottom.add(depositBtn);
        bottom.add(cancelBtn);

        add(bottom, BorderLayout.SOUTH);

        // Actions
        depositBtn.addActionListener(e -> deposit());
        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void deposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Enter valid amount");
                return;
            }

            Connection con = DBConnection.getConnection();

            // 1. Update balance
            String updateSql =
                    "UPDATE users SET balance = balance + ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(updateSql);
            ps.setDouble(1, amount);
            ps.setInt(2, user.getId());
            ps.executeUpdate();

            // 2. Insert transaction history
            String txnSql =
                    "INSERT INTO transactions (user_id, type, amount, details) VALUES (?, ?, ?, ?)";
            PreparedStatement ps2 = con.prepareStatement(txnSql);
            ps2.setInt(1, user.getId());
            ps2.setString(2, "DEPOSIT");
            ps2.setDouble(3, amount);
            ps2.setString(4, "Cash Deposit");
            ps2.executeUpdate();

            // 3. Update user object
            user.setBalance(user.getBalance() + amount);

            JOptionPane.showMessageDialog(this,
                    "Deposit Successful!\nNew Balance: Rs. " + user.getBalance());

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter numbers only");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while depositing");
        }
    }
}