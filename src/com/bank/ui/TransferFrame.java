package com.bank.ui;

import javax.swing.*;
import java.awt.*;

import com.bank.model.User;
import com.bank.service.BankService;

public class TransferFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    JTextField nameField, bankField, branchField, accField, amountField;
    JButton sendBtn, cancelBtn;
    User user;

    public TransferFrame(User user) {
        this.user = user;

        setTitle("Bank Transfer");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        //title
        JLabel title = new JLabel("Money Transfer", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(0, 102, 204));
        add(title, BorderLayout.NORTH);
        
        //center panel
        JPanel center = new JPanel(new GridLayout(5, 2, 10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        center.add(new JLabel("Receiver Name:"));
        nameField = new JTextField();
        center.add(nameField);

        center.add(new JLabel("Bank Name:"));
        bankField = new JTextField();
        center.add(bankField);

        center.add(new JLabel("Branch:"));
        branchField = new JTextField();
        center.add(branchField);

        center.add(new JLabel("Account Number:"));
        accField = new JTextField();
        center.add(accField);

        center.add(new JLabel("Amount:"));
        amountField = new JTextField();
        center.add(amountField);

        add(center, BorderLayout.CENTER);
         //buttons
        JPanel bottom = new JPanel();

        sendBtn = new JButton("Send Money");
        cancelBtn = new JButton("Cancel");

        sendBtn.setBackground(new Color(0, 153, 76));
        sendBtn.setForeground(Color.WHITE);

        cancelBtn.setBackground(Color.GRAY);
        cancelBtn.setForeground(Color.WHITE);

        bottom.add(sendBtn);
        bottom.add(cancelBtn);

        add(bottom, BorderLayout.SOUTH);

        sendBtn.addActionListener(e -> sendMoney());
        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void sendMoney() {
        try {
            String name   = nameField.getText();
            String bank   = bankField.getText();
            String branch = branchField.getText();
            String accNo  = accField.getText();
            double amount = Double.parseDouble(amountField.getText());

            if (name.isEmpty() || bank.isEmpty() || branch.isEmpty() || accNo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all details");
                return;
            }

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Enter valid amount");
                return;
            }

            if (amount > user.getBalance()) {
                JOptionPane.showMessageDialog(this,
                        "Insufficient Balance!\nCurrent Balance: Rs. " + user.getBalance());
                return;
            }

            boolean success = BankService.transfer(user, accNo, amount);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Transfer Successful!\nRemaining Balance: Rs. " + user.getBalance());
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Receiver Account Not Found");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter valid amount");
        }
    }
}