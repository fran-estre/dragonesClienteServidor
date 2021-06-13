package com.itmo.dragon.shared.commands;

import com.itmo.dragon.shared.DragonCharacter;

import java.io.Serializable;

public class CountByCharacter implements Serializable {
    Command command;
    DragonCharacter character;

    public DragonCharacter getCharacter() {
        return character;
    }

    public void setCharacter(DragonCharacter character) {
        this.character = character;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}

