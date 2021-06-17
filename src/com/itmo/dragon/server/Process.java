package com.itmo.dragon.server;

import com.itmo.dragon.shared.commands.Command;
import com.itmo.dragon.shared.entities.Dragon;

import java.util.Hashtable;

public class Process {
    public static void рrocessCommand(Command command) {
        switch (command.getName()) {
            case "help":
                help();
                break;
            case "info":
                info();
                break;
            case "show":
                show();
                break;
            case "clear":
                clear();
           /* case "save":
                save();
                break;*/
            case "print_character":
                printCharacter();
                break;
            case "remove_key":
                remove(command);
                break;
          /*  case "execute_script":
                if (parts.length < 2) {
                    System.out.println("The command is incomplete, you need to enter the filename that contain the commands.");
                    break;
                }
                executeScript(parts[1]);
                break; */
            case "replace_if_greater":
                replaceIfGreater(command);
                break;
            case "replace_if_lower":
                replaceIfLower(command);
                break;
           case "remove_greater_key":
                remove_greater_key(command);
                break;
            case "count_by_character":
                if (parts.length < 2) {
                    System.out.println("The command is incomplete, you need to enter the character (EVIL, GOOD, CHAOTIC, FICKLE).");
                    break;
                }
                DragonCharacter dragonCharacter = DragonCharacterHelper.parseDragonCharacter(parts[1]);
                if (dragonCharacter == null) {
                    System.out.println("The command is invalid.");
                    break;
                }
                count_by_character(dragonCharacter);
                break;
            case "filter_less_than_killer":
                if (parts.length < 2) {
                    System.out.println("The command is incomplete, you need to enter the killer weight.");
                    break;
                }
                Long weight;
                try {
                    weight = Long.parseLong(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("The command is invalid.");
                    break;
                }
                filterLessThanKiller(weight);

                break;
            case "insert":
                insert(keyboard);
                break;
            case "update":
                if (parts.length < 2) {
                    System.out.println("The command is incomplete, you need to enter the key.");
                    break;
                }
                try {
                    key = Long.parseLong(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("The command is invalid.");
                    break;
                }
                update(key, keyboard);
                break;*/
            default:
                System.out.println("unknown command");
                break;
        }

    }

    private static String remove_greater_key(Command command) {
        Long key = command.getDataCommand().getKey();
        if (key<=0)
            return "Invalid key value.";
        try {
            key = Long.parseLong(parts[1]);
        } catch (NumberFormatException e) {
            System.out.println("The command is invalid.");
            break;
        }
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
        if (key == 0) {
            return "Invalid key value.";
        }
        if (age == 0) {
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
        if (key == 0)
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
                dataCharacter.append("\n" + v.getCharacter()));
        return dataCharacter.toString();
    }

    private static String clear() {
        ServerApp.dragonsHashtable = new Hashtable<Long, Dragon>();
        return "the collection was cleared";
    }

    private static String show() {
        if (ServerApp.dragonsHashtable.isEmpty()) {
            return "There are no dragons";
        }
        StringBuilder dataDragon = new StringBuilder();

        ServerApp.dragonsHashtable.forEach((k, v) -> dataDragon.append("\n" + v.toString()));
        return dataDragon.toString();
    }

    private static String info() {
        return "type: Hashtable<Long, Dragon>\n" +
                "initialization: " + ServerApp.initialization + "\n" +
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
