package com.itmo.dragon.shared.commands;

import java.io.Serializable;

public class DataBox implements Serializable {
    Long weight;
    Long key;
    Long age;
    String dragonCharacter;
    String dataFile;

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long weight) {
        this.key = key;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getDragonCharacter() {
        return dragonCharacter;
    }

    public void setDragonCharacter(String dragonCharacter) {
        this.dragonCharacter = dragonCharacter;
    }

    public String getDataFile() {
        return dataFile;
    }

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }
}

