package com.itmo.dragon.shared.commands;

import java.io.Serializable;

public class Command implements Serializable {
    String name;
    Object dataCommand;

    public Command(String name, Object dataCommand) {
        this.name = name;
        this.dataCommand = dataCommand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDataCommand() {
        return dataCommand;
    }

    public void setDataCommand(Object dataCommand) {
        this.dataCommand = dataCommand;
    }
}