/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankapp.gui;
import obj.User;
import javax.swing.*;
public abstract class BaseFrame extends JFrame
{
    protected User user;
    public BaseFrame(String title,User user)
    {
        this.user=user;
        //Set Title 
        this.setTitle(title);
        //Set Size
        this.setSize(500, 600);
        //Set Defautl Close
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set layout
        this.setLayout(null);
        //set Size can not be changed
        this.setResizable(false);
        //set the window in the centre of screen
        this.setLocationRelativeTo(null);
        addGuiComponents();
        
    }
    public BaseFrame(String title)
    {
        //Set Title 
        this.setTitle(title);
        //Set Size
        this.setSize(500, 600);
        //Set Defautl Close
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set layout
        this.setLayout(null);
        //set Size can not be changed
        this.setResizable(false);
        //set the window in the centre of screen
        this.setLocationRelativeTo(null);
        addGuiComponents();
        
    }
    protected abstract void addGuiComponents();
    
}
