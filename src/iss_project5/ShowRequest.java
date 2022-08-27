/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iss_project5;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Bcc
 */
public class ShowRequest extends javax.swing.JFrame {
    Request request= new Request();
    Password password= new Password();
    User user = new User();
    static ArrayList list;
    /**
     * Creates new form ShowRequest
     */
    public ShowRequest() {
        initComponents();
        setTable(list);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        sharedRequests = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        request_id = new javax.swing.JTextField();
        accept = new javax.swing.JButton();
        delete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sharedRequests.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sharedRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Request Id", "User Id", "Title", "Password", "Description"
            }
        ));
        sharedRequests.setInheritsPopupMenu(true);
        sharedRequests.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                sharedRequestsAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(sharedRequests);
        if (sharedRequests.getColumnModel().getColumnCount() > 0) {
            sharedRequests.getColumnModel().getColumn(0).setMinWidth(80);
            sharedRequests.getColumnModel().getColumn(0).setMaxWidth(100);
            sharedRequests.getColumnModel().getColumn(1).setMinWidth(80);
            sharedRequests.getColumnModel().getColumn(1).setMaxWidth(100);
            sharedRequests.getColumnModel().getColumn(2).setMinWidth(100);
            sharedRequests.getColumnModel().getColumn(2).setMaxWidth(120);
        }

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel2.setText("Request Id");

        request_id.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        accept.setBackground(new java.awt.Color(255, 255, 153));
        accept.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        accept.setText("Accept");
        accept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptActionPerformed(evt);
            }
        });

        delete.setBackground(new java.awt.Color(255, 255, 153));
        delete.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        delete.setText("Delete");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(request_id, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(accept, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(request_id, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(78, 78, 78)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(accept, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void acceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptActionPerformed
        // TODO add your handling code here:
        password.user_id = Client.user.userId;
        user.userId = Integer.parseInt(request_id.getText());
        try {
            request.type = 8;
            request.password = this.password;
            request.user = this.user;
            Client.send_request(request);
        } catch (IOException ex) {
           System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddPassword.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AddPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();        
    }//GEN-LAST:event_acceptActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // TODO add your handling code here:
        password.user_id = Client.user.userId;
        user.userId = Integer.parseInt(request_id.getText());
        try {
            request.type = 9;
            request.password = this.password;
            request.user = this.user;
            Client.send_request(request);
        } catch (IOException ex) {
           System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddPassword.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AddPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose(); 
        
    }//GEN-LAST:event_deleteActionPerformed

    private void sharedRequestsAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_sharedRequestsAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_sharedRequestsAncestorAdded

    /**
     * @param args the command line arguments
     */
    public static void main(ArrayList list) {
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
            java.util.logging.Logger.getLogger(ShowRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShowRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShowRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShowRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        ShowRequest.list = list;
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShowRequest().setVisible(true);
            }
        });
    }
    public void setTable(ArrayList requests)
    {
        if(requests!=null)
        {
            DefaultTableModel table= (DefaultTableModel) sharedRequests.getModel();
            Object[] data = new Object[5];
            for(int i=0 ;i<requests.size();i++)
            {
                SharePassword sp = (SharePassword) requests.get(i);
                try {
                    sp.RSA_Decode(Client.user.privateKey);
                } catch (Exception ex) {
                    Logger.getLogger(ShowRequest.class.getName()).log(Level.SEVERE, null, ex);
                }
                data[0] = sp.id;
                data[1] = sp.user_id1;
                data[2] = sp.password.title;
                data[3] = sp.password.password;
                data[4] = sp.password.description;
                table.addRow(data);
            }
        }
    }
    
    public static ArrayList myRequests(Request request)
    {
        ArrayList list = new ArrayList();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/iss_project", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM requests");
            while(rs.next())
            {
                System.out.println("\nrequest.user.userId: "+request.user.userId);
                System.out.println("rs.getInt(3): "+rs.getInt(3));
                if(request.user.userId== rs.getInt(3) && rs.getInt(8)==0)
                {
                    SharePassword sp = new SharePassword();
                    sp.id = rs.getInt(1);
                    sp.user_id1 = rs.getInt(2);
                    sp.user_id2 = rs.getInt(3);
                    sp.password.title = rs.getString(4); 
                    sp.password.password = rs.getString(5); 
                    sp.password.description = rs.getString(6); 
                    Blob blob = rs.getBlob(7);
                    if(blob!=null)
                    {
                        int blobLength = (int) blob.length();
                        sp.password.file = blob.getBytes(1, blobLength);
                        blob.free();
                    }
                    list.add(sp);
                }
            }
            con.close();
        } catch (Exception ex) {
            Logger.getLogger(AddRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
//        if(list!=null){
//            System.out.println("list in Show Request: "+(SharePassword)list.get(0));
//        }
        return list;
    }
    
    public static Response process(Request request)
    {
        Response response = new Response();
        response.msg = "success";
        if(request.type == 8)
        {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/iss_project", "root", "");
                Statement stmt = con.createStatement();
                String sql = "UPDATE requests SET accepted = 1 WHERE  id = ? ";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, request.user.userId);
                int i= ps.executeUpdate();
                if(i==0)
                {
                    response.msg = "fail";
                }
                ps.close();
                con.close();
            }catch(Exception e){
                response.msg = "fail";
                System.out.println(e);
            }
        }
        else
        {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/iss_project", "root", "");
                String delete = "delete from requests where id = ? ";
                PreparedStatement ps = con.prepareStatement(delete);
                ps.setInt(1,request.user.userId);
                int i= ps.executeUpdate();
                if(i==0)
                {
                    response.msg = "fail";
                }
                ps.close();
                con.close();
            }catch(Exception e){
                response.msg = "fail";
                System.out.println(e);
            }
        }
        return response;
    }
    
    public static void next_step()
    {
        UserRequest.main();
    }
    
    public static void same_step()
    {
        ShowRequest.main(ShowRequest.list);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept;
    private javax.swing.JButton delete;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField request_id;
    private javax.swing.JTable sharedRequests;
    // End of variables declaration//GEN-END:variables
}
