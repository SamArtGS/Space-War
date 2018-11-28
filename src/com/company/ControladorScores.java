package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.*;
import java.net.URL;
import javafx.scene.control.TextArea;
import java.util.ResourceBundle;

public class ControladorScores implements Initializable {
    public void IrInicio(ActionEvent actionEvent) {
        PantInicio.root.getChildren().remove(PantInicio.grid.get(5));
        PantInicio.root.getChildren().add(PantInicio.grid.get(0));
    }
    @FXML
    TextArea pop;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FileInputStream fis = new FileInputStream(PantInicio.puntaj);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String linea = "";
            while ((linea=br.readLine())!=null){
                this.pop.setText(linea+"\n"+this.pop.getText()+"\n");
            }
        } catch (FileNotFoundException e) {
            // ESCRIBIR Archivo en caso no creado PantInicio.puntaj
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
