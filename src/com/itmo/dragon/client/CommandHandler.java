package com.itmo.dragon.client;

import com.itmo.dragon.shared.commands.SerializationHandler;
import com.itmo.dragon.shared.commands.*;

import java.io.*;
import java.util.Scanner;

/**
 * handle the commands given by the user.
 */
public class CommandHandler {
    public static void handleConsoleCommand() {
        Scanner keyboard = new Scanner(System.in);
        String currentCommand;
        System.out.println("Enter the command: ");
        while ((currentCommand = keyboard.nextLine()) != "exit") {
            String[] parts = currentCommand.split(" ");
            DataBox dataBox = new DataBox();
            StringBuilder comments = new StringBuilder();
            if (DataBoxHandler.getDataBox(parts, dataBox, comments, true))
                sendCommand(parts[0], dataBox);
            else
                System.out.println(comments.toString());
            System.out.println("Enter the new command: ");
        }
        System.out.println("Goodbye.");
    }

    private static String sendCommand(String name, DataBox dataCommand) {
        Command command = new Command(name, dataCommand);
        byte[] data = SerializationHandler.serialize(command);
        if (data == null)
            return "Se produjo un error al serializar";

        try {
            ClientApp.getCommunication().send(data);
            // TODO :  como obtengo la respuesta
            return "Ok";
        } catch (IOException e) {
            e.printStackTrace();
            return "Se produjo un error en el envío de datos";
        }
    }
}

