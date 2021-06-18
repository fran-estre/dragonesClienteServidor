package com.itmo.dragon.shared.commands;

import com.itmo.dragon.client.DragonReader;
import com.itmo.dragon.client.FileReader;
import com.itmo.dragon.shared.entities.Dragon;

public class DataBoxHandler {
    public static boolean getDataBox(String[] parts, DataBox dataBox, StringBuilder comments, boolean isInteractive) {
        switch (parts[0]) {
            case "help":
            case "info":
            case "show":
            case "clear":
            case "save":
            case "print_character":
                return true;
            case "remove_key":
            case "replace_if_greater":
            case "replace_if_lower":
            case "remove_greater_key":
                dataBox = readDataCommandKey(parts, comments);
                return dataBox != null;
            case "count_by_character":
                dataBox = readDataCommandCountByCharacter(parts, comments);
                return dataBox != null;
            case "filter_less_than_killer":
                dataBox = readDataCommandFilterLessThanKiller(parts, comments);
                return dataBox != null;
            case "insert":
                if (!isInteractive)
                    return false;
                dataBox = readDataCommandInsert();
                return dataBox != null;
            case "update":
                if (!isInteractive)
                    return false;
                dataBox = readDataCommandUpdate(parts, comments);
                return dataBox != null;
            case "execute_script":
                if (!isInteractive)
                    return false;
                dataBox = readDataCommandExecuteScript(parts, comments);
                return dataBox != null;
            default:
                comments.append("unknown command");
                return false;
        }
    }

    private static Dragon readDragon() {
        DragonReader dragonReader = new DragonReader();
        return dragonReader.read();
    }

    private static DataBox readDataCommandUpdate(String[] parts, StringBuilder comments) {
        if (parts.length < 2) {
            comments.append("The command is incomplete, you need to enter the key.");
            return null;
        }

        try {
            long key = Long.parseLong(parts[1]);
            Dragon dragonToUpdate = readDragon();
            dragonToUpdate.setId(key);
            DataBox dataBox = new DataBox();
            dataBox.setDragon(dragonToUpdate);
            return dataBox;
        } catch (NumberFormatException e) {
            comments.append("The command is invalid.");
            return null;
        }
    }

    private static DataBox readDataCommandInsert() {
        Dragon dragonToUpdate = readDragon();
        DataBox dataBox = new DataBox();
        dataBox.setDragon(dragonToUpdate);
        return dataBox;
    }

    private static DataBox readDataCommandFilterLessThanKiller(String[] parts, StringBuilder comments) {
        if (parts.length < 2) {
            comments.append("The command is incomplete, you need to enter the killer weight.");
            return null;
        }
        try {
            DataBox dataBox = new DataBox();
            dataBox.setWeight(Long.parseLong(parts[1]));
            return dataBox;
        } catch (NumberFormatException e) {
            comments.append("The command is invalid.");
            return null;
        }
    }

    private static DataBox readDataCommandCountByCharacter(String[] parts, StringBuilder comments) {
        if (parts.length < 2) {
            comments.append("The command is incomplete, you need to enter the character (EVIL, GOOD, CHAOTIC, FICKLE).");
            return null;
        }
        if (!parts[1].equals("EVIL") && !parts[1].equals("GOOD") && !parts[1].equals("CHAOTIC") && !parts[1].equals("FICKLE")) {
            comments.append("The command is invalid.");
            return null;
        }

        DataBox dataBox = new DataBox();
        dataBox.setDragonCharacter(parts[1]);
        return dataBox;
    }

    private static DataBox readDataCommandKey(String[] parts, StringBuilder comments) {
        if (parts.length < 3) {
            comments.append("The command is incomplete, you need to enter the key and the age.");
            return null;
        }

        DataBox dataBox = new DataBox();
        try {
            dataBox.setKey(Long.parseLong(parts[1]));
        } catch (NumberFormatException e) {
            comments.append("The command is invalid.");
            return null;
        }

        try {
            dataBox.setAge(Long.parseLong(parts[2]));
        } catch (NumberFormatException e) {
            comments.append("The command is invalid.");
            return null;
        }
        return dataBox;
    }

    private static DataBox readDataCommandExecuteScript(String[] parts, StringBuilder comments) {
        if (parts.length < 2) {
            comments.append("The command is incomplete, you need to enter the filename that contain the commands.");
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
}
