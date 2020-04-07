package com.avatarduel;

import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;

import com.avatarduel.model.Element;
import com.avatarduel.model.cards.Card;

public class CardDisplay {
    
    private Text name;
    private ImageView element;
    private Text desc;
    private Text attr;

    public CardDisplay(Card C, Pane hbox) {

        Pane box = new Pane();
        box.setPrefSize(300,400);
        Image img = new Image("com/avatarduel/asset/card-character.png");
        BackgroundSize backgroundSize = new BackgroundSize(300, 400, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(img,  
        BackgroundRepeat.NO_REPEAT,  
        BackgroundRepeat.NO_REPEAT,  
        BackgroundPosition.DEFAULT,  
        backgroundSize); 
        Background background = new Background(backgroundImage);
        box.setBackground(background);

        name = new Text();
        name.setText(C.getName());
        String elpath;
        if (C.getElement() == Element.AIR) {
            elpath = "com/avatarduel/asset/elm-air.png";
        }
        else if (C.getElement() == Element.WATER) {
            elpath = "com/avatarduel/asset/elm-water.png";
        }
        else if (C.getElement() ==  Element.FIRE) {
            elpath = "com/avatarduel/asset/elm-fire.png";
        }
        else {
            elpath = "com/avatarduel/asset/elm-earth.png";
        }
        Image image = new Image(elpath);
        element = new ImageView(image);
        element.setFitHeight(35);
        element.setFitWidth(35);
        element.setX(248);
        element.setY(18);
        
        desc = new Text();
        desc.setText(C.getDesc());
        desc.setX(150);
        desc.setY(300);

        box.getChildren().addAll(desc,element);
        hbox.getChildren().add(box);
        
    }
    


}