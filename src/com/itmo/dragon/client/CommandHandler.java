package com.itmo.dragon.client;

import com.itmo.dragon.shared.commands.*;

import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

/**
 * handle the commands given by the user.
 */
public class CommandHandler() {
    public static void handleConsoleCommand() {
        Scanner keyboard = new Scanner(System.in);
        String currentCommand;
        System.out.println("Enter the command: ");
        while ((currentCommand = keyboard.nextLine()) != "exit") {
            String[] parts = currentCommand.split(" ");
            switch (parts[0]) {
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
                case "save":
                    save();
                    break;
                case "print_character":
                    printCharacter();
                    break;
                case "remove_key":
                    if (parts.length < 2) {
                        System.out.println("The command is incomplete, you need to enter the key.");
                        break;
                    }
                    Long key;
                    try {
                        key = Long.parseLong(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("The command is invalid.");
                        break;
                    }
                    remove(key);
                    break;
                case "execute_script":
                    if (parts.length < 2) {
                        System.out.println("The command is incomplete, you need to enter the filename that contain the commands.");
                        break;
                    }
                    executeScript(parts[1]);
                    break;
                case "replace_if_greater":
                    if (parts.length < 3) {
                        System.out.println("The command is incomplete, you need to enter the key and the age.");
                        break;
                    }
                    try {
                        key = Long.parseLong(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("The command is invalid.");
                        break;
                    }
                    Long age;
                    try {
                        age = Long.parseLong(parts[2]);
                    } catch (NumberFormatException e) {
                        System.out.println("The command is invalid.");
                        break;
                    }
                    replaceIfGreater(key, age);
                    break;
                case "replace_if_lower":
                    if (parts.length < 3) {
                        System.out.println("The command is incomplete, you need to enter the key and the age.");
                        break;
                    }
                    try {
                        key = Long.parseLong(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("The command is invalid.");
                        break;
                    }

                    try {
                        age = Long.parseLong(parts[2]);
                    } catch (NumberFormatException e) {
                        System.out.println("The command is invalid.");
                        break;
                    }
                    replaceIfLower(key, age);
                    break;
                case "remove_greater_key":
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
                    remove_greater_key(key);
                    break;
                case "count_by_character":
                    if (parts.length < 2) {
                        System.out.println("The command is incomplete, you need to enter the character (EVIL, GOOD, CHAOTIC, FICKLE).");
                        break;
                    }
                    if (parts[1] != "EVIL" && parts[1] != "GOOD" && parts[1] != "CHAOTIC" && parts[1] != "FICKLE") {
                        System.out.println("The command is invalid.");
                        break;
                    }
                    count_by_character(parts[1]);
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
                    break;
                default:
                    System.out.println("unknown command");
                    break;
            }
            System.out.println("Enter the new command: ");
        }
        System.out.println("Goodbye.");
    }

    private static void help() {
        System.out.println("help : вывести справку по доступным командам");
        System.out.println("info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        System.out.println("show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        System.out.println("insert : добавить новый элемент с заданным ключом");
        System.out.println("update id : обновить значение элемента коллекции, id которого равен заданному");
        System.out.println("remove_key id : удалить элемент из коллекции по его ключу");
        System.out.println("clear : очистить коллекцию");
        System.out.println("save : сохранить коллекцию в файл");
        System.out.println("execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        System.out.println("exit : завершить программу (без сохранения в файл)");
        System.out.println("replace_if_greater key age : заменить значение по ключу, если новое значение больше старого");
        System.out.println("replace_if_lowe key age : заменить значение по ключу, если новое значение меньше старого");
        System.out.println("remove_greater_key key : удалить из коллекции все элементы, ключ которых превышает заданный");
        System.out.println("count_by_character character : вывести количество элементов, значение поля character которых равно заданному");
        System.out.println("filter_less_than_killer killer : вывести элементы, значение поля killer которых меньше заданного");
        System.out.println("print_unique_character : вывести уникальные значения поля character всех элементов в коллекции");
    }

    private static void clear() throws IOException {
        Clear clear = new Clear();
        clear.setCommand(new Command("Clear"));
        byte[] data = getBytes(clear);
        ClientApp.getCommunication().send(data);
    }

    private static byte[] getBytes(Object data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(data);
        oos.flush();
        return bos.toByteArray();
    }

    private static void show() throws IOException {
        Show show = new Show();
        show.setCommand(new Command("Show"));
        byte[] data = getBytes(show);
        ClientApp.getCommunication().send(data);
    }

    private static void info() throws IOException {
        Info info = new Info();
        info.setCommand(new Command("Info"));
        byte[] data = getBytes(info);
        ClientApp.getCommunication().send(data);
    }

    private static void executeCommand(String currentCommand) {
        String[] parts = currentCommand.split(" ");
        switch (parts[0]) {
            case "exit":
                System.out.println("Goodbye.");
                break;
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
            case "save":
                save();
                break;
            case "print_character":
                printCharacter();
                break;
            case "insert":
                System.out.println("The insert command isn't supported in the none-interactive mode.");
                break;
            case "update":
                System.out.println("The update command isn't supported in the none-interactive mode.");
                break;
            case "remove_key":
                if (parts.length != 2) {
                    System.out.println("The remove_key command is incomplete.");
                    break;
                }
                remove(Long.parseLong(parts[1]));
                break;
            case "execute_script":
                if (parts.length != 2) {
                    System.out.println("The execute_script command is incomplete.");
                    break;
                }
                executeScript(parts[1]);

                break;
            case "replace_if_greater":
                if (parts.length < 3) {
                    System.out.println("The command is incomplete");
                    break;
                }
                Long key;
                try {
                    key = Long.parseLong(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("The command is invalid.");
                    break;
                }
                Long age;
                try {
                    age = Long.parseLong(parts[2]);
                } catch (NumberFormatException e) {
                    System.out.println("The command is invalid.");
                    break;
                }

                replaceIfGreater(key, age);
                break;
            case "replace_if_lower":
                if (parts.length < 3) {
                    System.out.println("The command is incomplete, you need to enter the key and the age.");
                    break;
                }
                try {
                    key = Long.parseLong(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("The command is invalid.");
                    break;
                }

                try {
                    age = Long.parseLong(parts[2]);
                } catch (NumberFormatException e) {
                    System.out.println("The command is invalid.");
                    break;
                }
                replaceIfLower(key, age);
                break;
            case "remove_greater_key":
                if (parts.length != 2) {
                    System.out.println("The remove_greater_key command is incomplete, you need to enter the key.");
                    break;
                }
                remove_greater_key(Long.parseLong(parts[1]));
                break;
            case "count_by_character":
                if (parts.length != 2) {
                    System.out.println("The count_by_character command is incomplete.");
                    break;
                }
                count_by_character(DragonCharacterHelper.parseDragonCharacter(parts[1]));

                break;
            case "filter_less_than_killer":
                break;
            default:
                System.out.println("unknown command");
                break;
        }
    }

    private static void printCharacter() {
        dragonsHashtable.forEach((k, v) -> {
            System.out.println("\n" + v.getCharacter());
        });
    }

    private static void count_by_character(String dragonCharacter) throws IOException {
        CountByCharacter countByCharacter = new CountByCharacter();
        countByCharacter.setCommand(new Command("CountByCharacter"));
        countByCharacter.setCharacter(dragonCharacter);
        byte[] data = getBytes(countByCharacter);
        ClientApp.getCommunication().send(data);
    }

    private static void update(Long id, Scanner keyboard) {
        if (dragonsHashtable.containsKey(id)) {
            Dragon dragon = createDragon(keyboard);
            dragonsHashtable.replace(id, dragon);
            System.out.println("The dragon was updated.");
        } else
            System.out.println("The dragon don't exists.");
    }

    private static void save() {

        Iterator<Map.Entry<Long, Dragon>> it = dragonsHashtable.entrySet().iterator();
        String dragons = "";
        while (it.hasNext()) {
            Map.Entry<Long, Dragon> currentDragon = it.next();
            dragons += currentDragon.getValue().toXml();
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContent = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><dragons>%s</dragons>", dragons);
        if (writer != null) {
            try {
                writer.write(fileContent);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void remove_greater_key(Long id) {
        Iterator<Map.Entry<Long, Dragon>> it = dragonsHashtable.entrySet().iterator();
        Integer deleted = 0;
        while (it.hasNext()) {
            Map.Entry<Long, Dragon> entry = it.next();
            if (entry.getKey() > id) {
                it.remove();
                deleted++;
            }
        }
        System.out.println("Deleted elements: " + deleted);
    }

    private static void remove(Long id) {
        if (dragonsHashtable.containsKey(id)) {
            dragonsHashtable.remove(id);
            System.out.println("The dragon was removed.");
        } else
            System.out.println("The dragon don't exists.");
    }

    private static void insert(Scanner keyboard) {
        Dragon dragon = createDragon(keyboard);
        dragonsHashtable.put(dragon.getId(), dragon);
    }

    private static void executeScript(String fileName) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("\nCant read the file.");
            return;
        }

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println(ANSI_BLUE + line + ANSI_RESET);
                executeCommand(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("\nCant read the file.");
            return;
        }
    }

    private static void replaceIfGreater(Long id, Long age) {
        if (dragonsHashtable.containsKey(id))
            if (dragonsHashtable.get(id).getAge() < age) {
                dragonsHashtable.get(id).setAge(age);
                System.out.println("\nThe age was replaced.");
                return;
            }
        System.out.println("\nNothing to replace.");
    }

    private static void replaceIfLower(Long id, Long age) {
        if (dragonsHashtable.containsKey(id))
            if (dragonsHashtable.get(id).getAge() > age) {
                dragonsHashtable.get(id).setAge(age);
                System.out.println("\nThe age was replaced.");
                return;
            }
        System.out.println("\nNothing to replace.");
    }

    private static void filterLessThanKiller(Long weight) {
        dragonsHashtable.forEach((k, v) -> {
            if (v.getKiller().compareTo(weight) < 0) {
                System.out.println(v.toXml());
            }
        });
    }
}
