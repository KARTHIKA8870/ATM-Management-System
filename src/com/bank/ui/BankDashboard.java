package com.bank.ui;

	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.*;
	import com.bank.model.User;

public class BankDashboard extends JFrame implements ActionListener 
	
{
	private static final long serialVersionUID=1L;

	    JButton depositBtn, withdrawBtn, balanceBtn, transferBtn, exitBtn, historyBtn;
	    User user;

	    public BankDashboard(User user) {
	        this.user = user;

	        setTitle("Bank Management System");
	        setSize(380, 420);
	        setResizable(false);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        // Layout
	        setLayout(new GridLayout(6, 1, 10, 8));

	        // Font
	        Font f = new Font("Arial", Font.BOLD, 14);
            getContentPane().setBackground(new Color(245, 248, 255));
	        depositBtn  = new JButton("Deposit");
	        withdrawBtn = new JButton("Withdraw");
	        balanceBtn  = new JButton("Check Balance");
	        transferBtn = new JButton("Transfer");
	        exitBtn     = new JButton("Exit");
	        historyBtn=new JButton("Transaction HISTORY");
	        
	        depositBtn.setBackground(new Color(0, 102, 204));   // Blue
	        withdrawBtn.setBackground(new Color(255, 153, 51)); // Orange
	        balanceBtn.setBackground(new Color(108, 117, 125)); // Grey
	        transferBtn.setBackground(new Color(111, 66, 193)); // Purple
	        historyBtn.setBackground(new Color(40, 167, 69));   // Green
	        exitBtn.setBackground(new Color(220, 53, 69));      // Red

	        depositBtn.setForeground(Color.WHITE);
	        withdrawBtn.setForeground(Color.WHITE);
	        balanceBtn.setForeground(Color.WHITE);
	        transferBtn.setForeground(Color.WHITE);
	        historyBtn.setForeground(Color.WHITE);
	        exitBtn.setForeground(Color.WHITE);
	        
	        depositBtn.setFont(f);
	        withdrawBtn.setFont(f);
	        balanceBtn.setFont(f);
	        transferBtn.setFont(f);
	        historyBtn.setFont(f);
	        exitBtn.setFont(f);
	        JButton[] buttons = {
	            depositBtn, withdrawBtn, balanceBtn, transferBtn, historyBtn, exitBtn
	        };

	        for (JButton b : buttons) {
	            b.setFont(f);
	            b.setBackground(new Color(0, 153, 204));
	            b.setForeground(Color.WHITE);
	            b.addActionListener(this);
	            add(b);
	        }

	        exitBtn.setBackground(Color.RED);
	        getContentPane().setBackground(new Color(245, 248, 255));//light blue
            historyBtn.setBackground(new Color(40, 167,69));
            JOptionPane.showMessageDialog(this,
            		"Welcome to SBI","WELCOME",JOptionPane.PLAIN_MESSAGE);
	        setVisible(true);
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {

	        if (e.getSource() == depositBtn) {
	            new DepositFrame(user);
	        }
	        else if (e.getSource() == withdrawBtn) {
	            new WithdrawFrame(user);
	        }
	        else if (e.getSource() == balanceBtn) {
	            JOptionPane.showMessageDialog(this,
	                "Current Balance: Rs. " + user.getBalance());
	        }
	        else if (e.getSource() == transferBtn) {
	               new TransferFrame(user);
	        }
	        else if (e.getSource() == exitBtn) {
	            JOptionPane.showMessageDialog(this, "Thank you!","",JOptionPane.PLAIN_MESSAGE);
	            System.exit(0);
	        }
	        else if(e.getSource() == historyBtn) {
	        	new TransactionHistoryFrame(user);
	        }
	    }

}