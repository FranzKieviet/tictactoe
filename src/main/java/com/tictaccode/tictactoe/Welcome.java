package com.tictaccode.tictactoe;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Welcome{

    public static Scene getScene(Stage primaryStage, TicTacToeApplication app) throws Exception{
        //Create group for all elements:
        Group bkg=new Group();

        //Get the image from the dir
        FileInputStream input = new FileInputStream("src/main/resources/WelcomeScreenBackground.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        //Add to Group:
        bkg.getChildren().add(imageView);


        //Set text:
        String title="Tic Tac Toe";
        Text text = new Text(title);
        //Change its looks:
        text.setFill(Color.ORANGE);
        text.setStroke(Color.GREEN);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 80));
        //Change its location:
        text.setX(25);
        text.setY(250);
        text.setTextOrigin(VPos.CENTER);
        //Add to Group:
        bkg.getChildren().add(text);

        //Add Single player Button:
        Button buttonS = new Button("Single-Player");
        //Set size:
        Label label = new Label("Not clicked");
        buttonS.setPrefSize(150,50);
        buttonS.setLayoutX(75);
        buttonS.setLayoutY(400);

        buttonS.setOnAction(e ->  {
            try {
                app.startSinglePlayerGame(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //Add to Group:
        bkg.getChildren().add(buttonS);

        //Add Multi player Button:
        Button buttonM = new Button("Mutli-Player");
        //Set size:
        buttonM.setPrefSize(150,50);
        buttonM.setLayoutX(275);
        buttonM.setLayoutY(400);
        //Add to Group:
        bkg.getChildren().add(buttonM);

        //Return Scene
        Scene scene =new Scene(bkg, 500, 500);
        return scene;


    }
}
