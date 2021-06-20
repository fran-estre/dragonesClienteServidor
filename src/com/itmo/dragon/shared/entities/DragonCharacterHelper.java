package com.itmo.dragon.shared.entities;

public class DragonCharacterHelper {
    public static DragonCharacter parseDragonCharacter(String character) {
        return switch (character) {
            case "CHAOTIC" -> DragonCharacter.CHAOTIC;
            case "EVIL" -> DragonCharacter.EVIL;
            case "FICKLE" -> DragonCharacter.FICKLE;
            case "GOOD" -> DragonCharacter.GOOD;
            default -> null;
        };
    }

}
