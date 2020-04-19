package com.avatarduel.model.cards.card;

import java.util.ArrayList;

import com.avatarduel.model.Phase;
import com.avatarduel.model.gameplay.events.*;
import com.avatarduel.model.gameplay.BaseEvent;
import com.avatarduel.model.gameplay.GameplayChannel;
import com.avatarduel.model.gameplay.Publisher;
import com.avatarduel.model.gameplay.Subscriber;
import com.avatarduel.model.Phase;
import com.avatarduel.view.AlertPlayer;
import javafx.scene.control.Alert;

/**
 * Merupakan kelas yang mengimplementasi interface ICharSummoned (kartu yang disummon ke arena)
 */
public class SummonedCharacter implements ICharSummoned, Publisher, Subscriber,
        SkillCardAttachedEvent.SkillCardAttachedEventHandler, 
        AttackCharacterEvent.AttackCharacterEventHandler,
        RepositionCharacterEvent.RepositionCharacterEventHandler,
        DrawEvent.DrawEventHandler
        {

    private Character CharCard;
    private boolean isAttack; // true jika dalam keadaan attack dan false bila dalam keadaan defense
    private ArrayList<Skill> attachedSkill;
    private String owner;
    private boolean isPowerUp, isAlreadyAttack;
    private GameplayChannel gameplayChannel;
    private int auraAtt, auraDef;

    public SummonedCharacter(Character charCard, boolean isAttack, String player, GameplayChannel gameplayChannel) {
        this.attachedSkill = new ArrayList<Skill>();
        this.CharCard = charCard;
        this.isAttack = isAttack;
        this.owner = player;
        this.gameplayChannel = gameplayChannel;
        this.isPowerUp = false;
        this.isAlreadyAttack = true;
        this.auraAtt = 0;
        this.auraDef = 0;
        this.gameplayChannel.addSubscriber("ATTACK_CHARACTER_EVENT", this);
        this.gameplayChannel.addSubscriber("SUMMON_CHAR_CLICKED", this);
        this.gameplayChannel.addSubscriber("ATTACH_SKILL", this);
        this.gameplayChannel.addSubscriber("DRAW_PHASE", this);
        this.gameplayChannel.addSubscriber("REPOSITION_CHARACTER", this);
    }

    public String getOwner() {return this.owner;}
    public Character getCharCard() {return this.CharCard;}
    public boolean getPosition() {return this.isAttack;}
    public ArrayList<Skill> getAttachedSkill() {return this.attachedSkill;}
    public void setAlreadyAttack() {this.isAlreadyAttack = true;}
    public boolean getAlreadyAttack() { return this.isAlreadyAttack; }
    public int getAuraAtt() {return this.auraAtt;}
    public int getAuraDef() {return this.auraDef;}

    public void rotate() {this.isAttack = !this.isAttack;}

    public int getPositionValue() {
        if (isAttack) {
            return CharCard.getAttack() + this.auraAtt;
        } else {
            return CharCard.getDefense() + this.auraDef;
        }
    }

    @Override
    public void publish(String topic, BaseEvent event) {
        this.gameplayChannel.sendEvent(topic, event);
    }

    public void doAttack(SummonedCharacter target) {
        this.publish("ATTACK_CHARACTER_EVENT", new AttackCharacterEvent(this, target));
    }
    public void doAttackPlayer(String target) {
        this.publish("ATTACK_PLAYER_EVENT", new AttackPlayerEvent(this.CharCard.getAttack(), target));
    }

    public void doDiscardSkill(Skill S) {
        this.publish("DISCARD_SKILL", new DiscardSkillEvent(this, S));
    }

    public void destroy() {
        // TODO remove this card and remove every skill attached
        for (Skill skill : attachedSkill) {
            doDiscardSkill(skill);
        }
        this.publish("DESTROY_CHARACTER_EVENT", new DestroyCharacterEvent(this));
    }


    @Override
    public void onEvent(BaseEvent event) {
        if (event instanceof AttackCharacterEvent){
            this.onAttackCharacter((AttackCharacterEvent) event);
        }
        else if (event instanceof SkillCardAttachedEvent){
            this.onSkillCardAttached((SkillCardAttachedEvent) event);
        }
        else if (event instanceof DrawEvent){
            this.onDrawEvent((DrawEvent) event);
        }
        else if (event instanceof RepositionCharacterEvent){
            this.onRepositionCharacterEvent((RepositionCharacterEvent) event);
        }
    }

    @Override
    public void onSkillCardAttached(SkillCardAttachedEvent e) {
        if (this.equals(e.charCard)) {
            this.attachedSkill.add(e.skillCard);
            if(e.skillCard instanceof Aura){
                this.auraAtt = this.auraAtt + ((Aura) e.skillCard).getAttVal();
                this.auraDef = this.auraDef + ((Aura) e.skillCard).getDefVal();
            }
            if(e.skillCard instanceof Destroy){
                this.destroy();
            }
            
            if(e.skillCard instanceof PowerUp){
                this.isPowerUp = true;
            }
        }
    }


    @Override
    public void onAttackCharacter(AttackCharacterEvent e) {
        if (this == e.toCard) {
            if (e.fromCard.getPosition()) { // karakter yg menyerang harus dalam posisi attack
                if (e.toCard.getPositionValue() < e.fromCard.getPositionValue()) // attack/defense value this < attack fromCard
                {
                    if (e.toCard.getPosition()){ // karakter this dalam posisi attack (isAttack == true)

                        this.publish("ATTACK_PLAYER_EVENT", new AttackPlayerEvent(
                            e.fromCard.getPositionValue() - e.toCard.getPositionValue(), 
                            e.toCard.owner
                            ));
                    }
                    else if(e.fromCard.isPowerUp){ 

                        this.publish("ATTACK_PLAYER_EVENT", new AttackPlayerEvent(
                            e.fromCard.getPositionValue() - e.toCard.getPositionValue(), 
                            e.toCard.owner
                            ));
                    
                    }
                    e.fromCard.setAlreadyAttack();
                    this.destroy();
                } else {
                    AlertPlayer fail = new AlertPlayer("Attack failed! Your opponent is stronger than you think!", Alert.AlertType.WARNING, "Attack Failed");
                    fail.show();
                    publish("ATTACK_FAIL", new AttackFailEvent(e.fromCard.owner));
                }
            }
        }
    }


    @Override
    public void onDrawEvent(DrawEvent e) {
        if((this.gameplayChannel.phase == Phase.DRAW_PHASE)
             && this.gameplayChannel.activePlayer.getName() == this.owner) {
            this.isAlreadyAttack = false;
        }
    }

    @Override
    public void onRepositionCharacterEvent(RepositionCharacterEvent e) {
        if (e.SC.equals(this) && e.owner == this.owner) {
            rotate();
        }
    }

}