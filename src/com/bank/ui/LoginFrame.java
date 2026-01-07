package com.bank.ui;

import javax.swing.*;
import java.awt.*;
import com.bank.model.User;
import com.bank.service.LoginService;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField pinField;
    private JButton loginBtn, cancelBtn;

    public LoginFrame() {

        setTitle("ATM Login");
        setSize(380, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== Title =====
        JLabel title = new JLabel("WELCOME TO ATM", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(0, 102, 204));
        add(title, BorderLayout.NORTH);

        // ===== Center Panel =====
        JPanel center = new JPanel(new GridLayout(2, 2, 10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        center.add(new JLabel("Username:"));
        usernameField = new JTextField(15);
        center.add(usernameField);

        center.add(new JLabel("PIN:"));
        pinField = new JPasswordField(15);
        center.add(pinField);

        add(center, BorderLayout.CENTER);

        // ===== Buttons =====
        JPanel bottom = new JPanel();

        loginBtn = new JButton("Login");
        cancelBtn = new JButton("Exit");

        loginBtn.setBackground(new Color(40, 167, 69));
        loginBtn.setForeground(Color.WHITE);

        cancelBtn.setBackground(Color.RED);
        cancelBtn.setForeground(Color.WHITE);

        bottom.add(loginBtn);
        bottom.add(cancelBtn);

        add(bottom, BorderLayout.SOUTH);

        // ===== Actions =====
        loginBtn.addActionListener(e -> login());
        cancelBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String pin = new String(pinField.getPassword()).trim();

        if (username.isEmpty() || pin.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Enter username and PIN",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = LoginService.loginWithPin(username, pin);

        if (user != null) {
            JOptionPane.showMessageDialog(this,
                    "Login Successful!",
                    "Success",
                    JOptionPane.PLAIN_MESSAGE);

            new BankDashboard(user);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid Username or PIN",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
