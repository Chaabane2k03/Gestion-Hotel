package espace_paiement;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import espace_Admin.AdminGui;
import java.util.Hashtable;
import javax.swing.JFrame;
import user.User;
import java.awt.EventQueue;
import login.Login;
import reception.Receptioniste;
import javax.swing.ImageIcon;
import guiElements.Button;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import java.awt.event.ActionEvent;

public class PaiementGui extends JFrame implements ActionListener {
	JTable jt;
	JPanel tabPanel = new JPanel();
	
    JScrollPane sp;
    Hashtable<Integer, Integer> PaiementDict;
    JComboBox<String> statusComboBox;
    JComboBox<String> typeComboBox;
	private static final long serialVersionUID = 1L;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaiementGui frame = new PaiementGui();
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
	public PaiementGui() {
		new PaiementGui(default_user_parameter);
	}
	
	public PaiementGui(User user) {
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
        ImageIcon img = new ImageIcon(this.getClass().getResource("/features_frame.png"));
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        /*------------------------------------------------------------------------*/
        tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.Y_AXIS));
        tabPanel.setBounds(80, 51, 720, 365);
        getContentPane().add(tabPanel);
        JPanel filterPanel = new JPanel();
        filterPanel.setLocation(81, 416);
        filterPanel.setSize(719, 42);
        getContentPane().add(filterPanel);
        filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel filterLabel = new JLabel("Filters :");
        filterPanel.add(filterLabel);

        typeComboBox = new JComboBox<String>();
        typeComboBox.setFocusable(false);
        typeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "In Person", "Online" }));

        JComboBox<String> filterTypeComboBox = new JComboBox<String>();
        filterTypeComboBox.setFocusable(false);
        filterTypeComboBox
                .setModel(new DefaultComboBoxModel<String>(
                        new String[] { "Paiement Type", "In Person", "Online" }));
        filterPanel.add(filterTypeComboBox);

        JComboBox<String> orderComboBox = new JComboBox<String>();
        orderComboBox.setFocusable(false);
        orderComboBox.setModel(
                new DefaultComboBoxModel<String>(
                        new String[] { "Order by", "montant", "date", "surcharge", "typepaiement" }));
        filterPanel.add(orderComboBox);

        JComboBox<String> orderTypeComboBox = new JComboBox<String>();
        orderTypeComboBox.setFocusable(false);
        orderTypeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Ascending", "Descending" }));
        filterPanel.add(orderTypeComboBox);

        DrawTable(Paiement.getpaiementsFromDB(0, 0, 0));

        JButton resetFiltersButton = new JButton("Reset Filters");
        filterPanel.add(resetFiltersButton);

        JButton applyFiltersButton = new JButton("Apply Filters");
        filterPanel.add(applyFiltersButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLocation(81, 458);
        buttonPanel.setSize(719, 42);
        getContentPane().add(buttonPanel);

        JButton newButton = new JButton("New");
        buttonPanel.add(newButton);

        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(deleteButton);

        JButton resetButton = new JButton("Refresh");
        buttonPanel.add(resetButton);

        JButton saveButton = new JButton("Save Changes");
        buttonPanel.add(saveButton);
                        // Background :
                        JLabel BackgroundLabel = new JLabel();
                        BackgroundLabel.setBounds(0, 0, 800, 500);
                        BackgroundLabel.setIcon(img);
                        getContentPane().add(BackgroundLabel);

        resetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DrawTable(Paiement.getpaiementsFromDB(0, 0, 0));
            }

        });
        applyFiltersButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DrawTable(
                        Paiement.getpaiementsFromDB(filterTypeComboBox.getSelectedIndex(),
                                orderComboBox.getSelectedIndex(), orderTypeComboBox.getSelectedIndex()));

                repaint();
            }
        });

        resetFiltersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawTable(Paiement.getpaiementsFromDB(0, 0, 0));

                typeComboBox.setSelectedIndex(0);
                orderComboBox.setSelectedIndex(0);
                orderTypeComboBox.setSelectedIndex(0);

                repaint();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) jt.getModel();
                int j = 0;
                while ((j = jt.getSelectedRow()) != -1) {

                    String caseJtable = String.valueOf(jt.getValueAt(j, 0));
                    if (!caseJtable.isEmpty()) {

                        Paiement.DeletePaiement(Integer.valueOf(caseJtable));
                        model.removeRow(j);
                        PaiementDict.remove(Integer.valueOf(caseJtable));

                    } else
                        model.removeRow(j);

                }
                JOptionPane.showMessageDialog(null, "Payment Deleted !",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        newButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) jt.getModel();
                model.addRow(
                        new Object[] { "", "", "", "", "", "", "", "In Person" });

                TableColumn col1 = jt.getColumnModel().getColumn(7);
                col1.setCellEditor(new DefaultCellEditor(typeComboBox));
            }

        });

        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String error = "";
                int i;
                for (i = 0; i < jt.getRowCount(); i++) {
                    int j;
                    String caseJtable = "";
                    for (j = 1; j < 8; j++) {
                        caseJtable = String.valueOf(jt.getValueAt(i, j));
                        if (j != 2 & j != 3 & j != 7) {
                            if (!caseJtable.isEmpty()) {
                                if (j == 1)
                                    try {
                                        Integer.valueOf(caseJtable);
                                    } catch (Exception e1) {
                                        error = "Reseevation id needs to be integer !";
                                        break;
                                    }
                                if (j == 4 | j == 6)
                                    try {
                                        Double.valueOf(caseJtable);
                                    } catch (Exception e1) {
                                        error = "Montant and surcharge need to be integer !";
                                        break;
                                    }
                            } else {
                                error = "Make sure that there are no empty fields exepect for name and lastname!";
                                break;
                            }
                        }
                    }
                    if (j < 8) {
                        JOptionPane.showMessageDialog(null, error, "Error",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    } else {
                        Paiement paiement = new Paiement();
                        if (!String.valueOf(jt.getValueAt(i, 0)).isEmpty())
                            paiement.setIdpaiement(Integer.valueOf(String.valueOf(jt.getValueAt(i, 0))));
                        paiement.setIdreservation(Integer.valueOf(String.valueOf(jt.getValueAt(i, 1))));
                        Client client = new Client();
                        client.setNom_client(String.valueOf(jt.getValueAt(i, 2)));
                        client.setPrenom_client(String.valueOf(jt.getValueAt(i, 3)));
                        paiement.setClient(client);
                        paiement.setMontant(Double.valueOf(String.valueOf(jt.getValueAt(i, 4))));
                        paiement.setDate(String.valueOf(jt.getValueAt(i, 5)));
                        paiement.setSurcharge(Double.valueOf(String.valueOf(jt.getValueAt(i, 6))));
                        if (String.valueOf(jt.getValueAt(i, 7)).equals("In Person"))
                            paiement.setTypepaiement(1);
                        else
                            paiement.setTypepaiement(2);

                        Integer hash;
                        if ((hash = PaiementDict.get(paiement.getIdpaiement())) != null) {
                            if (hash != paiement.hashCode())
                                Paiement.updatePaiementDB(paiement);
                            PaiementDict.put(paiement.getIdpaiement(), paiement.hashCode());

                        } else {
                            Paiement.NewPaiement(paiement);
                            int id = Paiement.getIdLastInseredPaiement();
                            PaiementDict.put(id, paiement.hashCode());
                            jt.setValueAt(id, i, 0);
                        }

                    }
                }
                if (i >= jt.getRowCount())
                    JOptionPane.showMessageDialog(null, "Succes !",
                            "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        });
	}
/*----------------------------------------------------*/
	private void DrawTable(ArrayList<Paiement> paiementList) {
        String column[] = { "idpaiement", "idreservation", "nom", "prenom", "montant", "date", "surcharge",
                "typepaiement" };
        PaiementDict = new Hashtable<Integer, Integer>();
        Object data[][] = new Object[paiementList.size()][8];
        for (int j = 0; j < paiementList.size(); j++) {
            Paiement current = paiementList.get(j);
            PaiementDict.put(current.getIdpaiement(), current.hashCode());
            data[j][0] = String.valueOf(current.getIdpaiement());
            data[j][1] = String.valueOf(current.getIdreservation());
            data[j][2] = String.valueOf(current.getClient().getNom_client());
            data[j][3] = String.valueOf(current.getClient().getPrenom_client());
            data[j][4] = String.valueOf(current.getMontant());
            data[j][5] = String.valueOf(current.getDate());
            data[j][6] = String.valueOf(current.getSurcharge());

            if (current.getTypepaiement() == 1)
                data[j][7] = "In Person";
            else
                data[j][7] = "Online";

        }

      
        EditibleCells editibleCells = new EditibleCells(paiementList.size(), 8, true);
        editibleCells.setCol(0, false);
        editibleCells.setCol(2, false);
        editibleCells.setCol(3, false);
        MyDefaultTableModel myModel = new MyDefaultTableModel(data, column);
        myModel.setEditable_cells(editibleCells.getEditable_cells());
        jt = new JTable(new DefaultTableModel(data, column));
        jt.setModel(myModel);
        TableColumn col = jt.getColumnModel().getColumn(7);
        col.setCellEditor(new DefaultCellEditor(typeComboBox));

        Dimension d = jt.getPreferredSize();

        sp = new JScrollPane(jt);
        sp.setPreferredSize(
                new Dimension(d.width * 2 + 100, jt.getRowHeight() * 38 + 1));

        tabPanel.removeAll();
        tabPanel.add(sp);
        tabPanel.repaint();
        SwingUtilities.updateComponentTreeUI(this);
    }
/*----------------------------------------------------*/
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
//MRIGEL