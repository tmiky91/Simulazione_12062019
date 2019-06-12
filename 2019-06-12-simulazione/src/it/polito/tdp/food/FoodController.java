package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtCalorie;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<?> boxIngrediente;

    @FXML
    private Button btnDietaEquilibrata;

    @FXML
    private TextArea txtResult;

	private Model model;

    @FXML
    void doCalcolaDieta(ActionEvent event) {

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String numCalorie = txtCalorie.getText();
//    	try {
//    		Integer.parseInt(numCalorie);
//		} catch (NumberFormatException e) {
//			// TODO: handle exception
//			showMessage("Errore: Inserisci un numero di calorie valido");
//
//			return;
//		}
    	if(numCalorie!=null && !numCalorie.isEmpty()) {
    		if(model.isDigit(numCalorie)) {
    			txtResult.setText(model.stampaCalorie(numCalorie));
    		}else {
    			showMessage("Errore: Inserisci un numero di calorie valido");
    		}
    	}else {
    		showMessage("Errore: Inserisci un numero di calorie");
    	}

    }

    @FXML
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxIngrediente != null : "fx:id=\"boxIngrediente\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnDietaEquilibrata != null : "fx:id=\"btnDietaEquilibrata\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
	
	private void showMessage(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setContentText(message);
		alert.show();
	}
}
