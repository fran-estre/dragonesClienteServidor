package com.itmo.dragon.shared.entities;

public class DragonCharacterHelper {
    public static DragonCharacter parseDragonCharacter(String character) {
        switch (character) {
            case "CHAOTIC":
                return DragonCharacter.CHAOTIC;
            case "EVIL":
                return DragonCharacter.EVIL;
            case "FICKLE":
                return DragonCharacter.FICKLE;
            case "GOOD":
                return DragonCharacter.GOOD;
            default:
                return null;
        }
    }

}
