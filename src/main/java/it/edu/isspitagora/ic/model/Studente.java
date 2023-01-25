/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.edu.isspitagora.ic.model;

import java.util.Objects;

/**
 *
 * @author alony
 */
public class Studente {
    private Integer matricola;
    private String cognome;
    private String nome;
    private String cds;

    public Studente(Integer matricola, String cognome, String nome, String cds) {
        this.matricola = matricola;
        this.cognome = cognome;
        this.nome = nome;
        this.cds = cds;
    }

    public Integer getMatricola() {
        return matricola;
    }

    public void setMatricola(Integer matricola) {
        this.matricola = matricola;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCds() {
        return cds;
    }

    public void setCds(String cds) {
        this.cds = cds;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.matricola);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Studente other = (Studente) obj;
        if (!Objects.equals(this.matricola, other.matricola)) {
            return false;
        }
        return true;
    }
    
    
    
}