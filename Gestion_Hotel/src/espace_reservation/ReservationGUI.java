package espace_reservation;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import espace_client.Client;
import TableModel.EditibleCells;
import TableModel.MyDefaultTableModel;
import chambre.Chambre;
import espace_Admin.AdminGui;
import guiElements.Button;
import login.Login;
import reception.Receptioniste;
import user.User;

import java.awt.event.ActionEvent;
import javax.swing.JSpinner;

public class ReservationGUI extends JFrame implements ActionListener {
	 	JTable jt;
	    JPanel tabPanel;
	    JScrollPane sp;
	    Hashtable<Integer, Integer> ReservationDict;
	    JComboBox<String> statusComboBox;
	    JComboBox<String> typeComboBox;
	    JComboBox<String> filterTypeComboBox;
	    JComboBox<String> orderComboBox;
	    JComboBox<String> orderTypeComboBox;
	    JComboBox<String> floorComboBox ;
	    JComboBox<String> filtrestatusComboBox;
	    JComboBox<String> filtretypeComboBox;
	    JComboBox<String> filtrefloorComboBox;
	    JSpinner spinner;
	    
	    
	    
	    JButton resetFiltersButton;
	    JButton applyFiltersButton;
	    JButton newButton;
	    JButton deleteButton;
	    JButton resetButton;
	    JButton saveButton;
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
    
        JLabel BackgroundLabel = new JLabel();
        BackgroundLabel.setBounds(0, 0, 800, 500);
        ImageIcon img = new ImageIcon(this.getClass().getResource("/features_frame.png"));
        BackgroundLabel.setIcon(img);
        getContentPane().add(BackgroundLabel);
        
        Button aide = new Button();
        aide.setBounds(	28, 468, 23, 20);
        getContentPane().add(aide);
        aide.addActionListener(this); 
        
        
        tabPanel = new JPanel();
        tabPanel.setSize(680, 169);
        tabPanel.setLocation(100, 60);
        getContentPane().add(tabPanel);

        chamPanel = new JPanel();
        chamPanel.setSize(680, 135);
        chamPanel.setLocation(100, 234);
        getContentPane().add(chamPanel);

        JLabel filterLabel = new JLabel("Filters :");
        filterLabel.setSize(60, 30);
        filterLabel.setLocation(100, 430);
        filterLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
        getContentPane().add(filterLabel);
        
        
        
