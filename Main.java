import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false; // Insufficient balance or invalid amount
        }
    }
}

class ATMGUI extends JFrame {
    private BankAccount account;
    private JTextField amountField;
    private JTextArea infoArea;

    public ATMGUI(BankAccount account) {
        this.account = account;
        setTitle("ATM Machine");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components

        JLabel label = new JLabel("Enter Amount: ");
        amountField = new JTextField(10);
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton balanceButton = new JButton("Check Balance");

        infoArea = new JTextArea(8, 30);
        infoArea.setEditable(false);

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });

        balanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(amountField, gbc);

        // Create a horizontal box for buttons
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(Box.createRigidArea(new Dimension(10, 0))); // Add spacing

        buttonBox.add(depositButton);
        buttonBox.add(Box.createRigidArea(new Dimension(10, 0))); // Add spacing
        buttonBox.add(withdrawButton);
        buttonBox.add(Box.createRigidArea(new Dimension(10, 0))); // Add spacing
        buttonBox.add(balanceButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(buttonBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(infoArea, gbc);

        add(panel, BorderLayout.CENTER);

        // Customize the look and feel
        depositButton.setBackground(Color.GREEN);
        depositButton.setForeground(Color.WHITE);
        withdrawButton.setBackground(Color.RED);
        withdrawButton.setForeground(Color.WHITE);
        balanceButton.setBackground(Color.BLUE);
        balanceButton.setForeground(Color.WHITE);
    }

    private void deposit() {
        String amountStr = amountField.getText();
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount > 0) {
                account.deposit(amount);
                infoArea.setText("Deposit successful. New balance: $" + account.getBalance());
            } else {
                infoArea.setText("Invalid deposit amount.");
            }
        } catch (NumberFormatException e) {
            infoArea.setText("Invalid amount format.");
        }
        amountField.setText("");
    }

    private void withdraw() {
        String amountStr = amountField.getText();
        try {
            double amount = Double.parseDouble(amountStr);
            if (account.withdraw(amount)) {
                infoArea.setText("Withdrawal successful. New balance: $" + account.getBalance());
            } else {
                infoArea.setText("Withdrawal failed. Insufficient balance or invalid amount.");
            }
        } catch (NumberFormatException e) {
            infoArea.setText("Invalid amount format.");
        }
        amountField.setText("");
    }

    private void checkBalance() {
        infoArea.setText("Your balance is: $" + account.getBalance());
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount userAccount = new BankAccount(1000.0); // Initial balance is $1000
        ATMGUI atm = new ATMGUI(userAccount);
        atm.setVisible(true);
    }
}