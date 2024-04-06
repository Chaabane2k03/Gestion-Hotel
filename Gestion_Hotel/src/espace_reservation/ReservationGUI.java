package espace_reservation;

import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import espace_Admin.AdminGui;
import guiElements.Button;
import login.Login;
import reception.Receptioniste;
import user.User;

import java.awt.event.ActionEvent;

public class ReservationGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReservationGUI frame = new ReservationGUI();
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

	public ReservationGUI() {
		new ReservationGUI(default_user_parameter);
	}
	public ReservationGUI(User user) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(null);
		setUndecorated(true);
		
		
		
		
		//Boutons de nav Bar
		Button home = new Button();
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (user.getTypeuser() == 0) {
					new AdminGui(user);
					dispose();
				}
				else {
					new Receptioniste(user);
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
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
