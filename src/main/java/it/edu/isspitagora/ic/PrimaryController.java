/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

package it.edu.isspitagora.ic;

import it.edu.isspitagora.ic.model.Corso;
import it.edu.isspitagora.ic.model.Model;
import it.edu.isspitagora.ic.model.Studente;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class PrimaryController implements Controller {  // INTERFACCIA DA NON DIMENTICARE!

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCorso"
    private ComboBox<Corso> boxCorso; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaIscrittiCroso"
    private Button btnCercaIscrittiCroso; // Value injected by FXMLLoader

    @FXML // fx:id="txtMatricola"
    private TextField txtMatricola; // Value injected by FXMLLoader

    @FXML // fx:id="btnCerca"
    private Button btnCerca; // Value injected by FXMLLoader

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader

    @FXML // fx:id="hboxStudente"
    private HBox hboxStudente; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaCorsi"
    private Button btnCercaCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="btnIscrivi"
    private Button btnIscrivi; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextField txtRisultato; // Value injected by FXMLLoader

    @FXML // fx:id="btnReset"
    private Button btnReset; // Value injected by FXMLLoader

    @FXML // fx:id="btnAvanti"
    private Button btnAvanti; // Value injected by FXMLLoader
    private Model model;

    @FXML
    void doAvanti(ActionEvent event) throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    void doCerca(ActionEvent event) {
        String m = txtMatricola.getText();
        Integer matricola;
        try{
            matricola = Integer.parseInt(m);
        }catch(NumberFormatException ne){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attenzione...");
            //alert.setHeaderText("C'è qualcosa che non va...");
            alert.setHeaderText(null);
            alert.setContentText("Devi inserire un valore numerico...");
            alert.showAndWait();
            return;
        }
        
        Studente s = this.model.cercaStudenteByMatricola(new Studente(matricola, null, null, null));
        if(s == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione...");
            alert.setHeaderText(null);
            alert.setContentText("Lo studente con matricola " +matricola+ " non esiste...");
            alert.showAndWait();
            
            //Pulisco i campi
            hboxStudente.setDisable(true);
            txtMatricola.clear();
            txtCognome.clear();
            txtNome.clear();
            return;
            
        }else{
            txtCognome.setText(s.getCognome());
            txtNome.setText(s.getNome());
            hboxStudente.setDisable(false);
        }
    }
    
    @FXML
    void doCercaCorsi(ActionEvent event) {
         //CONTROLLO MATRICOLA
        String m = txtMatricola.getText();
        Integer matricola;
        try{
            matricola = Integer.parseInt(m);
        }catch(NumberFormatException ne){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attenzione...");
            //alert.setHeaderText("C'è qualcosa che non va...");
            alert.setHeaderText(null);
            alert.setContentText("Devi inserire un valore numerico...");
            alert.showAndWait();
            return;
        }
        
        Studente s = this.model.cercaStudenteByMatricola(new Studente(matricola, null, null, null));
        if(s == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione...");
            alert.setHeaderText(null);
            alert.setContentText("Lo studente con matricola " +matricola+ " non esiste...");
            alert.showAndWait();
            
            //Pulisco i campi
            hboxStudente.setDisable(true);
            //txtMatricola.clear();
            txtCognome.clear();
            txtNome.clear();
            return;
            
        }
        //CERCARE I CORSI A CUI E'ISCRITTO
        List<Corso> result = this.model.cercaCorsiByStudente(s);
        if (result == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione...");
            alert.setHeaderText(null);
            alert.setContentText("Lo studente non è iscritto a nessun corso...");
            alert.showAndWait();
        }else{
            txtRisultato.setStyle("-fx-font-family: monospace");
            StringBuilder sb = new StringBuilder();
        
            for(Corso c: result){
            sb.append(String.format("%-50s\n", c.getNome()));
                       
        }
        //OUTPUT Risultati
        txtRisultato.clear();
        txtRisultato.appendText(sb.toString());
        txtRisultato.home();
        }
    }

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {
       //RECUPERARE IL CORSO dalla combo box
        Corso corso = boxCorso.getValue();
        if(corso == null){
            //txtRisultato.appendText("Devi selezionare un corso dalla combobox...\n");
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione...");
            //alert.setHeaderText("C'è qualcosa che non va...");
            alert.setHeaderText(null);
            alert.setContentText("Devi selezionare un corso dalla combobox...");
            alert.showAndWait();
            
            return;
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
        txtRisultato.home();
    }

    @FXML
    void doIscrivi(ActionEvent event) {

    }

    @FXML
    void doReset(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCorso != null : "fx:id=\"boxCorso\" was not injected: check your FXML file 'primary.fxml'.";
        assert btnCercaIscrittiCroso != null : "fx:id=\"btnCercaIscrittiCroso\" was not injected: check your FXML file 'primary.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'primary.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'primary.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'primary.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'primary.fxml'.";
        assert hboxStudente != null : "fx:id=\"hboxStudente\" was not injected: check your FXML file 'primary.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'primary.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'primary.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'primary.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'primary.fxml'.";
        assert btnAvanti != null : "fx:id=\"btnAvanti\" was not injected: check your FXML file 'primary.fxml'.";

    }

    @Override
    public void setModel(Model model) {
        this.model = model;
        //POPOLO LA COMBOBOX
        boxCorso.getItems().clear();
        boxCorso.getItems().addAll(this.model.getCorsi());
    }
}
