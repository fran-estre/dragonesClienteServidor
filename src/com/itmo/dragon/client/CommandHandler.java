package com.itmo.dragon.client;

import com.itmo.dragon.shared.entities.Dragon;
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
            String commandText = parts[0];
            Object dataCommand;
            switch (commandText) {
                case "help":
                case "info":
                case "show":
                case "clear":
                case "save":
                case "print_character":
                    sendCommand(commandText);
                    break;
                case "execute_script":
                    dataCommand = readDataCommandExecuteScript(parts);
                    if (dataCommand == null)
                        break;
                    sendCommand(commandText, dataCommand);
                    break;
                case "remove_key":
                case "replace_if_greater":
                case "replace_if_lower":
                case "remove_greater_key":
                    dataCommand = readDataCommandKey(parts);
                    if (dataCommand == null)
                        break;
                    sendCommand(commandText, dataCommand);
                    break;
                case "count_by_character":
                    dataCommand = readDataCommandCountByCharacter(parts);
                    if (dataCommand == null)
                        break;
                    sendCommand(commandText, dataCommand);
                    break;
                case "filter_less_than_killer":
                    dataCommand = readDataCommandFilterLessThanKiller(parts);
                    if (dataCommand == null)
                        break;
                    sendCommand(commandText, dataCommand);
                    break;
                case "insert":
                    dataCommand = readDataCommandInsert();
                    if (dataCommand == null)
                        break;
                    sendCommand(commandText, dataCommand);
                    break;
                case "update":
                    dataCommand = readDataCommandUpdate(parts);
                    if (dataCommand == null)
                        break;
                    sendCommand(commandText, dataCommand);
                    break;
                default:
                    System.out.println("unknown command");
                    break;
            }
            System.out.println("Enter the new command: ");
        }
        System.out.println("Goodbye.");
    }

    private static Dragon readDragon() {
        DragonReader dragonReader = new DragonReader();
        return dragonReader.read();
    }

    private static Object readDataCommandUpdate(String[] parts) {
        if (parts.length < 2) {
            System.out.println("The command is incomplete, you need to enter the key.");
            return null;
        }

        try {
            Long key = Long.parseLong(parts[1]);
            Dragon dragonToUpdate = readDragon();
            dragonToUpdate.setId(key);
            return dragonToUpdate;
        } catch (NumberFormatException e) {
            System.out.println("The command is invalid.");
            return null;
        }
    }

    private static Object readDataCommandInsert() {
        return readDragon();
    }

    private static Object readDataCommandFilterLessThanKiller(String[] parts) {
        if (parts.length < 2) {
            System.out.println("The command is incomplete, you need to enter the killer weight.");
            return null;
        }
        try {
            DataBox dataBox = new DataBox();
            dataBox.setWeight(Long.parseLong(parts[1]));
            return dataBox;
        } catch (NumberFormatException e) {
            System.out.println("The command is invalid.");
            return null;
        }
    }

    private static Object readDataCommandCountByCharacter(String[] parts) {
        if (parts.length < 2) {
            System.out.println("The command is incomplete, you need to enter the character (EVIL, GOOD, CHAOTIC, FICKLE).");
            return null;
        }
        if (parts[1] != "EVIL" && parts[1] != "GOOD" && parts[1] != "CHAOTIC" && parts[1] != "FICKLE") {
            System.out.println("The command is invalid.");
            return null;
        }

        DataBox dataBox = new DataBox();
        dataBox.setDragonCharacter(parts[1]);
        return dataBox;
    }

    private static Object readDataCommandKey(String[] parts) {
        if (parts.length < 3) {
            System.out.println("The command is incomplete, you need to enter the key and the age.");
            return null;
        }

        DataBox dataBox = new DataBox();
        try {
            dataBox.setKey(Long.parseLong(parts[1]));
        } catch (NumberFormatException e) {
            System.out.println("The command is invalid.");
            return null;
        }

        try {
            dataBox.setAge(Long.parseLong(parts[2]));
        } catch (NumberFormatException e) {
            System.out.println("The command is invalid.");
            return null;
        }
        return dataBox;
    }

    private static Object readDataCommandExecuteScript(String[] parts) {
        if (parts.length < 2) {
            System.out.println("The command is incomplete, you need to enter the filename that contain the commands.");
            return null;
        }
        FileReader fileReader = new FileReader();
        String dataFile = fileReader.read(parts[1]);
        if (dataFile == null)
            return null;
        DataBox dataBox = new DataBox();
        dataBox.setDataFile(dataFile);
        return dataBox;
    }

    private static String sendCommand(String name) {
        return sendCommand(name, null);
    }

    private static String sendCommand(String name, Object dataCommand) {
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

