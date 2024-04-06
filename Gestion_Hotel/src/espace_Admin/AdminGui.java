package espace_Admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import chambre.ChambreGUI;
import compte.CompteGui;
import dashbord.Dashbord;
import espace_paiement.PaiementGui;
import espace_reservation.ReservationGUI;
import guiElements.Button;
import login.Login;
import user.User;

public class AdminGui extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	static Button logout;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGui frame = new AdminGui();
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
	public AdminGui() {
		new AdminGui(default_user_parameter);
	}
	public AdminGui(User user) {
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
        Button compte = new Button();
        compte.setBounds(628, 350, 87, 68);
        getContentPane().add(compte);
        compte.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				new CompteGui(user);
				dispose();
			}
        });
        
        //bouton pour demander les statistiques :
        Button dashbord = new Button();
        dashbord.setBounds(399, 271, 96, 68);
        getContentPane().add(dashbord);
        dashbord.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				new Dashbord(user);
				dispose();
			}
        });
        
        //Bouton pour gestion de paiement 
        Button gest_paiement = new Button();
        gest_paiement.setBounds(641, 158, 74, 68);
        getContentPane().add(gest_paiement);
        gest_paiement.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				new PaiementGui(user);
				dispose();
			}
        });
        
        
      //Bouton pour gestion de réservation : 
        Button gest_reservation = new Button();
        gest_reservation.setBounds(163, 150, 68, 76);
        getContentPane().add(gest_reservation);
        gest_reservation.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				new ReservationGUI(user);
				dispose();
			}
        });
        
      //Bouton pour gestion de Chambre : 
        Button gest_chambre = new Button();
        gest_chambre.setBounds(149, 350, 96, 68);
        getContentPane().add(gest_chambre);
        gest_chambre.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				new ChambreGUI(user);
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
        ImageIcon img = new ImageIcon(this.getClass().getResource("/admin_frame.png"));
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
