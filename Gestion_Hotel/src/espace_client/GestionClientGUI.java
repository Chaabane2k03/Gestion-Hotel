package espace_client;

import java.awt.Dimension;
import java.awt.EventQueue;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

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

import espace_Admin.AdminGui;
import guiElements.Button;
import login.Login;
import user.User;

import java.awt.event.ActionEvent;
import java.awt.Font;

public class GestionClientGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	
	JTable jt;
    JPanel tabPanel;
    JScrollPane sp;
    Hashtable<Integer, Integer> ClientDict;
    JComboBox<String> statusComboBox;
    JComboBox<String> typeComboBox;
    JComboBox<String> floorComboBox ;
    JComboBox<String> filtrestatusComboBox;
    JComboBox<String> filtretypeComboBox;

    //Les Boutons :
    JButton resetFiltersButton;
    JButton applyFiltersButton;
    JButton newButton;
    JButton deleteButton;
    JButton resetButton;
    JButton saveButton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionClientGUI frame = new GestionClientGUI();
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

	public GestionClientGUI() {
		new GestionClientGUI(default_user_parameter);
	}
	
	
	
	public GestionClientGUI(User user) {
//UI de fRAME 		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 811, 534);
		getContentPane().setLayout(null);
		//Boutons de nav Bar
		Button home = new Button();
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AdminGui(user);
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
        
        Button aide = new Button();
        aide.setBounds(	28, 468, 23, 20);
        getContentPane().add(aide);
        aide.addActionListener(this); 
        
        //Chamre GUI xD :
        

        tabPanel = new JPanel();
        tabPanel.setSize(680, 330);
        tabPanel.setLocation(100, 60);

        ClientDict = new Hashtable<Integer, Integer>();

        getContentPane().add(tabPanel);
        
        
        // Filtres :
        JLabel filterLabel = new JLabel("Filters :");
        filterLabel.setSize(60, 30);
        filterLabel.setLocation(100, 400);
        filterLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
        getContentPane().add(filterLabel);
        
        
        //Status de la Chambre :
        
        statusComboBox = new JComboBox<String>();
        statusComboBox.setModel(
                new DefaultComboBoxModel<String>(new String[] { "Order By", "Client_Prenom", "Nom_Client" }));
        statusComboBox.setSize(104, 30);
        statusComboBox.setLocation(175, 325);
        
        
        filtrestatusComboBox = new JComboBox<String>();
        filtrestatusComboBox.setModel(
                new DefaultComboBoxModel<String>(new String[] { "Order By", "Client_Prenom", "Nom_Client" }));
        filtrestatusComboBox.setSize(152, 30);
        filtrestatusComboBox.setLocation(175, 400);
        getContentPane().add(filtrestatusComboBox);
        
        
        
        // Type de la Chambre :
        typeComboBox = new JComboBox<String>();
        typeComboBox.setFocusable(false);
        typeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Order Type", "ASC", "DESC" }));
        
        
        filtretypeComboBox = new JComboBox<String>();
        filtretypeComboBox.setFocusable(false);
        filtretypeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Order Type", "ASC", "DESC" }));
        filtretypeComboBox.setSize(152, 30);
        filtretypeComboBox.setLocation(357, 400);
        getContentPane().add(filtretypeComboBox);
        
        
        DrawTable(Client.getClientsFromDB(0, 0));
        
        // add Buttons and call Draw Table Method
        
        resetFiltersButton = new JButton("Reset Filters");
        resetFiltersButton.setLocation(525, 400);
        resetFiltersButton.setSize(120, 30);
        resetFiltersButton.addActionListener(this);
        getContentPane().add(resetFiltersButton);
        
        applyFiltersButton = new JButton("Apply Filters");
        applyFiltersButton.setLocation(666, 400);
        applyFiltersButton.setSize(104, 30);
        applyFiltersButton.addActionListener(this);
        getContentPane().add(applyFiltersButton);


        newButton = new JButton("New");
        newButton.setLocation(200, 450);
        newButton.setSize(104, 30);
        newButton.addActionListener(this);
        getContentPane().add(newButton);

        deleteButton = new JButton("Delete");
        deleteButton.setLocation(325, 450);
        deleteButton.setSize(104, 30);
        deleteButton.addActionListener(this);
        getContentPane().add(deleteButton);

        resetButton = new JButton("Refresh");
        resetButton.setLocation(450, 450);
        resetButton.setSize(104, 30);
        resetButton.addActionListener(this);
        getContentPane().add(resetButton);

        saveButton = new JButton("Save Changes");
        saveButton.setLocation(575, 450);
        saveButton.setSize(104, 30);
        saveButton.addActionListener(this);
        getContentPane().add(saveButton);
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        
        
        
        
	}
	
	
	// à vérifier :
	@Override
	public void actionPerformed(ActionEvent e) {
		//Bouton Refresh :
		if (e.getSource() == resetButton) {
			DrawTable(Client.getClientsFromDB( 0, 0));
		}
		//Bouton Appliquer les filtres :
		
		if (e.getSource() == applyFiltersButton) {
			DrawTable(Client.getClientsFromDB(filtrestatusComboBox.getSelectedIndex(),
					filtretypeComboBox.getSelectedIndex()));
			repaint();
		}
		//Bouton pour supprimer les filtres :
		if (e.getSource() == resetFiltersButton) {
			DrawTable(Client.getClientsFromDB(0, 0));
			
			filtrestatusComboBox.setSelectedIndex(0);
            filtretypeComboBox.setSelectedIndex(0);

            repaint();
			
		}
	
		//Effacer Une chambre :
		if (e.getSource() == deleteButton) {
			DefaultTableModel model = (DefaultTableModel) jt.getModel();
			int j =0;
			while ((j = jt.getSelectedRow()) != -1) {
				String caseJtable = String.valueOf(jt.getValueAt(j, 0));
                if (!caseJtable.isEmpty()) {
                	if (!Client.clientHasReservation(Integer.valueOf(caseJtable))) {
                		if (Client.DeleteClient(Integer.valueOf(caseJtable))) {
                            model.removeRow(j);
                            ClientDict.remove(Integer.valueOf(caseJtable));
                            JOptionPane.showMessageDialog(null, "Client Deleted !",
                                    "Info", JOptionPane.INFORMATION_MESSAGE);
                        } else
                            break;
                	}
                	else {
                		JOptionPane.showMessageDialog(null, "Client has already reservation !! ","Error",JOptionPane.ERROR_MESSAGE);
                		break;
                	}
                    
                } else
                    model.removeRow(j);
                	JOptionPane.showMessageDialog(null, "Client Deleted !",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                	
			}
			
		}
		
		//Bouton new : Il l'affiche seulement mais sans modifications dans la base de données
		if (e.getSource() == newButton) {
			DefaultTableModel model = (DefaultTableModel) jt.getModel();
			model.addRow(new Object [] {"","","","",""});
			
		}
		
		if (e.getSource() == saveButton) {
			HashSet<Integer> numClientSet = new HashSet<Integer>();
            String error = "";
            int i;
            for (i=0;i < jt.getRowCount();i++) {
            	String caseJTable = "";
            	int j;
            	for (j=0;j<6;j++) {
            		caseJTable = String.valueOf(jt.getValueAt(i, j));
            		
            		if (caseJTable.isEmpty()) {
            			error = "Make sure to fill all the fields";
            			break;
            		}
            		else {
            			if(j==0) {
            				int a = Integer.valueOf(caseJTable);
            				if(numClientSet.contains(a)) {
								error = "Repeated room numbers !";
								break;
							}
							else
								numClientSet.add(a);
            			}
            		}
            	}
            		
            
            	

            if (j <6) {
            	JOptionPane.showMessageDialog(null, error,"Error",JOptionPane.ERROR_MESSAGE);
            	break;
            }else {
            	Client client = new Client();
            	client.setIdclient(Integer.valueOf(String.valueOf(jt.getValueAt(i, 0))));
            	client.setNom_client(String.valueOf(String.valueOf(jt.getValueAt(i, 1))));
            	client.setPrenom_client(String.valueOf(String.valueOf(jt.getValueAt(i, 2))));
            	client.setAdresse(String.valueOf(String.valueOf(jt.getValueAt(i, 3))));
            	client.setNum_tel(String.valueOf(String.valueOf(jt.getValueAt(i, 4))));
            	client.setEmail(String.valueOf(String.valueOf(jt.getValueAt(i, 5))));            	
            	
            	Integer hash;
            	if ((hash = ClientDict.get(client.getIdclient())) != null) {
            		if (hash != client.hashCode())
            			Client.updateclientDB(client);
            	}
            	else {
            		Client.NewClient(client);
            		ClientDict.put(client.getIdclient(), client.hashCode());
            	}
            }
		}
        if (i >= jt.getRowCount())
        	JOptionPane.showMessageDialog(null, "Succes !","Info",JOptionPane.INFORMATION_MESSAGE);	
		}
	}
	
	
	//Corrigé 
	private void DrawTable(ArrayList<Client> roomList) {
		String column[] = { "ClientId", "Nom", "Prenom", "adresse", "Contact", "email" };
		Object data[][] = new Object[roomList.size()][6];
		
		for (int j=0 ; j< roomList.size();j++) {
			Client current = roomList.get(j);
			ClientDict.put(current.getIdclient(), current.hashCode());
			data[j][0] = String.valueOf(current.getIdclient());
			data[j][1] = String.valueOf(current.getNom_client());
			data[j][2] = String.valueOf(current.getPrenom_client());
			data[j][3] = String.valueOf(current.getAdresse());
			data[j][4] = String.valueOf(current.getNum_tel());
			data[j][5] = String.valueOf(current.getEmail());
		}
		
		jt = new JTable(new DefaultTableModel(data, column));
		//Pour le Type de Chambre :
		Dimension d = jt.getPreferredSize();
		sp = new JScrollPane(jt);
		sp.setPreferredSize(
				new Dimension(d.width + 225 , jt.getRowHeight()*20+1)
				);
		tabPanel.removeAll();
		tabPanel.add(sp);
		tabPanel.repaint();
		SwingUtilities.updateComponentTreeUI(this);
		
		
		
	}
	
}
