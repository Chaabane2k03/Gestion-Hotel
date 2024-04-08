package login;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

import espace_Admin.AdminGui;
import espace_client.Client_Gui;
import guiElements.Button;
import reception.Receptioniste;
import user.User;

import java.awt.Color;
import java.awt.event.ActionEvent;

public class Login extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	static JTextField loginField;
	static Button button;
	static JPasswordField pwdField;
	static Button exit;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(null);
		setUndecorated(true);
		//Champs de texte pour l'email :
		loginField = new JTextField("");
		loginField.setFont(new Font("Segoe UI Variable", Font.PLAIN, 18));
		loginField.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		loginField.setBounds(380,190,390,45);
        getContentPane().add(loginField);
        loginField.setColumns(10);
        
        //Code pour le champs de mot de passe :
        pwdField = new JPasswordField();
        pwdField.setColumns(10);
        pwdField.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        pwdField.setBounds(380, 300, 390, 45);
        getContentPane().add(pwdField);
        
        //Code pour le boutton :
        button = new Button();
        button.setBounds(395, 365, 364, 51);
        getContentPane().add(button);
        button.addActionListener(this);

        exit = new Button();
        exit.setBounds(761, 8, 26, 25);
        getContentPane().add(exit);
        exit.addActionListener(this);
        
		//Code pour le design de background :
		JLabel BackgroundLabel = new JLabel();
        BackgroundLabel.setBounds(0, 0, 800, 500);
        ImageIcon img = new ImageIcon(this.getClass().getResource("/login_frame.png"));
        BackgroundLabel.setIcon(img);
        getContentPane().add(BackgroundLabel);
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button) {
			String username = loginField.getText();
	        char[] password = pwdField.getPassword();
	        String pass = String.valueOf(password);
	        User user = new User(username, User.cryptPass(pass));
            user = user.getUserFromDB();
	        if (user != null) {
	        	if (user.getTypeuser() == 0) {
	        		JOptionPane.showMessageDialog(null,"Login successful as admin ","Success",JOptionPane.INFORMATION_MESSAGE);
                    new AdminGui(user);
                    dispose();
                } 
	        	else if (user.getTypeuser() == 1) {
	        		JOptionPane.showMessageDialog(null,"Login successful as Receptionist ","Success",JOptionPane.INFORMATION_MESSAGE);
                    new Receptioniste(user);
                    dispose();
	        	}
	        	else {
	        		JOptionPane.showMessageDialog(null,"Login successful as Client ","Success",JOptionPane.INFORMATION_MESSAGE);
                    new Client_Gui(user);
                    dispose();
	        	}
                    
	        } else
				JOptionPane.showMessageDialog(null, "Invalid Login or Password!","Error", JOptionPane.ERROR_MESSAGE);

		}
		
		else if (e.getSource() == exit) {
			setVisible(false);
			dispose();
		}
		
	}
}
