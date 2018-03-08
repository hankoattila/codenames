package com.codenames.attilahanko.model.game;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;


    private boolean isFlopped = false;
    private boolean isSelected = false;

    public Card() {
    }

    public Card(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public boolean isFlopped() {
        return isFlopped;
    }

    public void setFlopped(boolean flopped) {
        isFlopped = flopped;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
