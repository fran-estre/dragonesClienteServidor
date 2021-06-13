package com.itmo.dragon.shared.commands;

import java.io.Serializable;

public class ExecuteScript implements Serializable {
    Command command;
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
