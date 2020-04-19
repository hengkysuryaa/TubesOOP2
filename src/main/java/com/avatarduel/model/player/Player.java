package com.avatarduel.model.player;
import com.avatarduel.model.gameplay.GameplayChannel;
import com.avatarduel.model.gameplay.Publisher;
import com.avatarduel.model.gameplay.Subscriber;
import com.avatarduel.model.gameplay.BaseEvent;
import com.avatarduel.model.gameplay.events.AttackPlayerEvent;
import com.avatarduel.model.gameplay.events.EndGameEvent;
import com.avatarduel.model.gameplay.events.ResetPowerEvent;
import com.avatarduel.model.gameplay.events.UseLandEvent;
import com.avatarduel.model.gameplay.events.SpendPowerEvent;
import com.avatarduel.model.cards.cardcollection.Deck;
import com.avatarduel.model.cards.cardcollection.Hand;
import com.avatarduel.model.cards.cardcollection.Board;

public class Player implements Publisher, Subscriber,
    AttackPlayerEvent.AttackPlayerEventHandler, ResetPowerEvent.ResetPowerEventHandler,
    UseLandEvent.UseLandEventHandler, SpendPowerEvent.SpendPowerEventHandler {

    protected String name;
    protected Deck deck;
    protected Hand hand;
    protected Board board;
    protected int health;
    protected Power powers;
    protected GameplayChannel channel;

    public Player(String name, int health, GameplayChannel channel) {
        this.name = name;
        this.deck = new Deck(channel, name);
        this.hand = new Hand(channel, name);
        this.board = new Board(channel, name);
        this.health = health;
        this.powers = new Power(channel, name);
        this.channel = channel;
        channel.addSubscriber("ATTACK_PLAYER_EVENT", this);
        channel.addSubscriber("RESET_POWER_EVENT", this);
        channel.addSubscriber("USE_LAND", this);
        channel.addSubscriber("SPEND_POWER_EVENT", this);
    }

    public String getName() {return this.name;}
    public Deck getDeck() {return this.deck;}
    public Hand getHand() {return this.hand;}
    public Board getBoard() {return this.board;}
    public int getHealth() {return this.health;}
    public Power getPower() {return this.powers;}

    public void publish(String topic, BaseEvent event) {
        this.channel.sendEvent(topic, event);
    }

    public void onEvent(BaseEvent e){
        if (e.getClass() == AttackPlayerEvent.class){
            this.onAttackPlayer((AttackPlayerEvent) e);
        } 
        else if (e.getClass() == ResetPowerEvent.class){
            this.onResetPowerEvent((ResetPowerEvent) e);
        }
        else if (e.getClass() == SpendPowerEvent.class){
            this.onSpendPowerEvent((SpendPowerEvent) e);
        }  
    }


    @Override
    public void onAttackPlayer(AttackPlayerEvent e) {
        if(this.name == e.target){
            this.health -= e.amount;
            if (this.health <= 0){
                this.publish("END_GAME", new EndGameEvent(this.name));
            }
        }
    }

    @Override
    public void onResetPowerEvent(ResetPowerEvent e) {
        this.powers.resetAllPower();
    }

    @Override
    public void onUseLandEvent(UseLandEvent e){
        this.powers.addCapacity(e.land.getElement(), 1);
    }

    @Override
    public void onSpendPowerEvent(SpendPowerEvent e) {
        if (e.sender.equals(this.name)){
            this.powers.usePower(e.element, e.amount);
        }    
    }

}
