package com.company;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.net.URL;
import java.util.ResourceBundle;

//para tener el control
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControladorPantallaPC implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tamaño.setItems(((FXCollections.observableArrayList(
                 "8", "9","10")
        )));

    }
    @FXML
    TextField nombre;
    @FXML
    ChoiceBox tamaño;


    public void IniciarJuego(ActionEvent actionEvent) {
        try {
            PantInicio.nombres.add(nombre.getText());
            PantInicio.tamaño = Integer.parseInt(tamaño.getValue().toString());
            System.out.println(PantInicio.nombres.get(0));
            PantInicio.root.getChildren().remove(PantInicio.grid.get(1));
            SpaceMain game = new SpaceMain();
            PantInicio.root.getChildren().add(game.createContent());
        }catch (java.lang.NullPointerException e){
            Alert alert2 = new Alert(Alert.AlertType.WARNING, "Por favor, rellena todos los campos");
            alert2.showAndWait();
        }

    }

    public void Retroceso(ActionEvent actionEvent) {
        PantInicio.root.getChildren().remove(PantInicio.grid.get(1));
        PantInicio.root.getChildren().add(PantInicio.grid.get(0));
    }
}
