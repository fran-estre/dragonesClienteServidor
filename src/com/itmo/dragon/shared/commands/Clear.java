package com.itmo.dragon.shared.commands;

import java.io.Serializable;

public class Clear implements Serializable {
    Command command;

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}

