package login;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Random;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;

public class SignUp extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6486589998830171874L;
	
	  private String hashPassword(String password) {
	        try {
	           
	            MessageDigest md = MessageDigest.getInstance("MD5");
	          
	            md.update(password.getBytes());
	          
	            byte[] bytes = md.digest();
	            StringBuilder sb = new StringBuilder();
	            for (byte b : bytes) {
	                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
	            }
	            return sb.toString();
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	    }
	public SignUp() {
        initComponents();
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jTextField3 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
Random rand = new Random();
jButton1.addActionListener(new ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent e) {
        String firstName = jTextField3.getText();
        String lastName = jTextField1.getText();
        String emailId = jTextField6.getText();
        String userName = jTextField8.getText();
        String mobileNumber = jTextField9.getText();
        String adrs = jTextField5.getText();
        String typeText = jTextField7.getText();
        int type = 2; // Default value
        if (!typeText.isEmpty()) {
            type = Integer.parseInt(typeText);
        }
      
        String password = new String(jPasswordField1.getPassword());
        String msg = "" + firstName;
        Object btnNewButton = null;
       
        String hashedPassword = hashPassword(password);
        if (jTextField1.getText().isEmpty() || jTextField3.getText().isEmpty() || jTextField6.getText().isEmpty() ||
                jTextField5.getText().isEmpty() || jTextField6.getText().isEmpty() || jTextField8.getText().isEmpty() ||
                jTextField9.getText().isEmpty() || jPasswordField1.getPassword().length == 0) {
            Component temporaryLostComponent = null;
			JOptionPane.showMessageDialog(temporaryLostComponent, this, "Please fill in all the required fields.", type);
            return; // Exit the method if any field is empty
        }
        PreparedStatement insertUserStatement = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
            // Check if the client exists or create a new one
            int idClient = rand.nextInt(1000);
            String checkClientQuery = "SELECT idclient FROM client WHERE email = ?";
            PreparedStatement checkClientStatement = connection.prepareStatement(checkClientQuery);
            checkClientStatement.setString(1, emailId);
            ResultSet resultSet = checkClientStatement.executeQuery();
            if (resultSet.next()) {
                idClient = resultSet.getInt("idclient");
            } else {
                // Create a new client
                String insertClientQuery = "INSERT INTO client (nom_client, prenom_client, adresse, num_tel, email) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertClientStatement = connection.prepareStatement(insertClientQuery, Statement.RETURN_GENERATED_KEYS);
                insertClientStatement.setString(1, lastName);
                insertClientStatement.setString(2, firstName);
                insertClientStatement.setString(3, adrs);
                insertClientStatement.setString(4, mobileNumber);
                insertClientStatement.setString(5, emailId);
                insertClientStatement.executeUpdate();
                // Get the auto-generated ID of the newly inserted client
                ResultSet generatedKeys = insertClientStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idClient = generatedKeys.getInt(1);
                }
                insertClientStatement.close();
            }

            // Create user
            String insertUserQuery = "INSERT INTO user (username, password, idclient, typeuser) VALUES (?, ?, ?, ?)";
            insertUserStatement = connection.prepareStatement(insertUserQuery);
            insertUserStatement.setString(1, userName);
            insertUserStatement.setString(2, hashedPassword);
            insertUserStatement.setInt(3, idClient);
            insertUserStatement.setInt(4, type);
            insertUserStatement.executeUpdate();

            JOptionPane.showMessageDialog((Component) btnNewButton, "Welcome, " + msg + ". Your account is successfully created");
            connection.close();
        } catch (SQLException exception) {
            if (exception instanceof SQLIntegrityConstraintViolationException) {
                JOptionPane.showMessageDialog((Component) btnNewButton, "Cannot add or update a child row: a foreign key constraint fails. Please enter valid data.");
            } else {
                exception.printStackTrace();
            }
        } finally {
            if (insertUserStatement != null) {
                try {
                    insertUserStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
});

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sign Up");
        setPreferredSize(new Dimension(850, 750));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new Dimension(800, 500));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new Color(0, 128, 192));

        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 0, 24)); 
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
     

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); 
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
      

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel2)))
                .addContainerGap(529, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(64, 64, 64))
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 880, 110);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setBackground(new java.awt.Color(0, 102, 102));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel4.setText("SIGN UP");

        jLabel5.setBackground(new java.awt.Color(102, 102, 102));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); 
        jLabel5.setText("Nom :");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); 
        jTextField1.setForeground(new java.awt.Color(102, 102, 102));

        jLabel6.setBackground(new java.awt.Color(102, 102, 102));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); 
        jLabel6.setText("Prenom : ");

        jLabel8.setText("I've an account");

        jButton2.setForeground(new Color(0, 128, 192));
        jButton2.setText("Login");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jPasswordField1.setForeground(new java.awt.Color(102, 102, 102));
       
        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 14)); 
        jTextField3.setForeground(new java.awt.Color(102, 102, 102));

        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextField5.setForeground(new java.awt.Color(102, 102, 102));

        jLabel9.setBackground(new java.awt.Color(102, 102, 102));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); 
        jLabel9.setText("Adresse : ");

        jLabel11.setBackground(new java.awt.Color(102, 102, 102));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); 
        jLabel11.setText("Numero de telephne : ");

        jTextField6.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextField6.setForeground(new java.awt.Color(102, 102, 102));

        jTextField7.setFont(new java.awt.Font("Segoe UI", 0, 14)); 
        jTextField7.setForeground(new java.awt.Color(102, 102, 102));

        jTextField8.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTextField8.setForeground(new java.awt.Color(102, 102, 102));

        jLabel12.setBackground(new java.awt.Color(102, 102, 102));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); 
        jLabel12.setText("Emlail :");

        jLabel13.setBackground(new java.awt.Color(102, 102, 102));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); 
        jLabel13.setText("Username :");

        jLabel14.setBackground(new java.awt.Color(102, 102, 102));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel14.setText("Client type :");

        jTextField9.setFont(new java.awt.Font("Segoe UI", 0, 14)); 
        jTextField9.setForeground(new java.awt.Color(102, 102, 102));

        jLabel15.setBackground(new java.awt.Color(102, 102, 102));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); 
        jLabel15.setText("Password :");

        jButton1.setBackground(new java.awt.Color(51, 102, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Sign Up");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3Layout.setHorizontalGroup(
        	jPanel3Layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(jPanel3Layout.createSequentialGroup()
        			.addGap(44)
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(jLabel9)
        				.addComponent(jLabel5)
        				.addComponent(jLabel6)
        				.addComponent(jLabel11)
        				.addComponent(jTextField1, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
        				.addComponent(jTextField3, GroupLayout.DEFAULT_SIZE, 259, GroupLayout.DEFAULT_SIZE)
        				.addComponent(jTextField5)
        				.addComponent(jTextField9))
        			.addPreferredGap(ComponentPlacement.RELATED, 217, Short.MAX_VALUE)
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(jTextField6)
        				.addComponent(jTextField8, GroupLayout.DEFAULT_SIZE, 259, GroupLayout.DEFAULT_SIZE)
        				.addComponent(jTextField7)
        				.addComponent(jLabel12)
        				.addComponent(jLabel14)
        				.addComponent(jLabel13))
        			.addContainerGap(31, Short.MAX_VALUE))
        		.addGroup(jPanel3Layout.createSequentialGroup()
        			.addContainerGap(367, Short.MAX_VALUE)
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(jPanel3Layout.createSequentialGroup()
        					.addComponent(jLabel4)
        					.addGap(347))
        				.addGroup(jPanel3Layout.createSequentialGroup()
        					.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
        					.addGap(336))))
        		.addGroup(Alignment.LEADING, jPanel3Layout.createSequentialGroup()
        			.addGap(335)
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.TRAILING, false)
        				.addGroup(jPanel3Layout.createSequentialGroup()
        					.addComponent(jLabel8)
        					.addGap(26)
        					.addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
        					.addGap(291))
        				.addGroup(jPanel3Layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
        					.addComponent(jLabel15)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jPasswordField1, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
        					.addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
        	jPanel3Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel3Layout.createSequentialGroup()
        			.addGap(10)
        			.addComponent(jLabel4)
        			.addGap(18)
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel5)
        				.addComponent(jLabel13))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jTextField8, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel6)
        				.addComponent(jLabel12))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jTextField6, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
        			.addGap(39)
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jLabel9, Alignment.TRAILING)
        				.addComponent(jLabel14, Alignment.TRAILING))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jTextField5, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jTextField7, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addComponent(jLabel11)
        			.addGap(18)
        			.addComponent(jTextField9, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jPasswordField1, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        				.addComponent(jLabel15))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel8))
        			.addGap(6)
        			.addComponent(jButton1, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
        			.addGap(68))
        );
        jPanel3.setLayout(jPanel3Layout);
        jPanel3.setLayout(jPanel3Layout);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(0, 110, 810, 640);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
        );

        pack();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        Login loginFrame = new Login();
        loginFrame.setVisible(true);
        loginFrame.setLocationRelativeTo(null); 
        this.dispose();
    }




  
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;

}
