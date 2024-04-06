package reception;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import compte.CompteGui;
import espace_paiement.PaiementGui;
import espace_reservation.ReservationGUI;
import guiElements.Button;
import login.Login;
import user.User;

public class Receptioniste extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	static Button logout;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Receptioniste frame = new Receptioniste();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	User default_user_parameter= null;
	public Receptioniste() {
		new Receptioniste(default_user_parameter);
	}
	
	public Receptioniste(User user){
		setTitle("Espace Receptionist");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(null);
		setUndecorated(true);
		
		// Home Button
		Button home = new Button();
        home.setBounds(28, 77, 24, 24);
        getContentPane().add(home);
        home.addActionListener(this);
        
        // LogOut Button
        logout = new Button();
        logout.setBounds(28, 130, 24, 24);
        getContentPane().add(logout);
        logout.addActionListener(this);
        
        //Button Gestion de paiement
        Button gest_paiement = new Button();
        gest_paiement.setBounds(643, 185, 82, 68);
        getContentPane().add(gest_paiement);
        gest_paiement.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				new PaiementGui(user);
				dispose();
			}
        });
        
        //Button Gestion de Reservation
        Button gest_reservation = new Button();
        gest_reservation.setBounds(172, 170, 68, 83);
        getContentPane().add(gest_reservation);
        gest_reservation.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				new ReservationGUI(user);
				dispose();
			}
        });
        
        
      //Button Gestion de clients
        Button gest_compte = new Button();
        gest_compte.setBounds(406, 181, 102, 83);
        getContentPane().add(gest_compte);
        gest_compte.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				new CompteGui(user);
				dispose();
			}
        });
        
        //Button : Gestion du compte
        Button paramCompte = new Button();
        paramCompte.setBounds(	709, 9, 54, 32);
        getContentPane().add(paramCompte);
        paramCompte.addActionListener(this);
        
        //Background
        JLabel BackgroundLabel = new JLabel();
        BackgroundLabel.setBounds(0, 0, 800, 500);
        ImageIcon img = new ImageIcon(this.getClass().getResource("/Receptionist_frame.png"));
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

