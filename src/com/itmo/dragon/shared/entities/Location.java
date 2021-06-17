package com.itmo.dragon.shared.entities;

import java.io.Serializable;

/**
 * Location class
 *
 * @author: Francisco Estrella
 * @version: 0.1
 */
public class Location implements Serializable {
    private double x;
    private double y;
    private float z;
    private String name; //Поле может быть null

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
