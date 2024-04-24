package compte;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;


import javax.swing.border.LineBorder;

import espace_Admin.AdminGui;
import guiElements.Button;
import login.Login;
import user.User;

import java.awt.Color;
import java.awt.event.ActionEvent;

public class Changepwd extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    Button button;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Changepwd frame = new Changepwd();
                    frame.setVisible(true);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
	User default_user_parameter= null;

    public Changepwd() {
		new Changepwd(default_user_parameter);
	}
    public Changepwd(User user) {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        getContentPane().setLayout(null);
        setUndecorated(true);

        Button home = new Button();
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AdminGui(user);
				dispose();
			}
		});
        home.setBounds(28, 54, 24, 24);
        getContentPane().add(home);
        
        // LogOut Button
        Button logout = new Button();
        logout.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				new Login();
				dispose();
			}
        });
        logout.setBounds(28, 109, 24, 24);
        getContentPane().add(logout);
        
        
        // Old Password Field
        oldPasswordField = new JPasswordField();
        oldPasswordField.setColumns(10);
        oldPasswordField.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        oldPasswordField.setBounds(218,188, 364, 56);
        getContentPane().add(oldPasswordField);
        
        // New Password Field
        newPasswordField = new JPasswordField();
        newPasswordField.setColumns(10);
        newPasswordField.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        newPasswordField.setBounds(218, 292 , 364, 56);
        getContentPane().add(newPasswordField);
        
        
        button = new Button();
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		char[] oldPassword = oldPasswordField.getPassword();
                char[] newPassword = newPasswordField.getPassword();
                String oldPass = User.cryptPass(String.valueOf(oldPassword));
                String newPass = User.cryptPass(String.valueOf(String.valueOf(newPassword)));
                
                if (oldPass.equals(user.getPassword())) {
                	if (oldPass.equals(newPass) ) {
                		JOptionPane.showMessageDialog(null, "It's already your password ","Error", JOptionPane.ERROR_MESSAGE);
                	}
                	else {
                		user.setPassword(newPass);
                		User.updateuserDB(user);
    	        		JOptionPane.showMessageDialog(null,"Password Changed ","Success",JOptionPane.INFORMATION_MESSAGE);
                	}
                }
                else {
                	JOptionPane.showMessageDialog(null, "Incorrect old Password ","Error", JOptionPane.ERROR_MESSAGE);
                }
                
        	}
        });
        button.setBounds(218, 378, 364, 51);
        getContentPane().add(button);
        
       
        
        // Background Image
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 800, 500);
        ImageIcon img = new ImageIcon(this.getClass().getResource("/Change_Password.png"));
        backgroundLabel.setIcon(img);
        getContentPane().add(backgroundLabel);
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    
    
    public void actionPerformed(ActionEvent e) {
    	
        /*if (e.getSource() == changePasswordButton) {
            String username = usernameField.getText();
            char[] oldPassword = oldPasswordField.getPassword();
            char[] newPassword = newPasswordField.getPassword();
            String oldPass = String.valueOf(oldPassword);
            String newPass = String.valueOf(newPassword);

            User.updateUser(username, newPass,iduser);

            JOptionPane.showMessageDialog(null, "Password Changed Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == exitButton) {
            setVisible(false);
            dispose();
        }*/
    }

}
