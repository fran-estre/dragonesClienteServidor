package com.itmo.dragon.server;

import com.itmo.dragon.shared.commands.Command;
import com.itmo.dragon.shared.commands.DataBox;
import com.itmo.dragon.shared.commands.DataBoxHandler;
import com.itmo.dragon.shared.entities.Dragon;
import com.itmo.dragon.shared.entities.DragonCharacter;
import com.itmo.dragon.shared.entities.DragonCharacterHelper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ProcessHandler {
    public static String processCommand(Command command) {
        return switch (command.getName()) {
            case "help" -> help();
            case "info" -> info();
            case "show" -> show();
            case "clear" -> clear();
            case "save" -> save();
            case "print_character" -> printCharacter();
            case "remove_key" -> remove(command);
            case "execute_script" -> executeScript(command);
            case "replace_if_greater" -> replaceIfGreater(command);
            case "replace_if_lower" -> replaceIfLower(command);
            case "remove_greater_key" -> remove_greater_key(command);
            case "count_by_character" -> count_by_character(command);
            case "filter_less_than_killer" -> filterLessThanKiller(command);
            case "insert" -> insert(command);
            case "update" -> update(command);
            default -> "unknown command";
        };
    }

    private static String executeScript(Command command) {
        StringBuilder dataFile = new StringBuilder();
        dataFile.append(command.getDataCommand().getDataFile());
        if (dataFile.length() <= 0) {
            return "The file was empty.";
        }

        String line;
        StringBuilder output = new StringBuilder();

        while ((line = getLine(dataFile)) != null) {
            output.append("\n").append(line).append("\n");
            output.append(executeCommand(line));
        }
        return output.toString();
    }

    private static String executeCommand(String currentCommand) {
        String[] parts = currentCommand.split(" ");
        DataBox dataBox = new DataBox();
        StringBuilder comments = new StringBuilder();
        if (DataBoxHandler.getDataBox(parts, dataBox, comments, false))
            return ProcessHandler.processCommand(new Command(parts[0], dataBox));
        else
            return comments.toString();
    }

    private static String getLine(StringBuilder dataFile) {
        String line;
        if (dataFile.indexOf("\n") > 0) {
            line = dataFile.substring(0, dataFile.indexOf("\n"));
            dataFile.delete(0, dataFile.indexOf("\n"));
        } else {
            line = dataFile.toString();
            dataFile.setLength(0);
        }
        return line;
    }

    private static String save() {
        Iterator<Map.Entry<Long, Dragon>> it = ServerApp.dragonsHashtable.entrySet().iterator();
        StringBuilder dragons = new StringBuilder();
        while (it.hasNext()) {
            Map.Entry<Long, Dragon> currentDragon = it.next();
            dragons.append(currentDragon.getValue().toXml());
        }

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(ServerApp.getFileName()));
        } catch (IOException e) {
            e.printStackTrace();
            return "There was a problem while saving";
        }
        String fileContent = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><dragons>%s</dragons>", dragons.toString());

        try {
            writer.write(fileContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "There was a problem while saving";
        }

        return "The changes was saved";
    }

    private static String insert(Command command) {
        ServerApp.dragonsHashtable.put(command.getDataCommand().getDragon().getId(), command.getDataCommand().getDragon());
        return "The dragon was inserted.";
    }

    private static String update(Command command) {
        Long key = command.getDataCommand().getKey();
        if (key <= 0)
            return "Invalid key value.";

        if (ServerApp.dragonsHashtable.containsKey(key)) {
            ServerApp.dragonsHashtable.replace(key, command.getDataCommand().getDragon());
            return "The dragon was updated.";
        } else
            return "The dragon don't exists.";
    }

    private static String filterLessThanKiller(Command command) {
        Long weight = command.getDataCommand().getWeight();
        if (weight < 0) {
            return "Invalid killer weight.";
        }
        StringBuilder builder = new StringBuilder();
        ServerApp.dragonsHashtable.forEach((k, v) -> {
            if (v.getKiller().compareTo(weight) < 0) {
                builder.append(v.toXml());
            }
        });
        return builder.toString();
    }

    private static String count_by_character(Command command) {
        String dragonCharacterText = command.getDataCommand().getDragonCharacter();
        DragonCharacter dragonCharacter = DragonCharacterHelper.parseDragonCharacter(dragonCharacterText);
        if (dragonCharacter == null) {
            return "Invalid character, you could enter (EVIL, GOOD, CHAOTIC or FICKLE)";
        }
        AtomicReference<Integer> counter = new AtomicReference<>(0);
        ServerApp.dragonsHashtable.forEach((k, v) -> {
            if (v.getCharacter() == dragonCharacter)
                counter.getAndSet(counter.get() + 1);
        });
        return "There are " + counter.get() + " dragons with " + dragonCharacterText + " character.";
    }

    private static String remove_greater_key(Command command) {
        Long key = command.getDataCommand().getKey();
        if (key <= 0)
            return "Invalid key value.";

        Iterator<Map.Entry<Long, Dragon>> it = ServerApp.dragonsHashtable.entrySet().iterator();
        int deleted = 0;
        while (it.hasNext()) {
            Map.Entry<Long, Dragon> entry = it.next();
            if (entry.getKey() > key) {
                it.remove();
                deleted++;
            }
        }
        return "Deleted elements: " + deleted;
    }

    private static String replaceIfLower(Command command) {
        Long key = command.getDataCommand().getKey();
        Long age = command.getDataCommand().getAge();
        if (key <= 0) {
            return "Invalid key value.";
        }
        if (age <= 0) {
            return "Invalid age value.";
        }
        if (ServerApp.dragonsHashtable.containsKey(key))
            if (ServerApp.dragonsHashtable.get(key).getAge() > age) {
                ServerApp.dragonsHashtable.get(key).setAge(age);
                return "The age was replaced.";
            }
        return "Nothing to replace.";
    }

    private static String replaceIfGreater(Command command) {
        Long key = command.getDataCommand().getKey();
        Long age = command.getDataCommand().getAge();
        if (key <= 0) {
            return "Invalid key value.";
        }
        if (age <= 0) {
            return "Invalid age value.";
        }
        if (ServerApp.dragonsHashtable.containsKey(key))
            if (ServerApp.dragonsHashtable.get(key).getAge() < age) {
                ServerApp.dragonsHashtable.get(key).setAge(age);
                return "The age was replaced.";
            }
        return "Nothing to replace.";
    }

    private static String remove(Command command) {
        Long key = command.getDataCommand().getKey();
        if (key <= 0)
            return "Invalid key value";
        if (ServerApp.dragonsHashtable.containsKey(key)) {
            ServerApp.dragonsHashtable.remove(key);
            return "The dragon was removed.";
        } else
            return "The dragon don't exists.";
    }

    private static String printCharacter() {
        StringBuilder dataCharacter = new StringBuilder();
        ServerApp.dragonsHashtable.forEach((k, v) ->
                dataCharacter.append("\n").append(v.getCharacter()));
        return dataCharacter.toString();
    }

    private static String clear() {
        ServerApp.dragonsHashtable = new Hashtable<>();
        return "the collection was cleared";
    }

    private static String show() {
        if (ServerApp.dragonsHashtable.isEmpty()) {
            return "There are no dragons";
        }
        StringBuilder dataDragon = new StringBuilder();

        ServerApp.dragonsHashtable.forEach((k, v) -> dataDragon.append("\n").append(v.toString()));
        return dataDragon.toString();
    }

    private static String info() {
        return "type: Hashtable<Long, Dragon>\n" +
                "initialization: " + ServerApp.getInitialization() + "\n" +
                "length: " + ServerApp.dragonsHashtable.size();
    }

    private static String help() {
        return "help : вывести справку по доступным командам" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении" +
                "insert : добавить новый элемент с заданным ключом" +
                "update id : обновить значение элемента коллекции, id которого равен заданному" +
                "remove_key id : удалить элемент из коллекции по его ключу" +
                "clear : очистить коллекцию" +
                "save : сохранить коллекцию в файл" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме." +
                "exit : завершить программу (без сохранения в файл)" +
                "replace_if_greater key age : заменить значение по ключу, если новое значение больше старого" +
                "replace_if_lowe key age : заменить значение по ключу, если новое значение меньше старого" +
                "remove_greater_key key : удалить из коллекции все элементы, ключ которых превышает заданный" +
                "count_by_character character : вывести количество элементов, значение поля character которых равно заданному" +
                "filter_less_than_killer killer : вывести элементы, значение поля killer которых меньше заданного" +
                "print_unique_character : вывести уникальные значения поля character всех элементов в коллекции";
    }
}