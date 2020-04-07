package com.avatarduel.model.gameplay;

import java.util.ArrayList;
import java.util.HashMap;

public class GameplayChannel implements EventChannel {
    
    private HashMap<String, ArrayList<Subscriber>> listSubscriber;

    public GameplayChannel() {
        this.listSubscriber = new HashMap<>();
      }
    
    public void addSubscriber(String topic, Subscriber s) {
    if(this.listSubscriber.get(topic) == null){
        this.listSubscriber.put(topic, new ArrayList<>());
    }
    this.listSubscriber.get(topic).add(s);
    }

    public void sendEvent(String topic, Event event) {
      ArrayList<Subscriber> list = this.listSubscriber.get(topic);
      if(list != null)
        for (Subscriber subscriber : list) {
          subscriber.onEvent(event);
        }
    }

}