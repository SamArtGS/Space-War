package com.company;


import java.awt.*;
import java.awt.font.ImageGraphicAttribute;
import java.io.*;
import java.util.Random;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.animation.AnimationTimer;
import com.company.Board.Cell;
import javafx.scene.image.Image;

import javafx.scene.canvas.Canvas;//lienzo
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.WindowEvent;


public class SpaceMain extends Application {

    static Text text4 = new Text("120");
    private boolean running = false;
    private Board enemyBoard, playerBoard;
    static int score = 10;
    private int shipsToPlace = 5;

    private boolean enemyTurn = false;

    private Random random = new Random();
    Text text3;
    public Parent createContent(){
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1000, 750);

        Canvas canvas = new Canvas(2000,2000);
        root.getChildren().add(canvas);
        GraphicsContext graphic = canvas.getGraphicsContext2D();
        Image mb = new Image("AssetsSpaceWar/fondo.png");

        Image mb2 = new Image("AssetsSpaceWar/Space War.png");
        Image mb3 = new Image("AssetsSpaceWar/LogoPOOm.png");


        Image [] imageArray = new Image[4];
        for (int i = 5; i < 9; i++)
            imageArray[i-5] = new Image("AssetsSpaceWar/Nave" + i + ".png");


        enemyBoard = new Board(true, event -> {
            if (!running)
                return;

            Cell cell = (Cell) event.getSource();
            if (cell.wasShot)
                return;
            if (cell.wasShot)
                PantInicio.score++;
            enemyTurn = !cell.shoot();

            if (enemyBoard.ships == 0) {
                PantInicio.root.getChildren().remove(root);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "GANASTE. ¿Deseas jugar otra vez? ", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    PantInicio.puntajes.add(Integer.parseInt(text3.getText()));
                    PantInicio.root.getChildren().add(PantInicio.grid.get(0));
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream("puntajes.txt",true);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    PrintStream ls = new PrintStream(fos);
                    for (String s: PantInicio.nombres)
                        ls.print(s + "   ");
                    ls.println(text3.getText());
                }if(alert.getResult() == ButtonType.NO){
                    System.exit(0);
                }

            }

            if (enemyTurn&&playerBoard.ships!=0) {
                try {
                    enemyMove();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        playerBoard = new Board(false, event -> {
            if (running)
                return;

            Cell cell = (Cell) event.getSource();
            if (playerBoard.placeShip(new Ship(shipsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
                if (--shipsToPlace == 0) {
                    startGame();
                }
                if(playerBoard.ships==0){
                    PantInicio.root.getChildren().removeAll();
                    PantInicio.root.getChildren().add(PantInicio.grid.get(0));
                }
                if(Integer.parseInt(text4.getText())==0){
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Tiempo agortado! ¿Deseas jugar otra vez?", ButtonType.YES, ButtonType.NO);
                    alert2.showAndWait();
                    if (alert2.getResult() == ButtonType.YES) {
                        PantInicio.puntajes.add(Integer.parseInt(text3.getText()));
                        PantInicio.root.getChildren().add(PantInicio.grid.get(0));
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream("puntajes.txt",true);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        PrintStream ls = new PrintStream(fos);
                        for (String s: PantInicio.nombres)
                            ls.print(s + "   ");
                        ls.println(PantInicio.puntajes);
                    }if(alert2.getResult() == ButtonType.NO){
                        System.exit(0);
                    }
                }
            }
        });
        ImageView img1 = new ImageView();
        final Image img = new Image("AssetsSpaceWar/Logoj.png");

        ImageView nave1 = new ImageView();
        final Image Nave1 = new Image("AssetsSpaceWar/Nave1.png");
        nave1.setImage(Nave1);
        ImageView nave2 = new ImageView();
        final Image Nave2 = new Image("AssetsSpaceWar/Nave4.png");
        nave2.setImage(Nave2);
        ImageView nave3 = new ImageView();
        final Image Nave3 = new Image("AssetsSpaceWar/Nave5.png");
        nave3.setImage(Nave3);
        ImageView nave4 = new ImageView();
        final Image Nave4 = new Image("AssetsSpaceWar/Nave8.png");
        nave4.setImage(Nave4);

        Text text = new Text("Naves disponibles");
        text.setFont(Font.font("Arkitech",10));
        text.setFill(Color.WHITE);

        Text text2 = new Text("Space War");
        text2.setFont(Font.font("Space Age",30));
        text2.setFill(Color.WHITE);

        text2.setLayoutX(750);
        text2.setLayoutY(60);


        text3 = new Text(PantInicio.score.toString());
        text3.setFont(Font.font("Space Age",30));
        text3.setFill(Color.WHITE);
        text3.setLayoutX(850);
        text3.setLayoutY(100);

        if(text4.getText()!=null) {
            text4.setFont(Font.font("Space Age", 30));
            text4.setFill(Color.WHITE);
            text4.setLayoutX(550);
            text4.setLayoutY(100);
        }
        javafx.scene.control.Button salir = new Button("Salir");
        salir.setLayoutX(900);
        salir.setLayoutY(500);

        salir.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                PantInicio.puntajes.add(Integer.parseInt(text3.getText()));
                PantInicio.root.getChildren().remove(root);
                PantInicio.root.getChildren().add(PantInicio.grid.get(0));
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream("puntajes.txt",true);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                PrintStream ls = new PrintStream(fos);
                for (String s: PantInicio.nombres)
                    ls.print(s + "   ");
                ls.println(text3.getText());
            }
        });



        GridPane grids = new GridPane();
        grids.setHgap(2);
        grids.setVgap(6);

        grids.add(text, 1, 0);
        grids.add(nave1, 1, 1);
        grids.add(nave2, 1, 2);
        grids.add(nave3, 1, 3);
        grids.add(nave4, 1, 4);

        Text a = new Text("1");
        Text b = new Text("2");
        Text c = new Text("3");
        Text d = new Text("4");
        a.setFill(Color.WHITE);
        b.setFill(Color.WHITE);
        c.setFill(Color.WHITE);
        d.setFill(Color.WHITE);

        a.setFont(Font.font("Space Age", 20));
        b.setFont(Font.font("Space Age", 20));
        c.setFont(Font.font("Space Age", 20));
        d.setFont(Font.font("Space Age", 20));


        grids.add(a, 2, 1);
        grids.add(b, 2, 2);
        grids.add(c, 2, 3);
        grids.add(d, 2, 4);



        grids.setLayoutX(40);
        grids.setLayoutY(250);
        img1.setImage(img);

        HBox hbox = new HBox(60, enemyBoard, playerBoard);

        hbox.setAlignment(Pos.CENTER);
        hbox.setLayoutX(250);
        hbox.setLayoutY(150);



        img1.setLayoutX(30);
        img1.setLayoutY(30);
        img1.setFitHeight(64);
        img1.setFitWidth(50);




        root.getChildren().addAll(hbox,img1,grids,text2,salir,text3,text4);
        return root;
    }

    private void enemyMove() throws FileNotFoundException {
        while (enemyTurn) {
            int x = random.nextInt(PantInicio.tamaño);
            int y = random.nextInt(PantInicio.tamaño);

            Cell cell = playerBoard.getCell(x, y);
            if (cell.wasShot)
                PantInicio.score++;
            if (cell.wasShot)
                continue;

            enemyTurn = cell.shoot();

            if (playerBoard.ships == 0&&tope==0) {
                tope++;
                PantInicio.puntajes.add(Integer.parseInt(text3.getText()));
                ControladorPantallaPR.reloj.destroy();
                PantInicio.puntajes.add(score);
                Alert alert4 = new Alert(Alert.AlertType.CONFIRMATION, "PERDISTE. ¿Deseas jugar otra vez? ", ButtonType.YES, ButtonType.NO);
                alert4.showAndWait();
                if (alert4.getResult() == ButtonType.YES) {
                    PantInicio.root.getChildren().removeAll();
                    PantInicio.root.getChildren().add(PantInicio.grid.get(0));
                    FileOutputStream fos = new FileOutputStream("puntajes.txt",true);
                    PrintStream ls = new PrintStream(fos);
                    for (String s: PantInicio.nombres)
                        ls.print(s + "   ");
                    ls.println(PantInicio.puntajes);
                }if(alert4.getResult() == ButtonType.NO){
                    System.exit(0);
                }
            }
        }
    }
int tope=0;
    private void startGame() {
        // place enemy ships
        int type = 5;

        while (type > 0) {
            int x = random.nextInt(PantInicio.tamaño);
            int y = random.nextInt(PantInicio.tamaño);

            if (enemyBoard.placeShip(new Ship(type, Math.random() < 0.5), x, y)) {
                type--;
            }
        }
        running = true;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Space War");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.getIcons().add(new Image("AssetsSpaceWar/Logoj.png"));
    }

    public static void main(String[] arg) {
        launch(arg);
    }
    public void salir(ActionEvent actionEvent) {
        PantInicio.root.getChildren().remove(this);
        PantInicio.root.getChildren().add(PantInicio.grid.get(0));
        ControladorPantallaPR.reloj.stop();



    }
}