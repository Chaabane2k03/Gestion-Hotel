package espace_reservation;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import chambre.Chambre;
import espace_client.Client;
import espace_client.Client_Gui;
import guiElements.Button;
import login.Login;
import user.User;
import javax.swing.JSpinner;

public class DemandeReservationGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DemandeReservationGUI frame = new DemandeReservationGUI();
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
	private JTextField nom_client;
	private JTextField prenom_client;
	private JTextField souhait_particulier;
	private JButton reserver;
	private JDateChooser check_in;
	private JDateChooser check_out;
	private JSpinner nb_personne;
	private JComboBox<String> type_reservation ;
	private int id_client;
	public DemandeReservationGUI() {
		new DemandeReservationGUI(default_user_parameter);
	}
	
	public DemandeReservationGUI(User user) {
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(null);
		
		
		//Avoir les infos de client :
		id_client = user.getIdclient();
		final Client client = Client.getClientFromUser(id_client);
		System.out.println(client);
		
		//Boutons de nav Bar
		Button home = new Button();
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Client_Gui(user);
				dispose();
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
        
        
        // Les elements de la formulaire :
        
        JLabel lblNom_client = new JLabel("Nom Client");
        lblNom_client.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
        lblNom_client.setBounds(135, 68, 104, 24);
        getContentPane().add(lblNom_client);
        
        JLabel lblPrenomClient = new JLabel("Prenom Client");
        lblPrenomClient.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
        lblPrenomClient.setBounds(431, 68, 119, 24);
        getContentPane().add(lblPrenomClient);
        
        JLabel lblTypeReservation = new JLabel("Type Reservation");
        lblTypeReservation.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
        lblTypeReservation.setBounds(135, 152, 140, 24);
        getContentPane().add(lblTypeReservation);
        
        JLabel lblNombreDesPersonnes = new JLabel("Nombre des personnes");
        lblNombreDesPersonnes.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
        lblNombreDesPersonnes.setBounds(431, 152, 176, 24);
        getContentPane().add(lblNombreDesPersonnes);
        
        JLabel lblCheckInDate = new JLabel("Check in date");
        lblCheckInDate.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
        lblCheckInDate.setBounds(135, 236, 104, 24);
        getContentPane().add(lblCheckInDate);
        
        JLabel lblCheckOutDate = new JLabel("Check out date");
        lblCheckOutDate.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
        lblCheckOutDate.setBounds(431, 236, 140, 24);
        getContentPane().add(lblCheckOutDate);
        
        JLabel lblSouhaitParticulier = new JLabel("Souhait Particulier");
        lblSouhaitParticulier.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
        lblSouhaitParticulier.setBounds(135, 316, 176, 24);
        getContentPane().add(lblSouhaitParticulier);
        
        nom_client = new JTextField();
        nom_client.setEditable(false);
        nom_client.setText(client.getNom_client());
        nom_client.setBounds(135, 103, 215, 31);
        getContentPane().add(nom_client);
        nom_client.setColumns(10);
        
        prenom_client = new JTextField();
        prenom_client.setEditable(false);
        prenom_client.setText(client.getPrenom_client());
        prenom_client.setColumns(10);
        prenom_client.setBounds(431, 103, 215, 31);
        getContentPane().add(prenom_client);
        
        souhait_particulier = new JTextField();
        souhait_particulier.setBounds(135, 350, 511, 31);
        getContentPane().add(souhait_particulier);
        souhait_particulier.setColumns(10);
        
        
        
        
        type_reservation = new JComboBox<String>();
        type_reservation.setModel(new DefaultComboBoxModel<String>(new String[] { "Ne pas spécifier","Normal", "VIP"}));
        type_reservation.setBounds(135, 186, 215, 31);
        getContentPane().add(type_reservation);
        
        check_in = new JDateChooser();
        check_in.setBounds(135, 270, 215, 31);
        getContentPane().add(check_in);
        
        check_out = new JDateChooser();
        check_out.setBounds(431, 270, 215, 31);
        getContentPane().add(check_out);
        
        
      //Boutton : 
        reserver = new JButton("Reserver");
        reserver.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String error ="";
    			int nbPersonne = (int) nb_personne.getValue();
    			Date checkIn = check_in.getDate();
    			Date checkOut = check_out.getDate();
    			String souhaitParticulier = souhait_particulier.getText();
    			
    			/*d = dateChooser.getDate();                      // Date selected by user
    			sdf = SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  // Or whatever format you need
    			s = sdf.format(d);*/
    			
    			if ( checkIn == null || checkOut == null) {
    				error = "Veuillez remplir tous les champs";
    				JOptionPane.showMessageDialog(null, error, "Error",
                            JOptionPane.ERROR_MESSAGE);
    				return ;
    			}
    			
    			if (nbPersonne <= 0) {
    				error = "Le nombre de personne doit être > 0";
    				JOptionPane.showMessageDialog(null, error, "Error",
                            JOptionPane.ERROR_MESSAGE);
    				return ;
    			}
    			Date today = new Date();
    			if (Reservation.comparerDate(checkIn, today) || Reservation.comparerDate(checkOut, checkIn) ) {
    				error = "Entrer Check in et CheckOut Valides";
    				JOptionPane.showMessageDialog(null, error, "Error",
                            JOptionPane.ERROR_MESSAGE);
    				return ;
    			}
    			
    			//Conversion des Dates en chaine de caractères :
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    			String check_in_date = sdf.format(checkIn);
    			String check_out_date = sdf.format(checkOut);
    			String aujourdhui = sdf.format(today);
    			
    			ArrayList <Chambre> liste_dispo = new ArrayList<Chambre> ();
    			
    			liste_dispo = Chambre.getAvailableRoomsFromDB(check_in_date, check_out_date, nbPersonne);
    			
    			
    			if (! liste_dispo.isEmpty()) {
    				Chambre chambre = liste_dispo.get(0);
    				Reservation reservation = new Reservation();    				
    				//Reservation :
    				
    				reservation.setClient(client);
    				reservation.setDate_reservation(aujourdhui);
    				reservation.setTypereservation(1);
    				reservation.setCheck_in_date(check_in_date);
    				reservation.setCheck_out_date(check_out_date);
    				reservation.setSouhait_particulier(souhaitParticulier);
    				reservation.setChambre(chambre);
    				//Ajout de la réservation :
    				Reservation.NewReservation(reservation);
    				chambre.setStatus(2);
    				Chambre.updateChambreDB(chambre);
    			}
    			else {
    				error = "Aucune Chambre est Disponible";
    				JOptionPane.showMessageDialog(null, error, "Error",
                            JOptionPane.ERROR_MESSAGE);
    				return ;
    			}
        	}
        });
        reserver.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
        reserver.setBounds(535, 410, 111, 44);
        
        getContentPane().add(reserver);
        
        nb_personne = new JSpinner();
        nb_personne.setBounds(431, 186, 215, 31);
        getContentPane().add(nb_personne);
       
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
			
			
			//Traitement :
			
			/*
			 *boolean reservable = true;
			for (Chambre element : liste) {
				//Si la chambre est free
	            if (element.getStatus() == 0) {
	            	if (element.getCapacity() >= nbPersonne) {
	            		break;
	            	}
	            }
	            else {
	            	//Si elle est occupé
	            	if (element.getStatus() == 1) {
	            		ArrayList<Reservation> list_res =new ArrayList<Reservation>();
	            		list_res = Chambre.getReservationFromChambre(element);
	            		if (list_res != null) {
	            			
	            			for (Reservation res : list_res) {
	            				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            				Date date = null;
								try {
									date = dateFormat.parse(res.check_out_date);
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
	            				if(Reservation.comparerDate(checkIn, date)) {
	            					reservable = false;
	            					break;
	            				}
	            			}
	            		}
	            		
	            	}
	            	
	            }
	        }
			if(reservable == false) {
            	JOptionPane.showMessageDialog(null, "Aucune Chambre Disponible dans cette période ","Error",JOptionPane.ERROR_MESSAGE);
        	}
    		else {
    			//Reservation.NewReservation(null);
    		} */
			
			
		
			//TODO : Parcourir les chambres et vérifier la capacité , l'état de chambre dans la période de réservation 
			
			//TODO : s'il existe des chambres : on l'ajoute dans la base de données et on imprime une fracture ( calcule le nb de jours Méthode et l'imprime)
		
	}
}
