package com.avatarduel;

import java.awt.Dimension;
import java.awt.Toolkit;
//import javafx.application.Application;
//import javafx.scene.Scene; 
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
//import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.avatarduel.model.Element;
import com.avatarduel.model.Land;
import com.avatarduel.util.CSVReader;

public class AvatarDuel extends Application {
/*  private static final String LAND_CSV_FILE_PATH = "card/data/land.csv";


  public void loadCards() throws IOException, URISyntaxException {
    File landCSVFile = new File(getClass().getResource(LAND_CSV_FILE_PATH).toURI());
    CSVReader landReader = new CSVReader(landCSVFile, "\t");
    landReader.setSkipHeader(true);
    List<String[]> landRows = landReader.read();
    for (String[] row : landRows) {
      Land l = new Land(row[1], row[3], Element.valueOf(row[2]));
    }
  } */

  @Override
  public void start(Stage stage) {
    MainScreen(stage);
   
    /*
    Scene scene = new Scene(root, 1280, 720);

    stage.setTitle("Avatar Duel");
    stage.setScene(scene);
    stage.show();

    try {
      this.loadCards();
      text.setText("Avatar Duel!");
    } catch (Exception e) {
      text.setText("Failed to load cards: " + e);
    } */
  }

  public void MainScreen(Stage stage) {
    HBox hbox = new HBox();
    Scene scene = new Scene(hbox);
    Image image = new Image("com/avatarduel/asset/board.png");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    BackgroundSize backgroundSize = new BackgroundSize(screenSize.getWidth(), screenSize.getHeight(), false, false, false, false);
    BackgroundImage backgroundImage = new BackgroundImage(image,  
    BackgroundRepeat.NO_REPEAT,  
    BackgroundRepeat.NO_REPEAT,  
    BackgroundPosition.DEFAULT,  
    backgroundSize); 
    Background background = new Background(backgroundImage);
    hbox.setBackground(background);
    stage.setTitle("Avatar Duel");
    stage.setScene(scene); 
    //stage.setFullScreen(true);
    stage.setMaximized(true); 
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}