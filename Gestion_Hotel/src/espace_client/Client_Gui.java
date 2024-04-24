package espace_client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import compte.Changepwd;
import espace_reservation.DemandeReservationGUI;
import guiElements.Button;
import login.Login;
import user.User;

public class Client_Gui extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	static Button logout;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client_Gui frame = new Client_Gui();
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
	public Client_Gui() {
		new Client_Gui(default_user_parameter);
	}
	
	public Client_Gui(User user) {
		setTitle("Espace Client");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(null);
		setUndecorated(true);
		
		//bouton home :
		Button home = new Button();
        home.setBounds(28, 77, 24, 24);
        getContentPane().add(home);
        home.addActionListener(this);
        
        
        //bouton logout : 
        logout = new Button();
        logout.setBounds(28, 130, 24, 24);
        getContentPane().add(logout);
        logout.addActionListener(this);
        
        
		//bouton pour Faire une réservation :
        Button reserver = new Button();
        reserver.setBounds(281, 199, 87, 76);
        getContentPane().add(reserver);
        reserver.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				new DemandeReservationGUI(user);
				dispose();
			}
        });
        
        //bouton pour demander gérer le compte :
        Button compte = new Button();
        compte.setBounds(485, 207, 81, 68);
        getContentPane().add(compte);
        compte.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				new Changepwd(user);
				dispose();
			}
        });
        
        //bouton pour "Demande de l'aide"
        Button aide = new Button();
        aide.setBounds(	28, 468, 23, 20);
        getContentPane().add(aide);
        aide.addActionListener(this); 
        
        //bouton pour acceder aux paramètres de comptes :
        
        Button paramCompte = new Button();
        paramCompte.setBounds(	709, 9, 54, 32);
        getContentPane().add(paramCompte);
        paramCompte.addActionListener(this);
		//Code pour le background:
		JLabel BackgroundLabel = new JLabel();
        BackgroundLabel.setBounds(0, 0, 800, 500);
        ImageIcon img = new ImageIcon(this.getClass().getResource("/client_frame.png"));
        BackgroundLabel.setIcon(img);
        getContentPane().add(BackgroundLabel);
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == logout) {
			new Login();
            dispose();
		}
		
	}
}
