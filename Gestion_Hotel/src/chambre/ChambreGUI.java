package chambre;

import java.awt.Dimension;
import java.awt.EventQueue;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
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

import espace_Admin.AdminGui;
import guiElements.Button;
import login.Login;
import user.User;

import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.UIManager;

public class ChambreGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	
	JTable jt;
    JPanel tabPanel;
    JScrollPane sp;
    Hashtable<Integer, Integer> chambreDict;
    JComboBox<String> statusComboBox;
    JComboBox<String> typeComboBox;
    JComboBox<String> floorComboBox ;
    JComboBox<String> filtrestatusComboBox;
    JComboBox<String> filtretypeComboBox;
    
    
    JComboBox<String> filtrefloorComboBox;

    //Les Boutons :
    Button resetFiltersButton;
    Button applyFiltersButton;
    Button newButton;
    Button deleteButton;
    Button resetButton;
    Button saveButton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChambreGUI frame = new ChambreGUI();
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

	public ChambreGUI() {
		new ChambreGUI(default_user_parameter);
	}
	
	
	
	public ChambreGUI(User user) {
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
        ImageIcon img = new ImageIcon(this.getClass().getResource("/Chambre.png")); //Juste modifier le nom -> manque des boutons
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

        chambreDict = new Hashtable<Integer, Integer>();

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
                new DefaultComboBoxModel<String>(new String[] { "Status", "Free", "Occupied", "Renovating" }));
        statusComboBox.setSize(104, 30);
        statusComboBox.setLocation(175, 325);
        
        
        filtrestatusComboBox = new JComboBox<String>();
        filtrestatusComboBox.setModel(
                new DefaultComboBoxModel<String>(new String[] { "Status", "Free", "Occupied", "Renovating" }));
        filtrestatusComboBox.setSize(104, 30);
        filtrestatusComboBox.setLocation(175, 400);
        getContentPane().add(filtrestatusComboBox);
        
        // Etage de les chamebres
        
        floorComboBox = new JComboBox<String>();
        floorComboBox.setFocusable(false);
        floorComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Floor", "1", "2", "3" }));
        floorComboBox.setSize(104, 30);
        floorComboBox.setLocation(290, 325);
        
        
        filtrefloorComboBox = new JComboBox<String>();
        filtrefloorComboBox.setFocusable(false);
        filtrefloorComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Floor", "1", "2", "3" }));
        filtrefloorComboBox.setSize(104, 30);
        filtrefloorComboBox.setLocation(290, 400);
        getContentPane().add(filtrefloorComboBox);
        
        
        // Type de la Chambre :
        typeComboBox = new JComboBox<String>();
        typeComboBox.setFocusable(false);
        typeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Type", "Normal", "VIP" }));
        
        
        filtretypeComboBox = new JComboBox<String>();
        filtretypeComboBox.setFocusable(false);
        filtretypeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Type", "Normal", "VIP" }));
        filtretypeComboBox.setSize(104, 30);
        filtretypeComboBox.setLocation(405, 400);
        getContentPane().add(filtretypeComboBox);
        
        
        DrawTable(Chambre.getRoomsFromDB(0, 0, 0));
        
        // add Buttons and call Draw Table Method
        
        resetFiltersButton = new Button();
        resetFiltersButton.setLocation(520, 400);
        resetFiltersButton.setSize(115, 35);
        resetFiltersButton.addActionListener(this);
        getContentPane().add(resetFiltersButton);
        
        applyFiltersButton = new Button();
        applyFiltersButton.setLocation(650, 400);
        applyFiltersButton.setSize(115, 35);
        applyFiltersButton.addActionListener(this);
        getContentPane().add(applyFiltersButton);


        newButton = new Button();
        newButton.setLocation(205, 455);
        newButton.setSize(104, 35);
        newButton.addActionListener(this);
        getContentPane().add(newButton);

        deleteButton = new Button();
        deleteButton.setLocation(335, 455);
        deleteButton.setSize(105, 35);
        deleteButton.addActionListener(this);
        getContentPane().add(deleteButton);

        resetButton = new Button();
        resetButton.setLocation(469, 455);
        resetButton.setSize(104, 35);
        resetButton.addActionListener(this);
        getContentPane().add(resetButton);

        saveButton = new Button();
        saveButton.setLocation(590, 455);
        saveButton.setSize(120, 35);
        saveButton.addActionListener(this);
        getContentPane().add(saveButton);
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        
        
        
        
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Bouton Refresh :
		if (e.getSource() == resetButton) {
			DrawTable(Chambre.getRoomsFromDB(0, 0, 0));
		}
		//Bouton Appliquer les filtres :
		
		//TODO : Verifier cette méthode 
		if (e.getSource() == applyFiltersButton) {
			DrawTable(Chambre.getRoomsFromDB(filtrestatusComboBox.getSelectedIndex(),
					filtrefloorComboBox.getSelectedIndex(), 
					filtretypeComboBox.getSelectedIndex()));
			repaint();
		}
		//Bouton pour supprimer les filtres :
		if (e.getSource() == resetFiltersButton) {
			DrawTable(Chambre.getRoomsFromDB(0, 0, 0));
			
			filtrestatusComboBox.setSelectedIndex(0);
            filtrefloorComboBox.setSelectedIndex(0);
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
                	if(Chambre.hasReservations(Integer.valueOf(caseJtable))) {
                		JOptionPane.showMessageDialog(null, "Chambre déja réservé","Error",JOptionPane.ERROR_MESSAGE);
                    	break;
                	}
                    if (Chambre.DeleteChambre(Integer.valueOf(caseJtable))) {
                        model.removeRow(j);
                        chambreDict.remove(Integer.valueOf(caseJtable));

                    } else
                        break;
                } else
                    model.removeRow(j);
			}
			JOptionPane.showMessageDialog(null, "Room Deleted !",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
		}
		
		//Bouton new : Il l'affiche seulement mais sans modifications dans la base de données
		if (e.getSource() == newButton) {
			DefaultTableModel model = (DefaultTableModel) jt.getModel();
			model.addRow(new Object [] {"","","Type","Status",""});
			TableColumn col = jt.getColumnModel().getColumn(2);
            col.setCellEditor(new DefaultCellEditor(typeComboBox));
            TableColumn col1 = jt.getColumnModel().getColumn(3);
            col1.setCellEditor(new DefaultCellEditor(statusComboBox));
		}
		
		if (e.getSource() == saveButton) {
			HashSet<Integer> numChambreSet = new HashSet<Integer>();
            String error = "";
            int i;
            for (i=0;i < jt.getRowCount();i++) {
            	String caseJTable = "";
            	int j;
            	for (j=0;j<6;j++) {
            		caseJTable = String.valueOf(jt.getValueAt(i, j));
            		if (j != 2 & j != 3) {
            			if(!caseJTable.isEmpty()) {
            				if (j != 5) {
            					try {
            						int a = Integer.valueOf(caseJTable);
            						if (j == 0)
            							if(numChambreSet.contains(a)) {
            								error = "Repeated room numbers !";
            								break;
            							}
            							else
            								numChambreSet.add(a);
            					}catch (Exception e1) {
            						error = "Floor, Capacity and Room number need to be integers ! ";
            						break;
            					}
            				}else {
            					try {
            						@SuppressWarnings("unused")
									Double a = Double.valueOf(caseJTable);
            						
            					}catch(Exception e1){
            						error = "Price should be Double";
            						break;
            					}
            				}
            				
            	}else {
            		error = "Make sure there are no empty values !";
            		break;
            	}
            		
            }else if(caseJTable.equals("Type") | caseJTable.equals("Status")) {
            	error = "Make sure that you change the room type and status";
            	break;
            }
            	
            	
            	
            }
            if (j <6) {
            	JOptionPane.showMessageDialog(null, error,"Error",JOptionPane.ERROR_MESSAGE);
            	break;
            }else {
            	Chambre chambre = new Chambre();
            	chambre.setNumChambre(Integer.valueOf(String.valueOf(jt.getValueAt(i, 0))));
            	chambre.setCapacity(Integer.valueOf(String.valueOf(jt.getValueAt(i, 1))));
            	
            	if (String.valueOf(jt.getValueAt(i, 2)).equals("Normal")) {
            		chambre.setTypeChambre(1);
            	}
            	else
            		chambre.setTypeChambre(2);
            	
            	if (String.valueOf(jt.getValueAt(i, 3)).equals("Free"))
            		chambre.setStatus(1);
            	else if (String.valueOf(jt.getValueAt(i, 3)).equals("Occupied"))
            		chambre.setStatus(2);
            	else
            		chambre.setStatus(3);
            	
            	chambre.setEtage(Integer.valueOf(String.valueOf(jt.getValueAt(i, 4))));
            	chambre.setPrix_par_jour(Double.valueOf(String.valueOf(jt.getValueAt(i, 5))));
            	
            	Integer hash;
            	if ((hash = chambreDict.get(chambre.getNumChambre())) != null) {
            		if (hash != chambre.hashCode())
            			Chambre.updateChambreDB(chambre);
            	}
            	else {
            		Chambre.NewChambre(chambre);
            		chambreDict.put(chambre.getNumChambre(), chambre.hashCode());
            	}
            }
		}
        if (i >= jt.getRowCount())
        	JOptionPane.showMessageDialog(null, "Succes !","Info",JOptionPane.INFORMATION_MESSAGE);	
		}
	}
	
	private void DrawTable(ArrayList<Chambre> roomList) {
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
		
		jt = new JTable(new DefaultTableModel(data, column));
		jt.setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		//Pour le Type de Chambre :
		TableColumn col = jt.getColumnModel().getColumn(2);
		col.setCellEditor(new DefaultCellEditor(typeComboBox));
		
		//Pour le statut des Chambres :
		TableColumn col1 = jt.getColumnModel().getColumn(3);
		col1.setCellEditor(new DefaultCellEditor(statusComboBox));
		
		
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
