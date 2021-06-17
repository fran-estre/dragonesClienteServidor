package com.itmo.dragon.client;

import com.itmo.dragon.shared.entities.*;

import java.util.Locale;
import java.util.Scanner;

public class DragonReader {
    public Dragon read() {
        Scanner keyboard = new Scanner(System.in);
        String name;
        do {
            System.out.println("Enter dragon name: ");
            name = keyboard.nextLine();
            if (name.isEmpty() || name == null) {
                System.out.println("The name is invalid.");
            }
        } while (name.isEmpty() || name == null);

        Long age;
        do {
            System.out.println("Enter dragon age: ");
            age = readLong(keyboard, "Enter the correct value for the age.");
            if (age == 0 || age == null) {
                System.out.println("The age is invalid.");
            }
        } while (age <= 0);

        Double weight;
        do {
            System.out.println("Enter weight: ");
            weight = readDouble(keyboard, "Enter the correct value for the weight.");
            if (weight <= 0) {
                System.out.println("The weight is invalid.");
            }
        } while (weight <= 0);

        Boolean speaking = false;
        System.out.println("Enter speaking (y: yes, otherwise no): ");
        String value = keyboard.nextLine();
        if (value.toLowerCase(Locale.ROOT) == "y") {
            speaking = true;
        }

        Coordinates coordinates = createCoordinates(keyboard);
        DragonCharacter character = createDragonCharacter(keyboard);
        Person killer = createPerson(keyboard);
        return new Dragon(name, coordinates, age, weight, speaking, character, killer);
    }

    public static Double readDouble(Scanner keyboard, String message) {
        while (true) {
            String value = keyboard.nextLine();
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                System.out.println(message);
            }
        }
    }

    public static Long readLong(Scanner keyboard, String message) {
        while (true) {
            String value = keyboard.nextLine();
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                System.out.println(message);
            }
        }
    }

    private static Coordinates createCoordinates(Scanner keyboard) {
        Double x;
        do {
            System.out.println("Enter coordinate x: ");
            x = readDouble(keyboard, "Enter the correct value for the coordinate.");
            if (x == null) {
                System.out.println("The coordinate is invalid.");
            }
        } while (x == null);

        Float y;
        do {
            System.out.println("Enter coordinate y: ");
            y = readFloat(keyboard, "Enter the correct value for the coordinate.");
            if (y == null) {
                System.out.println("The coordinate is invalid.");
            }
        } while (y == null);

        Coordinates coordinates = new Coordinates();
        coordinates.setX(x);
        coordinates.setY(y);
        return coordinates;
    }

    private static DragonCharacter createDragonCharacter(Scanner keyboard) {
        String value;
        DragonCharacter character = null;
        do {
            System.out.println("Enter Dragon character (EVIL, GOOD, CHAOTIC, FICKLE): ");
            value = keyboard.nextLine();
            switch (value) {
                case "EVIL":
                    character = DragonCharacter.EVIL;
                    break;
                case "GOOD":
                    character = DragonCharacter.GOOD;
                    break;
                case "CHAOTIC":
                    character = DragonCharacter.CHAOTIC;
                    break;
                case " FICKLE":
                    character = DragonCharacter.FICKLE;
                    break;
                default:
                    System.out.println("The character is invalid.");
                    break;
            }
        } while (character == null);
        return character;
    }

    private static Person createPerson(Scanner keyboard) {
        String personName;
        do {
            System.out.println("Enter person name: ");
            personName = keyboard.nextLine();
            if (personName.isEmpty() || personName == null) {
                System.out.println("The person name is invalid.");
            }
        } while (personName.isEmpty() || personName == null);

        Long personWeight;
        do {
            System.out.println("Enter person weight: ");
            personWeight = readLong(keyboard, "Enter the correct value for the weight.");
            if (personWeight <= 0) {
                System.out.println("The person weight is invalid.");
            }
        } while (personWeight <= 0);

        Double height;
        do {
            System.out.println("Enter height: ");
            height = readDouble(keyboard, "Enter the correct value for the height.");
            if (height <= 0) {
                System.out.println("The height is invalid.");
            }
        } while (height <= 0);


        Location location = createLocation(keyboard);

        Person killer = new Person();
        killer.setHeight(height);
        killer.setName(personName);
        killer.setWeight(personWeight);
        killer.setLocation(location);
        return killer;
    }

    public static Float readFloat(Scanner keyboard, String message) {
        while (true) {
            String value = keyboard.nextLine();
            try {
                return Float.parseFloat(value);
            } catch (NumberFormatException e) {
                System.out.println(message);
            }
        }
    }

    private static Location createLocation(Scanner keyboard) {
        String name;
        do {
            System.out.println("Enter location name: ");
            name = keyboard.nextLine();
            if (name.isEmpty() || name == null) {
                System.out.println("The location name is invalid.");
            }
        } while (name.isEmpty() || name == null);

        Double x;
        do {
            System.out.println("Enter location x: ");
            x = readDouble(keyboard, "Enter the correct value for the coordinate.");
            if (x == null) {
                System.out.println("The location is invalid.");
            }
        } while (x == null);

        Double y;
        do {
            System.out.println("Enter location y: ");
            y = readDouble(keyboard, "Enter the correct value for the coordinate.");
            if (y == null) {
                System.out.println("The location is invalid.");
            }
        } while (y == null);

        Float z;
        do {
            System.out.println("Enter coordinate z: ");
            z = readFloat(keyboard, "Enter the correct value for the coordinate.");
            if (z == null) {
                System.out.println("The coordinate is invalid.");
            }
        } while (z == null);

        Location location = new Location();
        location.setName(name);
        location.setX(x);
        location.setY(y);
        location.setZ(z);
        return location;
    }

}
