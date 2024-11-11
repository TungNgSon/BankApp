package bankapp.guis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import objs.MyJDBC;

public class RegisterGui extends BaseFrame {
    public RegisterGui() {
        super("Banking App Register");
    }

    @Override
    protected void addGuiComponents() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null); // Sử dụng layout null để tùy chỉnh vị trí các thành phần

        //banking app label
        JLabel bankingAppLabel = new JLabel("PTITBANK PLUS");
        bankingAppLabel.setBounds(0, 20, super.getWidth(), 40);
        bankingAppLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bankingAppLabel.setForeground(new Color(183, 28, 28));
        add(bankingAppLabel);

        //icon
        ImageIcon logoPTITicon = new ImageIcon("D:\\Workspace\\PTIT.jpg");
        Image logoPTITimage = logoPTITicon.getImage();
        Image resizePTITlogo = logoPTITimage.getScaledInstance(110, 100, Image.SCALE_SMOOTH);
        ImageIcon resizePTITlogoIcon = new ImageIcon(resizePTITlogo);
        JLabel logoPTITLabel = new JLabel(resizePTITlogoIcon);
        logoPTITLabel.setBounds((super.getWidth() - 110) / 2, 70, 110, 100); // Căn giữa biểu tượng
        add(logoPTITLabel);

        //create username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 190, 150, 20);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        usernameLabel.setForeground(Color.RED);
        add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(180, 190, super.getWidth() - 210, 30);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 22));
        add(usernameField);

        //create phoneNumber label and field
        JLabel phoneNumberLabel = new JLabel("Phone number:");
        phoneNumberLabel.setBounds(20, 240, 150, 20);
        phoneNumberLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        phoneNumberLabel.setForeground(Color.RED);
        add(phoneNumberLabel);

        JTextField phoneNumberField = new JTextField();
        phoneNumberField.setBounds(180, 240, super.getWidth() - 210, 30);
        phoneNumberField.setFont(new Font("Dialog", Font.PLAIN, 22));
        add(phoneNumberField);

        //create CCCD/CMND label and field
        JLabel CCCD_CMNDLabel = new JLabel("CCCD/CMND:");
        CCCD_CMNDLabel.setBounds(20, 290, 150, 20);
        CCCD_CMNDLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        CCCD_CMNDLabel.setForeground(Color.RED);
        add(CCCD_CMNDLabel);

        JTextField CCCD_CMNDField = new JTextField();
        CCCD_CMNDField.setBounds(180, 290, super.getWidth() - 210, 30);
        CCCD_CMNDField.setFont(new Font("Dialog", Font.PLAIN, 22));
        add(CCCD_CMNDField);

        //create password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 340, 150, 20);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        passwordLabel.setForeground(Color.RED);
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(180, 340, super.getWidth() - 210, 30);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 22));
        add(passwordField);

        //create rePassword label and field
        JLabel rePasswordLabel = new JLabel("Re-type Password:");
        rePasswordLabel.setBounds(20, 390, 150, 20);
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        rePasswordLabel.setForeground(Color.RED);
        add(rePasswordLabel);

        JPasswordField rePasswordField = new JPasswordField();
        rePasswordField.setBounds(180, 390, super.getWidth() - 210, 30);
        rePasswordField.setFont(new Font("Dialog", Font.PLAIN, 22));
        add(rePasswordField);

        //create OTP label and field (NEW)
        JLabel otpLabel = new JLabel("OTP (6 digits):");
        otpLabel.setBounds(20, 440, 150, 20);
        otpLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        otpLabel.setForeground(Color.RED);
        add(otpLabel);

        JTextField otpField = new JTextField();
        otpField.setBounds(180, 440, super.getWidth() - 210, 30);
        otpField.setFont(new Font("Dialog", Font.PLAIN, 22));
        add(otpField);

        //create Register button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(20, 480, super.getWidth() - 50, 40);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(183, 28, 28));
        registerButton.addActionListener(new ActionListener()
        {
            @Override public void actionPerformed(ActionEvent e)
            {
                String username=usernameField.getText();
                String password=String.valueOf(passwordField.getPassword());
                String repassword=String.valueOf(rePasswordField.getPassword());
                String CCCD=CCCD_CMNDField.getText();
                String OTP=otpField.getText();
                String phoneNumber=phoneNumberField.getText();
                String STK=phoneNumber;
                if(validRegis(username,password,repassword,CCCD,OTP,phoneNumber))
                {
                    try {
                        if(!MyJDBC.checkUser(username, CCCD, phoneNumber))
                        {
                           MyJDBC.register(username, password, CCCD, OTP, phoneNumber,STK);
                           JOptionPane.showMessageDialog(RegisterGui.this, "Register succesfully !");
                           RegisterGui.this.dispose();
                           new LoginGui().setVisible(true);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(RegisterGui.this, "Account existed !");
                        }
                    } catch (SQLException ex) {
                        
                    }
                }
                
            }
        });
        add(registerButton);

        //create Login label
        JLabel loginLabel = new JLabel("<html><a href=\"#\">Have an account? Sign-in here</a><html>");
        loginLabel.setBounds(15, 520, super.getWidth() - 50, 40);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.addMouseListener(new MouseAdapter()
        {
            @Override public void mouseClicked(MouseEvent e)
            {
                bankapp.guis.RegisterGui.this.dispose();
                new bankapp.guis.LoginGui().setVisible(true);
            }
        });
        add(loginLabel);
    }
   private boolean validRegis(String username, String password, String repassword, String CCCD, String OTP, String phoneNumber) 
   {
    // Kiểm tra các trường không được bỏ trống
    if (username.length() == 0 || password.length() == 0 || repassword.length() == 0 ||
        CCCD.length() == 0 || OTP.length() == 0 || phoneNumber.length() == 0) {
        JOptionPane.showMessageDialog(RegisterGui.this, "Fields cannot be empty!");
        return false;
    }

    // Kiểm tra độ dài tên người dùng (ít nhất 6 ký tự)
    if (username.length() < 6) {
        JOptionPane.showMessageDialog(RegisterGui.this, "Username must be at least 6 characters long!");
        return false;
    }

    // Kiểm tra độ dài mật khẩu (ít nhất 8 ký tự)
    if (password.length() < 8) {
        JOptionPane.showMessageDialog(RegisterGui.this, "Password must be at least 8 characters long!");
        return false;
    }

    // Kiểm tra mật khẩu và xác nhận mật khẩu phải trùng khớp
    if (!password.equals(repassword)) {
        JOptionPane.showMessageDialog(RegisterGui.this, "Passwords do not match!");
        return false;
    }

    // Kiểm tra CCCD có đúng định dạng (độ dài phải là 12 số)
    if (CCCD.length() != 12 || !CCCD.matches("\\d+")) {
        JOptionPane.showMessageDialog(RegisterGui.this, "CCCD must be exactly 12 digits!");
        return false;
    }

    // Kiểm tra OTP có đúng định dạng (OTP gồm 6 chữ số)
    if (OTP.length() != 6 || !OTP.matches("\\d+")) {
        JOptionPane.showMessageDialog(RegisterGui.this, "OTP must be exactly 6 digits!");
        return false;
    }

    // Kiểm tra số điện thoại có đúng định dạng (bắt đầu bằng 0 và độ dài từ 10-11 số)
    if (!phoneNumber.matches("0\\d{9,10}")) {
        JOptionPane.showMessageDialog(RegisterGui.this, "Phone number must start with 0 and be 10 or 11 digits long!");
        return false;
    }

    // Nếu tất cả kiểm tra đều hợp lệ
    return true;
}
    public static void main(String[] args) {
        new RegisterGui().setVisible(true);
    }
    
}
