/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.edu.isspitagora.ic.model;

import java.util.Comparator;

/**
 *
 * @author alony
 */
public class SortByNomeCorso implements Comparator<Corso> {

    @Override
    public int compare(Corso c1, Corso c2) {
        if(c1.getNome().equalsIgnoreCase(c2.getNome())) {
            return c1.getCodins().compareToIgnoreCase(c2.getCodins());
        } else {
        return c1.getNome().compareTo(c2.getNome());
        }
    }
    
}
