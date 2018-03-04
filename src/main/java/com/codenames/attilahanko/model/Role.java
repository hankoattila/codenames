package com.codenames.attilahanko.model;

public enum Role {
    PLAYER("PLAYER"),BOSS("BOSS"),ADMIN("ADMIN");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
