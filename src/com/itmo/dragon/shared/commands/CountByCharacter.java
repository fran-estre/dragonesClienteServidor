package com.itmo.dragon.shared.commands;

import com.itmo.dragon.shared.DragonCharacter;

import java.io.Serializable;

public class CountByCharacter implements Serializable {
    Command command;
    String character;

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}

