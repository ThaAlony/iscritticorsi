/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.edu.isspitagora.ic.model;

import it.edu.isspitagora.ic.db.CorsoDAO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ale
 */
public class Model {
    private CorsoDAO corsoDao;
    
    public Model(){
        corsoDao = new CorsoDAO();
    }
    
    public List<Corso> getCorsoByPeriodo(Integer periodo){
        return corsoDao.getCorsiByPeriodo(periodo);
    }
    
    public Map<Corso, Integer> getIscrittiByPeriodo(Integer periodo) {
        return corsoDao.getIscrittiByPeriodo(periodo);
    }
    
    public List<Studente> getStudentiByCorso(Corso c) {
        return corsoDao.getStudentiByCorso(c);
    }
    
    public Map<String, Integer> getDivisioneStudenti(Corso c){
        return corsoDao.getDivisioneStudenti(c);
    }
    
    public List<Corso> getCorsi() {
        return corsoDao.getCorsi();
    }
    
    public Studente cercaStudenteByMatricola(Studente s) {
        return corsoDao.cercaStudenteByMatricola(s);
    }
    
    public List<Corso> cercaCorsiByStudente(Studente s) {
        return corsoDao.cercaCorsiByStudente(s);
    }
}
