package com.itmo.dragon.shared;

import java.io.Serializable;

/**
 * Coordinates class
 *
 * @author: Francisco Estrella
 * @version: 0.1
 */
public class Coordinates implements Serializable {
    private Double x; //Поле не может быть null
    private float y;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
