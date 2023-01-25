/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.edu.isspitagora.ic.db;

import it.edu.isspitagora.ic.model.Corso;
import it.edu.isspitagora.ic.model.SortByNomeCorso;
import it.edu.isspitagora.ic.model.Studente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Ale
 */
public class CorsoDAO {
    
    public  List<Corso> getCorsiByPeriodo(Integer periodo){
        List<Corso> result = new ArrayList<>();
        String sql = "SELECT * " +
                     "FROM corso " +
                     "WHERE pd = ?";
    try{
        Connection conn = DBConnect.getConnection();
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, periodo);
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
            Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
            result.add(c);
        }
        
        rs.close();
        st.close();
        conn.close();
        
    }   catch (SQLException ex) {    
            throw new RuntimeException("Problema al DB... Metodo getCorsiByPeriodo..." + ex);
        } 
    return result;
    }
    
    public Map<String, Integer> getDivisioneStudenti(Corso c){
        Map<String, Integer> result = new HashMap<>();
//        String sql = "SELECT s.CDS, COUNT(*) AS tot " +
//                    "FROM studente s, iscrizione i " +
//                    "WHERE s.matricola = i.matricola AND i.codins = ? " +
//                    "GROUP BY s.CDS";
        String sql = "SELECT f.nome, t.cds, t.tot " +
                    "FROM facolta f " +
                    "RIGHT JOIN " +
                    "(SELECT s.CDS, COUNT(*) AS tot " +
                    "FROM studente s, iscrizione i " +
                    "WHERE s.matricola = i.matricola AND i.codins = ? " +
                    "GROUP BY s.cds) AS t " +
                    "on f.cds = t.cds";
        
        try{
            Connection conn = DBConnect.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, c.getCodins());
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                String cds = rs.getString("cds");
                Integer tot = rs.getInt("tot");
                result.put(cds+ " " + rs.getString("nome"), tot);
            }   
            
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Problema al DATABASE: metodo getDivisioneStudenti\n" + ex);
        }
        return result;
        
    }
    
    public Map<Corso, Integer> getIscrittiByPeriodo(Integer periodo) {
        Map<Corso, Integer> result = new HashMap<>();
        
        String sql = "SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot " +
                        "FROM corso c, iscrizione i " +
                        "WHERE c.codins = i.codins AND c.pd = ? " +
                        "GROUP BY c.codins,c.nome, c.crediti, c.pd " +
                        "ORDER BY c.nome";
        try{
        Connection conn = DBConnect.getConnection();
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, periodo);
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
            Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
            Integer tot = rs.getInt("tot");
            result.put(c, tot);
        }
        
        rs.close();
        st.close();
        conn.close();
        
        }   catch (SQLException ex) {    
            throw new RuntimeException("Problema al DB... Metodo getIscrittiByPeriodo..." + ex);
        } 
        
        SortedMap<Corso, Integer> sortResult = new TreeMap(new SortByNomeCorso());
        sortResult.putAll(result);
        return sortResult;
    }
    
    public List<Studente> getStudentiByCorso(Corso c) {
        List<Studente> result = new ArrayList<>();
        String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS " +
        "FROM studente s, iscrizione i " +
        "WHERE s.matricola = i.matricola AND i.codins = ? " +
            "ORDER BY s.cognome, s.nome";
        
        try{
        Connection conn = DBConnect.getConnection();
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, c.getCodins());
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
            Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
            result.add(s);
        }
        
        rs.close();
        st.close();
        conn.close();
        
        }   catch (SQLException ex) {    
            throw new RuntimeException("Problema al DB... Metodo getStudentiByCorso..." + ex);
        } 
        
        return result;
    }
    
    /*
    
        SECONDA PARTE
    
        DELLESERCIZIO
    */
    
    public List<Corso> getCorsi() {
        List<Corso> result = new LinkedList<>();
        String sql = "SELECT * FROM corso order by nome";
        try{
        Connection conn = DBConnect.getConnection();
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
            Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
            result.add(c);
        }
        
        rs.close();
        st.close();
        conn.close();
        
    }   catch (SQLException ex) {    
            throw new RuntimeException("Problema al DB... Metodo getCorsi..." + ex);
        } 
    return result;
    }
    
    public Studente cercaStudenteByMatricola(Studente s) {
        Studente result = null;
        String sql = "SELECT s.matricola, s.nome, s.cognome, s.cds " +
                    "FROM studente s " +
                    "WHERE s.matricola = ?";
         try{
        Connection conn = DBConnect.getConnection();
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, s.getMatricola());
        ResultSet rs = st.executeQuery();
        
        if(rs.next()) {
            result = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("cds"));
        }
        
        rs.close();
        st.close();
        conn.close();
        
    }   catch (SQLException ex) {    
            throw new RuntimeException("Problema al DB... Metodo cercaStudenteByMatricola..." + ex);
        } 
    return result;
    }
    
    public List<Corso> cercaCorsiByStudente(Studente s) {
        List<Corso> result = new ArrayList<>();
        String sql = "SELECT c.nome " +
                    "FROM iscrizione i, corso c " +
                    "WHERE i.codins = c.codins AND i.matricola = ? " +
                    "ORDER BY c.nome";
        try{
            Connection conn = DBConnect.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, s.getMatricola());
            ResultSet rs = st.executeQuery();
        
        if(rs.next()) {
            Corso c = new Corso(null, null, rs.getString("nome"), null);
            result.add(c);
            
        }
        
        rs.close();
        st.close();
        conn.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Problema al DB... Metodo cercaCorsiByStudente..." + ex);
        }
        return result;
    }
    
     public boolean isStudente(Studente s) {
       String sql = "SELECT * FROM studente s WHERE s.matricola = ?";
       
       try {
           Connection conn = DBConnect.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, s.getMatricola());
            ResultSet rs = st.executeQuery();
            
            if(rs.next()) {
                rs.close();
                st.close();
                conn.close();
                return true;
            } else {
                rs.close();
                st.close();
                conn.close();
                return false;
            }
       } catch (SQLException ex ) {
           throw new RuntimeException("Problema al DB... Metodo isStudente..." + ex);
       }
       
   }
     
   
}
