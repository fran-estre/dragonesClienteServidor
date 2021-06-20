package com.itmo.dragon.shared.commands;

import java.io.Serializable;

public class Command implements Serializable {
    private DataBox dataCommand;
    private CommandType commandType;

    public Command() {

    }

    public Command(CommandType commandType, DataBox dataCommand) {
        this.commandType = commandType;
        this.dataCommand = dataCommand;
    }

    public DataBox getDataCommand() {
        return dataCommand;
    }

    public void setDataCommand(DataBox dataCommand) {
        this.dataCommand = dataCommand;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }
}
