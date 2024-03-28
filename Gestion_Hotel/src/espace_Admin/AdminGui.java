package espace_Admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import guiElements.Button;
import login.Login;

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
	public AdminGui() {
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
        compte.addActionListener(this);
        
        //bouton pour demander les statistiques :
        Button dashbord = new Button();
        dashbord.setBounds(638, 150, 87, 76);
        getContentPane().add(dashbord);
        dashbord.addActionListener(this);
        
        //Bouton pour gestion de paiement 
        Button gest_paiement = new Button();
        gest_paiement.setBounds(160, 150, 68, 76);
        getContentPane().add(gest_paiement);
        gest_paiement.addActionListener(this);
        
      //Bouton pour gestion de réservation : 
        Button gest_reservation = new Button();
        gest_reservation.setBounds(151, 342, 87, 87);
        getContentPane().add(gest_reservation);
        gest_reservation.addActionListener(this);
        
      //Bouton pour gestion de réservation : 
        Button gest_chambre = new Button();
        gest_chambre.setBounds(399, 270, 96, 68);
        getContentPane().add(gest_chambre);
        gest_chambre.addActionListener(this);
        
        
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
