package dashbord;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionListener;

import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import espace_Admin.AdminGui;
import espace_client.Client;
import guiElements.Button;
import login.Login;
import user.User;
import TableModel.FilePrintable;
import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import TableModel.EditibleCells;
import TableModel.MyDefaultTableModel;
import chambre.Chambre;


import javax.swing.JTable;
import javax.swing.JFileChooser;


public class Dashbord extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JTable jt;
	JTable jc;
    JPanel tabPanel;
    JPanel tabPanel1;
    JScrollPane sp;
    JScrollPane sc;
    Hashtable<Integer, Integer> ChambreDict;
    
    Button btnExport;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashbord frame = new Dashbord();
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

	public Dashbord() {
		new Dashbord(default_user_parameter);
	}
	public Dashbord(User user) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(null);
		setUndecorated(true);
		
		
		
		
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
        ImageIcon img = new ImageIcon(this.getClass().getResource("/dashbord_frame.png"));
        BackgroundLabel.setIcon(img);
        getContentPane().add(BackgroundLabel);
        
        
        //Pour les chambres :
        
        tabPanel = new JPanel();
        tabPanel.setSize(720, 185);
        tabPanel.setLocation(80, 50);

        getContentPane().add(tabPanel);
        
        //Pour les Clients :
        tabPanel1 = new JPanel();
        tabPanel1.setSize(720, 185);
        tabPanel1.setLocation(80, 235);

        getContentPane().add(tabPanel1);
        
        //Traçage dans la base données :
        DrawTableChambre(Chambre.getChambresPlusDemandesFromDB());
        DrawTableClient(Client.getRegulierClientsFromDB());
        
        // Les Bouttons
        Button btnExport = new Button();
        btnExport.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JFileChooser fileChooser = new JFileChooser(); 
    			fileChooser.setDialogTitle("Specify a file save");
    			int userSelection = fileChooser.showSaveDialog(null);
    			if(userSelection == JFileChooser.APPROVE_OPTION) {
    				File filetoSave = fileChooser.getSelectedFile();
    				
    				
    				//Ecrire dans les fichiers :
    				try {
    					FileWriter fw = new FileWriter(filetoSave);
    					BufferedWriter bw = new BufferedWriter(fw);
    					
    					for (int i = 0; i < jt.getColumnCount(); i++) {
    	                    bw.write(jt.getColumnName(i));
    	                    if (i < jt.getColumnCount() - 1) {
    	                        bw.write(",");
    	                    }
    	                }
    					
    	                bw.newLine();
    	                
    					for(int i = 0; i <jt.getRowCount();i++) {
    						for(int j=0; j<jt.getColumnCount();j++) {
    							bw.write(jt.getValueAt(i, j).toString()+",");
    						}
    						bw.newLine();
    					}
    					JOptionPane.showMessageDialog(null,"LOADED WITH SUCCESS","Success",JOptionPane.INFORMATION_MESSAGE);
    	                bw.close();
    	                fw.close();	
    				}catch(IOException ex) {
    					JOptionPane.showMessageDialog(null,"ERROR","ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
    				}
    			}
        	}
        });
        btnExport.setBounds(303, 443, 138, 34);
        getContentPane().add(btnExport);
       
        
        Button btnImprimer = new Button();
        btnImprimer.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			FileWriter fileWriter = new FileWriter("result.txt");
        			PrintWriter printWriter = new PrintWriter(fileWriter);
        			
        			//Pour l'affichage des chambres :
        			
        			printWriter.println("Chambres les plus demandées :");
        			
        			for (int i = 0; i < jt.getColumnCount(); i++) {
	                    printWriter.print(jt.getColumnName(i));
	                    if (i < jt.getColumnCount() - 1) {
	                    	printWriter.print(",");
	                    }
	                }
					
	                printWriter.println();
	                
					for(int i = 0; i <jt.getRowCount();i++) {
						for(int j=0; j<jt.getColumnCount();j++) {
							printWriter.write(jt.getValueAt(i, j).toString()+"\t");
						}
						printWriter.println();
					}
					
					//Pour l'affichage des Clients :
					printWriter.println("Les Clients les plus Réguliers :");
					
					for (int i = 0; i < jc.getColumnCount(); i++) {
	                    printWriter.print(jc.getColumnName(i));
	                    if (i < jc.getColumnCount() - 1) {
	                    	printWriter.print(",");
	                    }
	                }
					printWriter.println();
					for(int i = 0; i <jc.getRowCount();i++) {
						for(int j=0; j<jc.getColumnCount();j++) {
							printWriter.write(jc.getValueAt(i, j).toString()+"\t");
						}
						printWriter.println();
					}
					 printWriter.close();
					 
					 PrinterJob job = PrinterJob.getPrinterJob();
			            job.setPrintable( new FilePrintable("result.txt"));
			            if (job.printDialog()) {
			                job.print();
			            }
					
        		}catch(Exception ex) {
        			System.err.println("Erreur lors de la création ou de l'impression du fichier: " + ex.getMessage());
        		}
        	}
        });
        btnImprimer.setBounds(470, 443, 110, 34);
        getContentPane().add(btnImprimer);
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
	}

	// Pour les chambres les plus demandées :
	private void DrawTableChambre(ArrayList<Chambre> ChambreList) {
		String column[] = { "numChambre", "Capacite", "TypeChambre", "etage", "Prix_par_jour", "Nombre de demandes" };
        Object data[][] = new Object[ChambreList.size()][6];
        
        
        EditibleCells editibleCells = new EditibleCells(ChambreList.size(), 6, false);
        for (int j = 0; j < ChambreList.size(); j++) {
            Chambre current = ChambreList.get(j);
            data[j][0] = String.valueOf(current.getNumChambre());
            data[j][1] = String.valueOf(current.getCapacity());
            if (current.getTypeChambre() == 1)
                data[j][2] = "Normal";
            else
                data[j][2] = "VIP";
            data[j][3] = String.valueOf(current.getEtage());
            data[j][4] = String.valueOf(current.getPrix_par_jour());
            data[j][5] = String.valueOf(current.getNbDemandes());

        }
        
        MyDefaultTableModel myModel = new MyDefaultTableModel(data, column);
        myModel.setEditable_cells(editibleCells.getEditable_cells());
        jt = new JTable(new DefaultTableModel(data, column));
        jt.setModel(myModel);
        
        
        Dimension d = jt.getPreferredSize();
        
        sp = new JScrollPane(jt);
        sp.setPreferredSize(
                new Dimension(d.width + 220, jt.getRowHeight() * 6 + 50));

        tabPanel.removeAll();
        tabPanel.add(sp);
        tabPanel.repaint();
        SwingUtilities.updateComponentTreeUI(this);
        
        
	}
	
	//Pour les Clients Réguliers
	private void DrawTableClient(ArrayList<Client> clientList) {
        String column[] = { "idclient", "nom_client", "prenom_client", "Nombre de reservations" };
        Object data[][] = new Object[clientList.size()][4];

        EditibleCells editibleCells = new EditibleCells(clientList.size(), 4, false);
        for (int j = 0; j < clientList.size(); j++) {
            Client current = clientList.get(j);
            data[j][0] = String.valueOf(current.getIdclient());
            data[j][1] = String.valueOf(current.getNom_client());
            data[j][2] = String.valueOf(current.getPrenom_client());
            data[j][3] = String.valueOf(current.getNbReservation());
        }

        MyDefaultTableModel myModel = new MyDefaultTableModel(data, column);
        myModel.setEditable_cells(editibleCells.getEditable_cells());
        jc = new JTable(new DefaultTableModel(data, column));
        jc.setModel(myModel);

        Dimension d = jc.getPreferredSize();

        sc = new JScrollPane(jc);
        sc.setPreferredSize(
                new Dimension(d.width * 2 + 70, jc.getRowHeight() * 6 + 50));

        tabPanel1.removeAll();
        tabPanel1.add(sc);
        tabPanel1.repaint();
        SwingUtilities.updateComponentTreeUI(this);
    }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
