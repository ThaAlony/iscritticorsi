/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.edu.isspitagora.ic.model;

import java.util.Objects;

/**
 *
 * @author Ale
 */
public class Corso {
    private String codins;
    private Integer crediti;
    private String nome;
    private Integer pd;

    public Corso(String codins, Integer crediti, String nome, Integer pd) {
        this.codins = codins;
        this.crediti = crediti;
        this.nome = nome;
        this.pd = pd;
    }

    public String getCodins() {
        return codins;
    }

    public Integer getCrediti() {
        return crediti;
    }

    public String getNome() {
        return nome;
    }

    public Integer getPd() {
        return pd;
    }

    public void setCodins(String codins) {
        this.codins = codins;
    }

    public void setCrediti(Integer crediti) {
        this.crediti = crediti;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPd(Integer pd) {
        this.pd = pd;
    }

    @Override
    public String toString() {
        return nome ;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.codins);
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
        final Corso other = (Corso) obj;
        if (!Objects.equals(this.codins, other.codins)) {
            return false;
        }
        return true;
    }
    
    
    
}
