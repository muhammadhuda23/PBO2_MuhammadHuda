/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class dbCRUD {
    
    String jdbcURL ="jdbc:mysql://localhost:3306/pemetaan_kehutanan";
    String username ="root";
    String password ="";
    
    Connection koneksi;
    
    public dbCRUD(){
        try (Connection Koneksi = DriverManager.getConnection(jdbcURL, username, password)){
            Driver mysqldriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(mysqldriver);
            System.out.println("Berhasil");
        } catch (SQLException error) {
            System.err.println(error.toString());
        }
    }
    
    public dbCRUD(String url, String user, String pass){
        
        try(Connection Koneksi = DriverManager.getConnection(url, user, pass)) {
            Driver mysqldriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(mysqldriver);
            
            System.out.println("Berhasil");
        } catch (Exception error) {
            System.err.println(error.toString());
        }
        
    }
    
    public Connection getKoneksi () throws SQLException{
        try { 
            Driver mysqldriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver (mysqldriver);

        } catch (SQLException e) {

            System.err.println(e.toString());
        }

         return DriverManager.getConnection(this.jdbcURL, this.username, this.password);
    }
    
    public boolean duplicateKey(String table, String PrimaryKey, String value) { 
        boolean hasil=false;

        try {
            Statement sts = getKoneksi().createStatement();
            ResultSet rs = sts.executeQuery("SELECT * FROM "+table+" WHERE "+PrimaryKey+" ='"+value+"'");

            hasil = rs.next();

            rs.close();
            sts.close(); 
            getKoneksi().close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        
        return hasil;
    }
    
    public void SimpanDinamiscPersonnel(String nip, String nama, String tmplahir, String tgllahir, String jenkel, String status, String agama, String alamat, String telp) {
    try {
        if (duplicateKey("personnel", "NIP", nip)) {
            JOptionPane.showMessageDialog(null, "NIP sudah terdaftar");
        } else { 
            String SQL = "INSERT INTO personnel (NIP, NAMA, TMPLAHIR, TGLLAHIR, JENKEL, STATUS, AGAMA, ALAMAT, TELP) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement simpan = getKoneksi().prepareStatement(SQL);
            simpan.setString(1, nip);
            simpan.setString(2, nama);
            simpan.setString(3, tmplahir);
            simpan.setString(4, tgllahir);
            simpan.setString(5, jenkel);
            simpan.setString(6, status);
            simpan.setString(7, agama);
            simpan.setString(8, alamat);
            simpan.setString(9, telp);
            
            System.out.println("Data Berhasil Disimpan");
            simpan.executeUpdate();
            simpan.close();
            getKoneksi().close();
        }
    } catch (Exception e) {
        System.out.println(e.toString());
    }
}

    
    public void SimpanDinamiscFprint(String kode_fprint, String tgl, String alasan) {
    try {
        String SQL = "INSERT INTO fprint (KODE_FPRINT, TGL, ALASAN) VALUES (?, ?, ?)";
        PreparedStatement simpan = getKoneksi().prepareStatement(SQL);
        simpan.setString(1, kode_fprint);
        simpan.setString(2, tgl);
        simpan.setString(3, alasan);

        System.out.println("Data Berhasil Disimpan");
        simpan.executeUpdate();
        simpan.close();
        getKoneksi().close();
    } catch (Exception e) {
        System.out.println(e.toString());
    }
}

    
    public String getFieldValueEdit(String[] Field, String[] value){
        String hasil = "";
        int deteksi = Field.length-1;
        try {
            for (int i = 0; i < Field.length; i++) {
                if (i==deteksi){
                    hasil = hasil +Field[i]+" ='"+value[i]+"'";
                }else{
                   hasil = hasil +Field[i]+" ='"+value[i]+"',";  
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        return hasil;
    }
    
    public void UbahDinamis(String NamaTabel, String PrimaryKey, String IsiPrimary,String[] Field, String[] Value){
        
        try {
           String SQLUbah = "UPDATE "+NamaTabel+" SET "+getFieldValueEdit(Field, Value)+" WHERE "+PrimaryKey+"='"+IsiPrimary+"'";
           Statement perintah = getKoneksi().createStatement();
           perintah.executeUpdate(SQLUbah);
           perintah.close();
           getKoneksi().close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        
    }
    
    public void HapusDinamis(String NamaTabel, String PK, String isi){
        try {
            String SQL="DELETE FROM "+NamaTabel+" WHERE "+PK+"='"+isi+"'";
            Statement perintah = getKoneksi().createStatement();
            perintah.executeUpdate(SQL);
            perintah.close();
            JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
} 

