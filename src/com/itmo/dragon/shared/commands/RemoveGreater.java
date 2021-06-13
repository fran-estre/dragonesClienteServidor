package com.itmo.dragon.shared.commands;

import com.itmo.dragon.shared.DragonCharacter;

import java.io.Serializable;

public class RemoveGreater implements Serializable {
    Command command;
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}

