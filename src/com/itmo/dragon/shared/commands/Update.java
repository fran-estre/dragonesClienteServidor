package com.itmo.dragon.shared.commands;

import com.itmo.dragon.shared.Dragon;

import java.io.Serializable;

public class Update implements Serializable {
    Command command;
    Dragon dragon;

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Dragon getDragon() {
        return dragon;
    }

    public void setDragon(Dragon dragon) {
        this.dragon = dragon;
    }
}

