package com.codenames.attilahanko.event.player;

public class CardDTO {


    private String value;
    private boolean flopped;
    private boolean selected;

    public CardDTO(String value, boolean flopped, boolean selected) {
        this.value = value;
        this.flopped = flopped;
        this.selected = selected;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isFlopped() {
        return flopped;
    }

    public void setFlopped(boolean flopped) {
        this.flopped = flopped;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
