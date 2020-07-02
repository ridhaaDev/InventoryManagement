/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productmanagementsystem;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Main_Window extends javax.swing.JFrame {

    /**
     * Creates new form Main_Window
     */
    public Main_Window() {
        initComponents();
        show_products_in_JTable();
        String[] creds = getCredentials();
    }
    
    public static String[] creds = getCredentials();
    public static String connectionStr = creds[0];
    public static String username = creds[0];
    public static String password = creds[0];
    
    String imagePath = null;
    int pos = 0;
    
    public static String[] getCredentials() {
        String [] creds = new String[3];
        try {
            File file = new File("config.txt");
            
            BufferedReader br = new BufferedReader(new FileReader(file));
            
            creds[0] = br.readLine();
            creds[1] = br.readLine();
            creds[2] = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return creds;
    }
    public Connection getConnection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connectionStr, username, password);
            return conn;
        } catch (SQLException ex) {
            Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    // Check input fields
    public boolean checkInputs() {
        if (txt_name.getText() == null
                || txt_price.getText() == null
                || txt_add_date.getDate() == null) {
            return false;
        } else {
            try {
                Float.parseFloat(txt_price.getText());
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }

    // Resize image
    public ImageIcon ResizeImage(String imagePath, byte[] pic) {
        ImageIcon myImage = null;

        if (imagePath != null) {
            myImage = new ImageIcon(imagePath);
        } else {
            myImage = new ImageIcon(pic);
        }

        Image img = myImage.getImage();
        Image img2 = img.getScaledInstance(lbl_image.getWidth(), lbl_image.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);

        return image;
    }

    // Display the data in JTable
    // 1 - Fill ArrayList with the data
    public ArrayList<Product> getProductList() {
        ArrayList<Product> productList = new ArrayList<Product>();
        Connection conn = getConnection();
        String query = "SELECT * FROM products";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Product product;

            while (rs.next()) {
                product = new Product(rs.getInt("id"), rs.getString("name"), Float.parseFloat(rs.getString("price")), rs.getString("add_date"), rs.getBytes("image"));
                productList.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
        }

        return productList;
    }

    // 2 - Populate the JTable 
    public void show_products_in_JTable() {
        ArrayList<Product> list = getProductList();
        DefaultTableModel model = (DefaultTableModel) jtable_products.getModel();

        // clear jtable content
        model.setRowCount(0);
        Object[] row = new Object[4];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getName();
            row[2] = String.format("%.2f", list.get(i).getPrice());
            row[3] = list.get(i).getAddDate();

            model.addRow(row);
        }
    }

    public void showItem(int index) {
        txt_id.setText(Integer.toString(getProductList().get(index).getId()));
        txt_name.setText(getProductList().get(index).getName());
        String price = String.format("%.2f", getProductList().get(index).getPrice());
        txt_price.setText(price);

        try {
            Date addDate = null;
            addDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) getProductList().get(index).getAddDate());
            txt_add_date.setDate(addDate);
        } catch (ParseException ex) {
            Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
        }

        lbl_image.setIcon(ResizeImage(null, getProductList().get(index).getPicture()));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_name = new javax.swing.JTextField();
        txt_price = new javax.swing.JTextField();
        txt_id = new javax.swing.JTextField();
        lbl_image = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtable_products = new javax.swing.JTable();
        btn_choose_image = new javax.swing.JButton();
        btn_insert = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_first = new javax.swing.JButton();
        btn_previous = new javax.swing.JButton();
        btn_last = new javax.swing.JButton();
        btn_next = new javax.swing.JButton();
        txt_add_date = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));

        jLabel1.setText("ID: ");

        jLabel2.setText("Name:");

        jLabel3.setText("Price:");

        jLabel4.setText("Add Date:");

        jLabel5.setText("Image:");

        txt_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nameActionPerformed(evt);
            }
        });

        txt_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_priceActionPerformed(evt);
            }
        });

        txt_id.setEnabled(false);
        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });

        lbl_image.setOpaque(true);

        jtable_products.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Price", "Add Date"
            }
        ));
        jtable_products.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtable_productsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtable_products);
        if (jtable_products.getColumnModel().getColumnCount() > 0) {
            jtable_products.getColumnModel().getColumn(0).setMinWidth(30);
            jtable_products.getColumnModel().getColumn(0).setMaxWidth(30);
        }

        btn_choose_image.setText("Choose image");
        btn_choose_image.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_choose_imageActionPerformed(evt);
            }
        });

        btn_insert.setText("Insert");
        btn_insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertActionPerformed(evt);
            }
        });

        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_first.setText("First");
        btn_first.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_firstActionPerformed(evt);
            }
        });

        btn_previous.setText("Previous");
        btn_previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_previousActionPerformed(evt);
            }
        });

        btn_last.setText("Last");
        btn_last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lastActionPerformed(evt);
            }
        });

        btn_next.setText("Next");
        btn_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txt_price, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btn_choose_image, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                        .addComponent(txt_id, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_name, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(txt_add_date, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_insert, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_image, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_first, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_next, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_previous, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_last, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_name, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                .addGap(22, 22, 22))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_price, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_add_date, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_image, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btn_choose_image, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_last, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_insert, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btn_first, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_previous, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_next, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(61, 61, 61))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nameActionPerformed

    private void txt_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_priceActionPerformed

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idActionPerformed

    private void btn_choose_imageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_choose_imageActionPerformed

        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));

        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.image", "jpg", "png");
        file.addChoosableFileFilter(filter);

        int result = file.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = file.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            lbl_image.setIcon(ResizeImage(path, null));
            imagePath = path;
        } else {
            System.out.println("No File selected");
        }

    }//GEN-LAST:event_btn_choose_imageActionPerformed

    private void btn_insertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertActionPerformed

        if (checkInputs() && imagePath != null) {
            try {
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO PRODUCTS (name, price, add_date, image)"
                        + "values(?, ?, ?, ?)");
                ps.setString(1, txt_name.getText());
                ps.setString(2, txt_price.getText());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String addDate = dateFormat.format(txt_add_date.getDate());
                ps.setString(3, addDate);

                InputStream img = new FileInputStream(new File(imagePath));
                ps.setBlob(4, img);
                ps.executeUpdate();
                show_products_in_JTable();
                JOptionPane.showMessageDialog(null, "Data inserted");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "One or fields are empty");
        }
    }//GEN-LAST:event_btn_insertActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed

        if (checkInputs() && txt_id.getText() != null) {
            String updateQuery = null;
            PreparedStatement ps = null;

            Connection conn = getConnection();

            // Update without image
            if (imagePath == null) {
                try {
                    updateQuery = "UPDATE products SET name = ?, price = ?"
                            + ", add_date = ? WHERE id = ?";
                    ps = conn.prepareStatement(updateQuery);

                    ps.setString(1, txt_name.getText());
                    ps.setString(2, txt_price.getText());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String addDate = dateFormat.format(txt_add_date.getDate());

                    ps.setString(3, addDate);

                    ps.setInt(4, Integer.parseInt(txt_id.getText()));
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Product updated!");
                    show_products_in_JTable();
                } catch (SQLException ex) {
                    Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    // Update with image
                    InputStream img = new FileInputStream(new File(imagePath));

                    updateQuery = "UPDATE products SET name = ?, price = ?"
                            + ", add_date = ?, image = ? WHERE id = ?";

                    ps = conn.prepareStatement(updateQuery);

                    ps.setString(1, txt_name.getText());
                    ps.setString(2, txt_price.getText());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String addDate = dateFormat.format(txt_add_date.getDate());

                    ps.setString(3, addDate);

                    ps.setBlob(4, img);

                    ps.setInt(5, Integer.parseInt(txt_id.getText()));
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Product updated!");
                    show_products_in_JTable();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "One or more fields are empty or incorrect");
        }
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed

        if (!txt_id.getText().equals("")) {
            try {
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM products WHERE id = ?");
                int id = Integer.parseInt(txt_id.getText());

                ps.setInt(1, id);
                ps.executeUpdate();
                show_products_in_JTable();
                JOptionPane.showMessageDialog(null, "Product has been deleted!");
            } catch (SQLException ex) {
                Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Product not deleted");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Product not deleted: Enter a valid id to delete");
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void jtable_productsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtable_productsMouseClicked
        int index = jtable_products.getSelectedRow();
        showItem(index);
    }//GEN-LAST:event_jtable_productsMouseClicked

    private void btn_firstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_firstActionPerformed

        pos = 0;
        showItem(pos);
    }//GEN-LAST:event_btn_firstActionPerformed

    private void btn_lastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lastActionPerformed
        pos = getProductList().size() - 1;
        showItem(pos);
    }//GEN-LAST:event_btn_lastActionPerformed

    private void btn_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextActionPerformed
        pos++;

        if (pos <= 0) {
            pos = 0;
        }

        showItem(pos);
    }//GEN-LAST:event_btn_nextActionPerformed

    private void btn_previousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_previousActionPerformed
        pos--;

        if (pos >= getProductList().size()) {
            pos = getProductList().size() - 1;
        }

        showItem(pos);
    }//GEN-LAST:event_btn_previousActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main_Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main_Window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_choose_image;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_first;
    private javax.swing.JButton btn_insert;
    private javax.swing.JButton btn_last;
    private javax.swing.JButton btn_next;
    private javax.swing.JButton btn_previous;
    private javax.swing.JButton btn_update;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtable_products;
    private javax.swing.JLabel lbl_image;
    private com.toedter.calendar.JDateChooser txt_add_date;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_price;
    // End of variables declaration//GEN-END:variables
}
