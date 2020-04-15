package com.avatarduel.model.gameplay.events;

import com.avatarduel.model.gameplay.BaseEvent;
import com.avatarduel.model.Element;
import com.avatarduel.model.cards.card.SummonedCharacter;

public class AttackPlayerEvent implements BaseEvent {

    public int amount;
    public String target;

    public AttackPlayerEvent(int amount, String target){
        this.amount = amount;
        this.target = target;
    }

    public interface AttackPlayerEventHandler {
        void onAttackPlayer(AttackPlayerEvent e);
    }
    
    public void execute(){

    }
}
