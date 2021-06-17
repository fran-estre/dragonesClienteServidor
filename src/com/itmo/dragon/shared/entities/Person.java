package com.itmo.dragon.shared.entities;

import com.itmo.dragon.shared.entities.Location;

import java.io.Serializable;

/**
 * Person class
 *
 * @author: Francisco Estrella
 * @version: 0.1
 */
public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Double height; //Поле не может быть null, Значение поля должно быть больше 0
    private long weight; //Значение поля должно быть больше 0
    private Location location; //Поле не может быть null

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
