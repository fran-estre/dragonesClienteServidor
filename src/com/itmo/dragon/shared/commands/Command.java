package com.itmo.dragon.shared.commands;

import java.io.Serializable;

public class Command implements Serializable {
    String name;

    public Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

