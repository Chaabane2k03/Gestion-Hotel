package user;

import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import espace_Admin.AdminGui;
import espace_client.Client_Gui;
import guiElements.Button;
import login.Login;
import reception.Receptioniste;

import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;

public class AddSpecialUser extends JFrame  {

	private static final long serialVersionUID = 1L;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddSpecialUser frame = new AddSpecialUser();
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
	private JTextField textField;
	private JPasswordField passwordField;
	public AddSpecialUser() {
		new AddSpecialUser(default_user_parameter);
	}
	
	public AddSpecialUser(User user) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(null);
		
		
		
		
		//Boutons de nav Bar
		Button home = new Button();
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (user.getTypeuser() == 0) {
					new AdminGui(user);
					dispose();
				}
				else if (user.getTypeuser() == 1) {
					new Receptioniste(user);
					dispose();
				}
				else {
					new Client_Gui(user);
					dispose();
				}
			}
		});
		
        home.setBounds(28, 77, 24, 24);
        getContentPane().add(home);
        
        // LogOut Button
        Button logout = new Button();
        logout.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				new Login();
				dispose();
			}
        });
        logout.setBounds(28, 130, 24, 24);
        getContentPane().add(logout);
		// Background :
		JLabel BackgroundLabel = new JLabel();
        BackgroundLabel.setBounds(0, 0, 800, 500);
        ImageIcon img = new ImageIcon(this.getClass().getResource("/features_frame.png"));
        BackgroundLabel.setIcon(img);
        getContentPane().add(BackgroundLabel);
        
        textField = new JTextField();
        textField.setBounds(364, 158, 242, 32);
        getContentPane().add(textField);
        textField.setColumns(10);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(364, 218, 242, 32);
        getContentPane().add(passwordField);
        
        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
        comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Admin", "Réceptioniste"}));
        comboBox.setBounds(364, 281, 242, 32);
        getContentPane().add(comboBox);
        
        JButton btnNewButton = new JButton("Ajouter");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//TODO : vérifier que l'utilisateur n'existe pas dans la base de données
        		
        		//TODO : Ajouter user
        	}
        });
        btnNewButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
        btnNewButton.setBounds(552, 347, 139, 40);
        getContentPane().add(btnNewButton);
        
        JLabel lblNewLabel = new JLabel("Username");
        lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
        lblNewLabel.setBounds(191, 158, 146, 32);
        getContentPane().add(lblNewLabel);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
        lblPassword.setBounds(191, 218, 146, 32);
        getContentPane().add(lblPassword);
        
        JLabel lblTypeDutilisateur = new JLabel("Type d'utilisateur");
        lblTypeDutilisateur.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
        lblTypeDutilisateur.setBounds(191, 281, 163, 32);
        getContentPane().add(lblTypeDutilisateur);
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
	}


}