        statusComboBox = new JComboBox<String>();
        statusComboBox.setModel(
                new DefaultComboBoxModel<String>(new String[] { "Reg Type", "In Person", "Online"  }));
        statusComboBox.setSize(104, 30);
        statusComboBox.setLocation(175, 355);
        
        
        filtrestatusComboBox = new JComboBox<String>();
        filtrestatusComboBox.setModel(
                new DefaultComboBoxModel<String>(new String[] { "Reg Type", "In Person", "Online" }));
        filtrestatusComboBox.setSize(104, 30);
        filtrestatusComboBox.setLocation(175, 430);
        getContentPane().add(filtrestatusComboBox);
        
        
        floorComboBox = new JComboBox<String>();
        floorComboBox.setFocusable(false);
        floorComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Order by", "date_reservation", "typereservation", "check_in_date", "check_out_date" }));
        floorComboBox.setSize(104, 30);
        floorComboBox.setLocation(290, 325);
        
        
        filtrefloorComboBox = new JComboBox<String>();
        filtrefloorComboBox.setFocusable(false);
        filtrefloorComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Order by", "date_reservation", "typereservation", "check_in_date", "check_out_date"}));
        filtrefloorComboBox.setSize(104, 30);
        filtrefloorComboBox.setLocation(290, 430);
        getContentPane().add(filtrefloorComboBox);
        
        
        typeComboBox = new JComboBox<String>();
        typeComboBox.setFocusable(false);
        typeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {  "Ascending", "Descending"  }));
        
        
        filtretypeComboBox = new JComboBox<String>();
        filtretypeComboBox.setFocusable(false);
        filtretypeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {  "Ascending", "Descending"  }));
        filtretypeComboBox.setSize(104, 30);
        filtretypeComboBox.setLocation(405, 430);
        getContentPane().add(filtretypeComboBox);
        
        DrawChambreTable(Chambre.getRoomsFromDB(0, 0, 0));
        DrawTable(Reservation.getreservationsFromDB(0, 0, 0));
        
        // add Buttons and call Draw Table Method
        
        resetFiltersButton = new JButton("Reset Filters");
        resetFiltersButton.setLocation(525, 430);
        resetFiltersButton.setSize(120, 30);
        resetFiltersButton.addActionListener(this);
        getContentPane().add(resetFiltersButton);
        
        applyFiltersButton = new JButton("Apply Filters");
        applyFiltersButton.setLocation(666, 430);
        applyFiltersButton.setSize(104, 30);
        applyFiltersButton.addActionListener(this);
        getContentPane().add(applyFiltersButton);


        newButton = new JButton("New");
        newButton.setLocation(200, 468);
        newButton.setSize(104, 30);
        newButton.addActionListener(this);
        getContentPane().add(newButton);

        deleteButton = new JButton("Delete");
        deleteButton.setLocation(325, 468);
        deleteButton.setSize(104, 30);
        deleteButton.addActionListener(this);
        getContentPane().add(deleteButton);

        resetButton = new JButton("Refresh");
        resetButton.setLocation(450, 468);
        resetButton.setSize(104, 30);
        resetButton.addActionListener(this);
        getContentPane().add(resetButton);

        saveButton = new JButton("Save Changes");
        saveButton.setLocation(575, 468);
        saveButton.setSize(104, 30);
        saveButton.addActionListener(this);
        getContentPane().add(saveButton);
        
        JLabel lblIdChambre = new JLabel("Id Chambre:");
        lblIdChambre.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
        lblIdChambre.setBounds(260, 390, 114, 30);
        getContentPane().add(lblIdChambre);
        
        spinner = new JSpinner();
        spinner.setBounds(384, 390, 170, 30);
        getContentPane().add(spinner);
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
      
	}
	
	
	//Les Variables :
    Hashtable<Integer, Integer> chambreDict = new Hashtable<Integer , Integer>();
    JPanel chamPanel;
    JTable jc;
    JScrollPane cp;
	private void DrawChambreTable(ArrayList<Chambre> roomList) {
		String column[] = { "Num Room", "Capacity", "Type", "Status", "Floor", "PricePerDay" };
		Object data[][] = new Object[roomList.size()][6];
		
		for (int j=0 ; j< roomList.size();j++) {
			Chambre current = roomList.get(j);
			chambreDict.put(current.getNumChambre(), current.hashCode());
			//Numero de chambre
			data[j][0] = String.valueOf(current.getNumChambre());
			//Capacité de chambre
			data[j][1] = String.valueOf(current.getCapacity());
			// Type de Chambre :
			if (current.getTypeChambre() == 1) {
				data[j][2] = "Normal";
			}
			else {
				data[j][2] = "Vip";
			}
			// Statut de Chambre :
			if(current.getStatus() == 1)
				data[j][3] = "Free";
			else if (current.getStatus() == 2)
				data[j][3] = "Occupied";
			else 
				data[j][3] = "Renovating";
			// Etage :
			data[j][4] = String.valueOf(current.getEtage());
			// Prix Par Jour
			data[j][5] = String.valueOf(current.getPrix_par_jour());
		}
		//Sans Modification :
		EditibleCells editibleCells = new EditibleCells(roomList.size(), 6, true);
        editibleCells.setCol(0, false);
        editibleCells.setCol(1, false);
        editibleCells.setCol(2, false);
        editibleCells.setCol(3, false);
        editibleCells.setCol(4, false);
        editibleCells.setCol(5, false);
        
        MyDefaultTableModel myModel = new MyDefaultTableModel(data, column);
        myModel.setEditable_cells(editibleCells.getEditable_cells());
		jc = new JTable(new DefaultTableModel(data, column));
		jc.setModel(myModel);
		
		//Pour le Type de Chambre :
		
		
		
		Dimension d = jc.getPreferredSize();
		cp = new JScrollPane(jc);
		cp.setPreferredSize(
				new Dimension(d.width + 225 , jc.getRowHeight()*20+1)
				);
		chamPanel.removeAll();
		chamPanel.add(cp);
		chamPanel.repaint();
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	
    private void DrawTable(ArrayList<Reservation> reservationList) {
        String column[] = { "idreservation", "id_client", "nom_client", "prenom_client", "date_res",
                "type", "check_in", "check_out", "souhait" ,"idChambe"};
        ReservationDict = new Hashtable<Integer, Integer>();
        Object data[][] = new Object[reservationList.size()][10];
        for (int j = 0; j < reservationList.size(); j++) {
            Reservation current = reservationList.get(j);
            ReservationDict.put(current.getIdreservation(), current.hashCode());
            data[j][0] = String.valueOf(current.getIdreservation());
            data[j][1] = String.valueOf(current.getClient().getIdclient());
            data[j][2] = String.valueOf(current.getClient().getNom_client());
            data[j][3] = String.valueOf(current.getClient().getPrenom_client());
            data[j][4] = String.valueOf(current.getDate_reservation());

            if (current.getTypereservation() == 1)
                data[j][5] = "In Person";
            else
                data[j][5] = "Online";

            data[j][6] = String.valueOf(current.getCheck_in_date());
            data[j][7] = String.valueOf(current.getCheck_out_date());
            data[j][8] = String.valueOf(current.getSouhait_particulier());
            data[j][9] = String.valueOf(current.getChambre().getNumChambre());

        }

        EditibleCells editibleCells = new EditibleCells(reservationList.size(), 10, true);
        editibleCells.setCol(0, false);
        editibleCells.setCol(2, false);
        editibleCells.setCol(3, false);
        editibleCells.setCol(9, false);
        MyDefaultTableModel myModel = new MyDefaultTableModel(data, column);
        myModel.setEditable_cells(editibleCells.getEditable_cells());
        jt = new JTable(new DefaultTableModel(data, column));
        jt.setModel(myModel);
        TableColumn col = jt.getColumnModel().getColumn(5);
        col.setCellEditor(new DefaultCellEditor(typeComboBox));

        Dimension d = jt.getPreferredSize();

        sp = new JScrollPane(jt);
        sp.setPreferredSize(
                new Dimension(d.width , jt.getRowHeight() * 38 + 1));

        tabPanel.removeAll();
        tabPanel.add(sp);
        tabPanel.repaint();
        SwingUtilities.updateComponentTreeUI(this);
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		//Bouton Refresh :
				if (e.getSource() == resetButton) {
	                DrawTable(Reservation.getreservationsFromDB(0, 0, 0));
	                DrawChambreTable(Chambre.getRoomsFromDB(0, 0, 0));
				}
		
				if (e.getSource() == applyFiltersButton) {
					 DrawTable(
		                        Reservation.getreservationsFromDB(filterTypeComboBox.getSelectedIndex(),
		                                orderComboBox.getSelectedIndex(), orderTypeComboBox.getSelectedIndex()));

		                repaint();
				}
				if (e.getSource() == resetFiltersButton) {
				      DrawTable(Reservation.getreservationsFromDB(0, 0, 0));
		               typeComboBox.setSelectedIndex(0);
		               orderComboBox.setSelectedIndex(0);
		               orderTypeComboBox.setSelectedIndex(0);

		                repaint();
					
				}
				if(e.getSource() == deleteButton) {
					   DefaultTableModel model = (DefaultTableModel) jt.getModel();
		                int j = 0;
		                while ((j = jt.getSelectedRow()) != -1) {
		                    String caseJtable = String.valueOf(jt.getValueAt(j, 0));
		                    if (!caseJtable.isEmpty()) {
		                    	int chambre_id = Integer.valueOf(String.valueOf(jt.getValueAt(j,9)));
		                    	Chambre chambre = Chambre.getRoomFromId(chambre_id);
		                    	chambre.setStatus(1);
		                    	Chambre.updateChambreDB(chambre);
		                    	
		                        Reservation.DeleteReservation(Integer.valueOf(caseJtable));
		                        model.removeRow(j);
		                        ReservationDict.remove(Integer.valueOf(caseJtable));

		                    } else
		                        model.removeRow(j);

		                }
		                JOptionPane.showMessageDialog(null, "Reservation Deleted !",
		                        "Info", JOptionPane.INFORMATION_MESSAGE);
				}
				if (e.getSource() == newButton) {
					  DefaultTableModel model = (DefaultTableModel) jt.getModel();
		                model.addRow(
		                        new Object[] { "", "", "", "", "2024-05-07", "In Person", "2024-05-07", "2024-05-07", "" });

		                TableColumn col1 = jt.getColumnModel().getColumn(5);
		                col1.setCellEditor(new DefaultCellEditor(statusComboBox));
				}
				
				
				//MODIFICATIONS MAJEURES :
				
				if (e.getSource() == saveButton) {
					
					
					String error = "";
					
	                int i;
	                for (i = 0; i < jt.getRowCount(); i++) {
	                    int j;
	                    String caseJtable = "";
	                    for (j = 1; j < 8; j++) {
	                        caseJtable = String.valueOf(jt.getValueAt(i, j));
	                        if (j != 5 & j != 2 & j != 3) {
	                            if (!caseJtable.isEmpty()) {
	                                System.out.println(Integer.toString(i) + j + caseJtable);
	                                if (j == 1)
	                                    try {
	                                        Integer.valueOf(caseJtable);
	                                    } catch (Exception e1) {
	                                        error = "Client id needs to be integer !";
	                                        break;
	                                    }
	                            } else {
	                                error = "Make sure that the dates and the client id are filled in!";
	                                break;
	                            }
	                        }
	                    }
	                    if (j < 8) {
	                        JOptionPane.showMessageDialog(null, error, "Error",
	                                JOptionPane.ERROR_MESSAGE);
	                        break;
	                    } else {
	                    	// Initialisation de Reservation :
	                        Reservation reservation = new Reservation();
	                        if (!String.valueOf(jt.getValueAt(i, 0)).isEmpty())
	                            reservation.setIdreservation(Integer.valueOf(String.valueOf(jt.getValueAt(i, 0))));
	                        Client client = new Client();
	                        client.setIdclient(Integer.valueOf(String.valueOf(jt.getValueAt(i, 1))));
	                        client.setNom_client(String.valueOf(jt.getValueAt(i, 2)));
	                        client.setPrenom_client(String.valueOf(jt.getValueAt(i, 3)));
	                        reservation.setClient(client);
	                        reservation.setDate_reservation(String.valueOf(jt.getValueAt(i, 4)));
	                        if (String.valueOf(jt.getValueAt(i, 5)).equals("In Person"))
	                            reservation.setTypereservation(1);
	                        else
	                            reservation.setTypereservation(2);

	                        reservation.setCheck_in_date(String.valueOf(jt.getValueAt(i, 6)));
	                        reservation.setCheck_out_date(String.valueOf(jt.getValueAt(i, 7)));
	                        reservation.setSouhait_particulier(String.valueOf(jt.getValueAt(i, 8)));
	                        if (jt.getValueAt(i, 9) != null) {
	                        	reservation.setChambre(Chambre.getRoomFromId((Integer.valueOf(String.valueOf(jt.getValueAt(i, 9))))));
	                        }
	                        //Vérification s'il y a des chambres disponibles :
	                        //Soit free , soit Occupé :
	                        
	                        Integer hash;
	                        if ((hash = ReservationDict.get(reservation.getIdreservation())) != null) {
	                            if (hash != reservation.hashCode()) {	                            	
	                            	Reservation.updateReservationDB(reservation);
	                            	ReservationDict.put(reservation.getIdreservation(), reservation.hashCode());
	                            }
	                                

	                        } else {
	                        	int id_chambre = (int) spinner.getValue();
	        					if (id_chambre <= 0) {
	        						error="Donner un Id Valide";
	        						JOptionPane.showMessageDialog(null, error, "Error",
	                                    JOptionPane.ERROR_MESSAGE);
	        						return;
	        					}
	        					//Vérifier que l'id existe dans la base de données du Chambre :
	        					Chambre chambreAreserver = new Chambre();
	        					chambreAreserver = Chambre.getRoomFromId(id_chambre);
	        					if (chambreAreserver != null) {
	        						//Vérifier que la chambre est disponible :)
	        						ArrayList<Chambre> liste_dispo = new ArrayList<Chambre>();
	        						liste_dispo = Chambre.getAvailableRoomsFromDB(reservation.check_out_date, reservation.check_in_date, 1);
	        						
	        						
	        						if (liste_dispo != null && liste_dispo.contains(chambreAreserver)) {
	        							chambreAreserver.setStatus(2);
	        							Chambre.updateChambreDB(chambreAreserver);
	        							reservation.setChambre(chambreAreserver);
	        							Reservation.NewReservation(reservation);
	    	                            ReservationDict.put(Reservation.getIdLastInseredReservation(), reservation.hashCode());
	    	                            //TODO : Ajouter un paiement 
	        						}
	        						else {
	        							error="Aucune Chambre Disponible";
		        						JOptionPane.showMessageDialog(null, error, "Error",
		                                    JOptionPane.ERROR_MESSAGE);
		        						return;
	        						}
	        						
	        					}
	        					
	        					//A Modidier reservation  :
	                            /*
	                       */ }

	                    }
	                }
	                if (i >= jt.getRowCount())
	                    JOptionPane.showMessageDialog(null, "Succes !",
	                            "Info", JOptionPane.INFORMATION_MESSAGE);
				}
				
				
		
	}
}
