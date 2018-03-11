package com.codenames.attilahanko.event.player;

import java.util.List;

public class CardSelected {

    private List<Integer> selected;
    private String color;

    public CardSelected(List<Integer> selected, String color) {
        this.selected = selected;
        this.color = color;
    }

    public List<Integer> getSelected() {
        return selected;
    }

    public String getColor() {
        return color;
    }


}