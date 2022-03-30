package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWorld;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	Dictionary dictionary;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbLanguage;

    @FXML
    private Label msgerrori;

    @FXML
    private Label msgtempo;

    @FXML
    private TextArea txtcontrollato;

    @FXML
    private TextArea txtdacontrollare;

    @FXML
    void doClearText(ActionEvent event) {
    	txtcontrollato.clear();
    	txtdacontrollare.clear();
    	msgtempo.setText("");
    	msgerrori.setText("");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	String s = txtdacontrollare.getText();
    	s.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]","");
    	String ss[] = s.split(" ");
    	List<String> parole = new ArrayList<>();
    	for(String f: ss) {
    		parole.add(f);
    	}
    	String lingua = cmbLanguage.getValue();
    	dictionary.loadDictionary(lingua);
    	double start, end;
    	start = System.nanoTime();
    	List<RichWorld> par = dictionary.spellCheckTextLinear(parole);
    	end = System.nanoTime();
    	int nsbagliate=0;
    	for(RichWorld r : par) {
    		if(r.getCorretta()==false) {
    			txtcontrollato.appendText(r.getParola()+" ");
    			nsbagliate++;
    		}
    	}
    	msgerrori.setText("The text contains "+nsbagliate+" errors");
    	msgtempo.setText("tempo= "+(end-start)/1000000+" ms");
    }

    @FXML
    void initialize() {
        assert cmbLanguage != null : "fx:id=\"cmbLanguage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert msgerrori != null : "fx:id=\"msgerrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert msgtempo != null : "fx:id=\"msgtempo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtcontrollato != null : "fx:id=\"txtcontrollato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtdacontrollare != null : "fx:id=\"txtdacontrollare\" was not injected: check your FXML file 'Scene.fxml'.";
        cmbLanguage.getItems().clear();
        cmbLanguage.getItems().add("English");
        cmbLanguage.getItems().add("Italian");
    }
    
    public void setDictionary(Dictionary model) {
    	this.dictionary = model;
    }
}


