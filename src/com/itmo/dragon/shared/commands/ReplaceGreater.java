package com.itmo.dragon.shared.commands;

import java.io.Serializable;

public class ReplaceGreater implements Serializable {
    Command command;
    Long id;
    Long age;

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

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

