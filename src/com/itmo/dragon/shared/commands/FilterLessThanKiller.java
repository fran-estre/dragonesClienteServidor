package com.itmo.dragon.shared.commands;

import com.itmo.dragon.shared.DragonCharacter;

import javax.xml.crypto.Data;
import java.io.Serializable;

public class FilterLessThanKiller implements Serializable {
    Command command;
    String killerName;

    public String getKillerName() {
        return killerName;
    }

    public void setKillerName(String killerName) {
        this.killerName = killerName;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}

