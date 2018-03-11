package com.codenames.attilahanko.event.player;

import java.util.List;
import java.util.Map;

public class CardSelected {


    private List<Integer> listOfIndex;
    private Map<Integer, String> colors;

    public CardSelected(List<Integer> listOfIndex, Map<Integer, String> colors) {
        this.listOfIndex = listOfIndex;
        this.colors = colors;
    }

    public List<Integer> getListOfIndex() {
        return listOfIndex;
    }

    public Map<Integer, String> getColors() {
        return colors;
    }
}