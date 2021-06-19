package com.itmo.dragon.client;

import com.itmo.dragon.shared.commands.Command;
import com.itmo.dragon.shared.commands.DataBox;
import com.itmo.dragon.shared.commands.SerializationHandler;

import java.io.IOException;

public class CommandSender {
    public String sendCommand(Command command) {
        byte[] data = SerializationHandler.serialize(command);
        if (data == null)
            return "There was an error while serialization.";
        try {
            ClientApp.getCommunication().send(data);
            // TODO :  como obtengo la respuesta
            return "Ok";
        } catch (IOException e) {
            e.printStackTrace();
            return "There was an error while sending data.";
        }
    }
}
