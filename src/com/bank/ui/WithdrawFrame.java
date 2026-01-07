package com.bank.ui;

import javax.swing.*;
import java.awt.*;

import com.bank.model.User;
import com.bank.service.BankService;

public class WithdrawFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    JTextField amountField;
    JButton withdrawBtn, cancelBtn;
    User user;

    public WithdrawFrame(User user) {
        this.user = user;

        setTitle("Withdraw Amount");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Withdraw Money", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(204, 102, 0));
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

        withdrawBtn = new JButton("Withdraw");
        cancelBtn   = new JButton("Cancel");

        withdrawBtn.setBackground(new Color(255, 153, 51));
        withdrawBtn.setForeground(Color.WHITE);

        cancelBtn.setBackground(Color.GRAY);
        cancelBtn.setForeground(Color.WHITE);

        bottom.add(withdrawBtn);
        bottom.add(cancelBtn);

        add(bottom, BorderLayout.SOUTH);

        // Actions
        withdrawBtn.addActionListener(e -> withdraw());
        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void withdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Enter valid amount");
                return;
            }

            if (amount > user.getBalance()) {
                JOptionPane.showMessageDialog(this,
                        "Insufficient Balance!\nCurrent Balance: Rs. " + user.getBalance());
                return;
            }

            BankService.withdraw(user, amount);

            JOptionPane.showMessageDialog(this,
                    "Withdraw Successful!\nRemaining Balance: Rs. " + user.getBalance());

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter numbers only");
        }
    }
}