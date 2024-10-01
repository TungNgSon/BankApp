/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankapp.gui;

/**
 *
 * @author Moment
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import obj.MyJDBC;
public class RegisterGui extends BaseFrame
{
    public RegisterGui()
    {
        super("RegisterApp");
    }
    @Override protected void addGuiComponents()
    {
        //BankAppilication Label
        JLabel bankApplication =new JLabel("Banking Application");
        bankApplication.setBounds(0, 20, super.getWidth(), 40);
        bankApplication.setFont(new Font("Dialog",Font.BOLD,32));
        bankApplication.setHorizontalAlignment(SwingConstants.CENTER);
        add(bankApplication);
        
        //Username lable
        JLabel username = new JLabel("Username:");
        username.setBounds(20, 120, super.getWidth()-30, 30);
        username.setFont(new Font("Dialog",Font.PLAIN,20));
        add(username);
        
        //UsernameField
        JTextField usernameField=new JTextField();
        usernameField.setBounds(20, 160, super.getWidth()-50, 40);
        usernameField.setFont(new Font("Dialog",Font.PLAIN,30));
        add(usernameField);
        
        //Password lable
        JLabel password = new JLabel("Password:");
        password.setBounds(20, 220, super.getWidth()-30, 30);
        password.setFont(new Font("Dialog",Font.PLAIN,20));
        add(password);
        
        //PasswordField
        JPasswordField passwordField=new JPasswordField();
        passwordField.setBounds(20, 260, super.getWidth()-50, 40);
        passwordField.setFont(new Font("Dialog",Font.PLAIN,30));
        add(passwordField);
        //Repassword
        JLabel repassword = new JLabel("Re-Type Password:");
        repassword.setBounds(20, 300, super.getWidth()-30, 30);
        repassword.setFont(new Font("Dialog",Font.PLAIN,20));
        add(repassword);
        
        //RepassField
        JPasswordField repasswordField=new JPasswordField();
        repasswordField.setBounds(20, 330, super.getWidth()-50, 40);
        repasswordField.setFont(new Font("Dialog",Font.PLAIN,30));
        add(repasswordField);
        
        //Register Button
        JButton RegisButton=new JButton("Register");
        RegisButton.setBounds(super.getWidth()/2-120,super.getHeight()-180,250, 40);
        RegisButton.setFont(new Font("Dialog",Font.BOLD,20));
        RegisButton.addActionListener(new ActionListener()
        {
            @Override public void actionPerformed(ActionEvent e)
            {
                String username=usernameField.getText();
                String password=String.valueOf(passwordField.getPassword());
                String repassword=String.valueOf(repasswordField.getPassword());
                if(validRegis(username,password,repassword))
                {
                    if(!MyJDBC.checkUser(username))
                    {
                        MyJDBC.register(username, password);
                        JOptionPane.showMessageDialog(RegisterGui.this, "Register Succesfully!");
                        RegisterGui.this.dispose();
                        new LoginGui().setVisible(true);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(RegisterGui.this, "Username existed!");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(RegisterGui.this, "Your input is invalid!");
                }
            }
        });
        add(RegisButton);
        
        //Register Label
        JLabel loginLabel=new JLabel("<html><a href=\"#\">If you had an account , click here! ");
        loginLabel.setBounds(super.getWidth()/2-160,super.getHeight()-150,super.getWidth(), 40);
        loginLabel.setFont(new Font("Dialog",Font.BOLD,20));
        loginLabel.addMouseListener(new MouseAdapter()
        {
            @Override public void mouseClicked(MouseEvent e)
            {
                RegisterGui.this.dispose();
                new LoginGui().setVisible(true);
            }
        });
        add(loginLabel);
    }
    private boolean validRegis(String username,String password,String repassword)
    {
        if(username.length()==0||password.length()==0||repassword.length()==0)
        {
           
            return false;
        }
        if(username.length()<6||password.length()<8)
        {
            
            return false;
        }
        if(!password.equals(repassword))
        {
           
            return false;
        }
        return true;
    }
}
