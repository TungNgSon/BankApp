package bankapp.guis;

import objs.MyJDBC;
import objs.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import bankapp.guis.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
    This gui will allow user to login or launch the register gui
    This extends from the BaseFrame which emans we will need to define our own addGuiComponent()
 */
public class LoginGui extends BaseFrame{
    public LoginGui(){
        super("Banking App Login");
    }

    @Override
    protected void addGuiComponents() {
    	// tạo màu nền
    	getContentPane().setBackground(Color.WHITE);
    	// thêm logo
    	ImageIcon logoIcon = new ImageIcon(getClass().getResource("icon//LogoImage.jpg"));
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(190, 100,100,100);
        add(logoLabel);
        
        //ImageIcon logoIcon1 = new ImageIcon("C:\\Users\\Moment\\Documents\\NetBeansProjects\\BankApp\\BankApp1\\src\\bankapp\\guis\\icon\\VietNamFlag.png"); // Đường dẫn tới tệp hình ảnh
        ImageIcon logoIcon1 = new ImageIcon(getClass().getResource("/bankapp/guis/icon/VietNamFlag.png"));
        JLabel logoLabel1 = new JLabel(logoIcon1);
        logoLabel1.setBounds(380, 20,60,60);
        add(logoLabel1);
        
        // create banking app label
        JLabel bankingAppLabel = new JLabel("PTITBANK PLUS");
        

        // set the location and the size of the gui component
        bankingAppLabel.setBounds(0, 30, super.getWidth(), 40);

        // change the font style
        bankingAppLabel.setFont(new Font("Dialog", Font.BOLD, 32));

        // center text in Jlabel
        bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //đổi chữ BankingApp thành màu trắng
        bankingAppLabel.setForeground(new Color(183, 28, 28));
        // add to gui
        add(bankingAppLabel);

        // username label
        JLabel usernameLabel = new JLabel("Tên đăng nhập");

        // getWidth() returns us the width of our frame which is about 420
        usernameLabel.setBounds(20, 215, getWidth() - 30, 24);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        // đổi chữ thành màu trắng
        usernameLabel.setForeground(new Color(183, 28, 28));
        add(usernameLabel);

        // create username field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 240, getWidth() - 50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 25));
        // đổi màu cho khung viền 
        add(usernameField);

        // create password label
        JLabel passwordLabel = new JLabel("Mật khẩu");
        passwordLabel.setBounds(20, 295, getWidth() - 50, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        // đổi chữ thành màu trắng
        passwordLabel.setForeground(new Color(183, 28, 28));
        add(passwordLabel);

        // create password field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 320, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 25));
        add(passwordField);
        
        // Checkbox để hiển thị mật khẩu
        JCheckBox showPasswordCheckBox = new JCheckBox("Hiển thị mật khẩu");
        showPasswordCheckBox.setBounds(135, 362, 150, 24);
        showPasswordCheckBox.setFont(new Font("Dialog", Font.PLAIN, 14));
        showPasswordCheckBox.setForeground(new Color(183, 28, 28));
        showPasswordCheckBox.setBackground(Color.WHITE); // Màu nền giống như frame

        // Thêm ActionListener cho checkbox
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0); // Hiển thị mật khẩu
                } else {
                    passwordField.setEchoChar('•'); // Ẩn mật khẩu
                }
            }
        });

        add(showPasswordCheckBox);
        
        // Thêm nút quên mật khẩu
        JButton forgotPasswordButton = new JButton("Quên mật khẩu?");
        forgotPasswordButton.setBounds(20, 410, getWidth() - 50, 40);
        forgotPasswordButton.setFont(new Font("Dialog", Font.PLAIN, 16));
        forgotPasswordButton.setForeground(new Color(183, 28, 28));
        forgotPasswordButton.setBackground(Color.WHITE);
        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a password recovery GUI or dialog
                // This is just a placeholder, replace with actual implementation
                dispose();
            	new ForgotPassword().setVisible(true);
            	    }
        });
        add(forgotPasswordButton);
        
        // create login button
        JButton loginButton = new JButton("Đăng nhập");
        loginButton.setBounds(20, 460, getWidth() - 50, 40);
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20));
        loginButton.setBackground(new Color(183, 28, 28)); // Màu nền giống bankingAppLabel
        loginButton.setForeground(Color.WHITE); // Màu chữ trắng để nổi bật
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get username
                String username = usernameField.getText();

                // get password
                String password = String.valueOf(passwordField.getPassword());

                // validate login
                User user;
                try {
                    user = MyJDBC.validLogin(username, password);
                 

                // if user is null it means invalid otherwise it is a valid account
                if(user != null){
                    // means valid login

                    // dispose this gui
                    LoginGui.this.dispose();

                    // launch bank app gui
                    //BankingAppGui bankingAppGui = new BankingAppGui(user);
                    BankingAppGui bankingAppGui = new BankingAppGui(user);
                    bankingAppGui.setVisible(true);

                    // show success dialog
                    JOptionPane.showMessageDialog(bankingAppGui, "Login Successfully!");
                }else{
                    // invalid login
                    
                    JOptionPane.showMessageDialog(LoginGui.this, "Login failed...");
                }
            }
                catch (SQLException ex) {
                    Logger.getLogger(LoginGui.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
        });
        add(loginButton);

        // create register label
        JLabel registerLabel = new JLabel("<html><a href=\"#\">Chưa có tài khoản, ĐĂNG KÍ</a></html>");
        registerLabel.setBounds(0, 510, getWidth() - 10, 30);
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // adds an event listener so when the mouse is clicked it will launch the register gui
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // dispose of this gui
                LoginGui.this.dispose();

                // launch the register gui
                new RegisterGui().setVisible(true);
            }
        });

        add(registerLabel);
    }
}















