package it.edu.isspitagora.ic;

import it.edu.isspitagora.ic.model.Corso;
import it.edu.isspitagora.ic.model.Model;
import it.edu.isspitagora.ic.model.Studente;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SecondaryController implements Controller{
    
    private Model model;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtPeriodo;

    @FXML
    private Button btnCorsiPerPeriodo;

    @FXML
    private Button btnNumeroStudenti;

    @FXML
    private ComboBox<Corso> boxCorso;

    @FXML
    private Button btnStudenti;

    @FXML
    private Button btnDivisioneStudenti;

    @FXML
    private TextArea txtRisultato;

    @FXML
    private Button btnIndietro;

    @FXML
    void corsiPerPeriodo(ActionEvent event) {
        String periodoDidattico = txtPeriodo.getText();
        Integer periodo;
        //VALIDAZIONE INPUT
        try{
            periodo = Integer.parseInt(periodoDidattico);
            if(periodo < 1 || periodo > 2){
                //txtRisultato.appendText("Devi inserire un numero (1 oppure 2) per il periodo didattico \n");
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Attenzione...");
                //alert.setHeaderText("C'è qualcosa che non va..");
                alert.setHeaderText(null);
                alert.setContentText("Devi specificare il pediodo didattico (1/2)...");
                alert.showAndWait();
                return;
            }
        }catch(NumberFormatException e){
            //txtRisultato.appendText("Devi inserire un numero (1 oppure 2) per il periodo didattico \n");
            Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Attenzione...");
                //alert.setHeaderText("C'è qualcosa che non va..");
                alert.setHeaderText(null);
                alert.setContentText("Devi specificare il pediodo didattico (1/2)...");
                alert.showAndWait();
            return;
        }
        //INPUT VALIDATO
        //CHIAMA Model
        List<Corso> corsi = this.model.getCorsoByPeriodo(periodo);
        
        txtRisultato.setStyle("-fx-font-family: monospace");
        StringBuilder sb = new StringBuilder();
        
        for(Corso c: corsi){
            //txtRisultato.appendText(c.toString() + "\n");
            sb.append(String.format("%-8s", c.getCodins()));
            sb.append(String.format("%-4d", c.getCrediti()));
            sb.append(String.format("%-50s", c.getNome()));
            sb.append(String.format("%-2d\n", c.getPd()));            
        }
        //OUTPUT Risultati
        txtRisultato.clear();
        txtRisultato.appendText(sb.toString());
        
        //POPOLO la combobox
        boxCorso.getItems().clear();
        boxCorso.getItems().addAll(this.model.getCorsoByPeriodo(periodo));
        
    }

    @FXML
    void doIndietro(ActionEvent event) throws IOException{
        App.setRoot("primary");
    }

    @FXML
    void numeroStudenti(ActionEvent event) {
        String periodoDidattico = txtPeriodo.getText();
        Integer periodo;
        //VALIDAZIONE INPUT
        try{
            periodo = Integer.parseInt(periodoDidattico);
            if(periodo < 1 || periodo > 2){
                //txtRisultato.appendText("Devi inserire un numero (1 oppure 2) per il periodo didattico \n");
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Attenzione...");
                alert.setHeaderText("C'è qualcosa che non va..");
                alert.setContentText("Devi specificare il pediodo didattico (1/2)...");
                alert.showAndWait();
                return;
            }
        }catch(NumberFormatException e){
            //txtRisultato.appendText("Devi inserire un numero (1 oppure 2) per il periodo didattico \n");
            Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Attenzione...");
                alert.setHeaderText("C'è qualcosa che non va..");
                alert.setContentText("Devi specificare il pediodo didattico (1/2)...");
                alert.showAndWait();
            return;
        }
        //INPUT VALIDATO
        //CHIAMA Model
        Map<Corso, Integer> corsiIscrizione = this.model.getIscrittiByPeriodo(periodo);
        
        txtRisultato.setStyle("-fx-font-family: monospace");
        StringBuilder sb = new StringBuilder();
        
        for(Corso c: corsiIscrizione.keySet()){
            //txtRisultato.appendText(c.toString() + "\n");
            Integer n = corsiIscrizione.get(c);
            sb.append(String.format("%-8s", c.getCodins()));
            sb.append(String.format("%-4d", c.getCrediti()));
            sb.append(String.format("%-50s", c.getNome()));
            sb.append(String.format("%-2d", c.getPd())); 
            sb.append(String.format("%-4d\n", n));
        }
        //OUTPUT Risultati
        txtRisultato.clear();
        txtRisultato.appendText(sb.toString());
        
        //POPOLO la combobox
        boxCorso.getItems().clear();
        boxCorso.getItems().addAll(this.model.getCorsoByPeriodo(periodo));
    }

    @FXML
    void stampaDivisione(ActionEvent event) {
        Map<String, Integer> risultato = new HashMap<>();
        
        Corso corso = boxCorso.getValue();
        if(corso == null){
            //txtRisultato.appendText("[ERRORE] devi selezionare un corso.\n");
            Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Attenzione...");
                alert.setHeaderText("C'è qualcosa che non va..");
                alert.setContentText("Devi selezionare un corso...");
                alert.showAndWait();
            return;
        }
        
        risultato = this.model.getDivisioneStudenti(corso);
        
        txtRisultato.setStyle("-fx-font-family: monospace");
        StringBuilder sb = new StringBuilder();
        
        for(String cds: risultato.keySet()){
            sb.append(String.format("%-60s", cds));
            sb.append(String.format("%-4d\n", risultato.get(cds)));
        }
        
        txtRisultato.clear();
        txtRisultato.appendText(sb.toString());
    }

    @FXML
    void stampaStudenti(ActionEvent event) {
        //RECUPERARE IL CORSO DELLA comboBox
        Corso corso = boxCorso.getValue();
        if(corso == null) {
            //txtRisultato.appendText("Devi selezionare un corso dalla comboBox... \n");
            Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Attenzione...");
                alert.setHeaderText("C'è qualcosa che non va..");
                alert.setContentText("Devi selezionare un corso dalla comboBox...");
                alert.showAndWait();
        }
        
        List<Studente> studenti = new LinkedList<>();
        
        studenti = this.model.getStudentiByCorso(corso);
        
        txtRisultato.setStyle("-fx-font-family: monospace");
        StringBuilder sb = new StringBuilder();
        
        for(Studente s: studenti){
            //txtRisultato.appendText(c.toString() + "\n");
            sb.append(String.format("%-8s", s.getMatricola()));
            sb.append(String.format("%-30s", s.getCognome()));
            sb.append(String.format("%-30s", s.getNome()));
            sb.append(String.format("%-10s\n", s.getCds()));            
        }
        //OUTPUT Risultati
        txtRisultato.clear();
        txtRisultato.appendText(sb.toString());
    }

    @FXML
    void initialize() {
        assert txtPeriodo != null : "fx:id=\"txtPeriodo\" was not injected: check your FXML file 'secondary.fxml'.";
        assert btnCorsiPerPeriodo != null : "fx:id=\"btnCorsiPerPeriodo\" was not injected: check your FXML file 'secondary.fxml'.";
        assert btnNumeroStudenti != null : "fx:id=\"btnNumeroStudenti\" was not injected: check your FXML file 'secondary.fxml'.";
        assert boxCorso != null : "fx:id=\"boxCorso\" was not injected: check your FXML file 'secondary.fxml'.";
        assert btnStudenti != null : "fx:id=\"btnStudenti\" was not injected: check your FXML file 'secondary.fxml'.";
        assert btnDivisioneStudenti != null : "fx:id=\"btnDivisioneStudenti\" was not injected: check your FXML file 'secondary.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'secondary.fxml'.";
        assert btnIndietro != null : "fx:id=\"btnIndietro\" was not injected: check your FXML file 'secondary.fxml'.";

    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }
}
