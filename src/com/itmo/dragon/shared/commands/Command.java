package com.itmo.dragon.shared.commands;

import java.io.Serializable;

public class Command implements Serializable {
    String name;
    DataBox dataCommand;

    public Command(String name, DataBox dataCommand) {
        this.name = name;
        this.dataCommand = dataCommand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataBox getDataCommand() {
        return dataCommand;
    }

    public void setDataCommand(DataBox dataCommand) {
        this.dataCommand = dataCommand;
    }
}