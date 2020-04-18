package com.avatarduel.view;

import com.avatarduel.model.Element;
import com.avatarduel.model.cards.card.Character;
import com.avatarduel.model.cards.card.SummonedCharacter;
import com.avatarduel.model.gameplay.GameplayChannel;
import com.avatarduel.model.player.Player;
import com.avatarduel.model.player.Power;
import com.avatarduel.model.cards.card.Card;
import com.avatarduel.view.cards.CardDisplay;
//import com.avatarduel.view.cards.CharDisplay;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController{

    @FXML
    private Pane cardPane;
    @FXML
    public Label name1;
    @FXML
    private Label health1;
    @FXML
    private Label name2;
    @FXML
    private Label health2;
    @FXML
    private ScrollPane hand1Pane;
    @FXML
    private HBox hand1HBox;
    @FXML
    private ScrollPane hand2Pane;
    @FXML
    private HBox hand2HBox;
    @FXML
    private AnchorPane board1Pane;
    @FXML
    private AnchorPane board2Pane;
    @FXML
    private Pane power1Pane;
    @FXML
    private Pane power2Pane;
    @FXML
    private Label contoh1;

    private GameplayChannel channel;
    private int cardAmount;

    private Player player1;
    private Player player2;

    public MainPageController(GameplayChannel channel, int cardAmount, String P1, String P2) {

        this.channel = channel;
        this.cardAmount = cardAmount;
        Card cardSample = new Card("Aang", Element.AIR, "None","/com/avatarduel/card/image/character/Aang.png");
        FXMLLoader cardLoader = new FXMLLoader(getClass().getResource("/com/avatarduel/fxml/CardDisplay.fxml"));
        cardLoader.setControllerFactory(c -> new CardDisplay(channel, cardSample, true));
        try {
            System.out.println("MEH");
            Pane card = cardLoader.load();
//            cardPane.getChildren().add(card);
            System.out.println("MEH");
        } catch (Exception e) {
            e.printStackTrace();
        }

//        this.player1 = new Player()


//        //stage.setFullScreen(true);
//        Player p1 = new Player("Hojun", 80, channel);
//        Character card = new Character("Aang", Element.AIR,"Aang is the last surviving Airbender, a monk of the Air Nomads' Southern Air Temple. He is an incarnation of the \"Avatar\", the spirit of light and peace manifested in human form.","com/avatarduel/card/image/character/Aang.png",100,100,100);
//        SummonedCharacter cardS = new SummonedCharacter(card, false, new Player("hengky",80, channel), channel);
//        try {
//
//        }
//        CardDisplay DCard = new CharDisplay(cardS.getCharCard(), cardPane, cardDisW, cardDisH, cardDisPosX, cardDisPosY);
//
//        Power powlist = new Power(gameplay,"hengky");
//        PowerDisplay P = new PowerDisplay(powlist,pane);


    }

    public void handleButtonClick() {

        System.out.println(getClass());
    }
    public Pane getCardPane() {return cardPane;}

}