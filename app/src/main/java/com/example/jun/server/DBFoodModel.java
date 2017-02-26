package com.example.jun.server;

/**
 * Model for food objects retrieved from db
 */

public class DBFoodModel {
    private int food_id;
    private String name;
    private String description;
    private String path;
    private int vegetarian;
    private int vegan;
    private int kosher;
    private int gluten_free;

    public int food_id() {
        return food_id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public String path() {
        return path;
    }

    public boolean vegetarian() {
        return (vegetarian == 1);
    }

    public boolean vegan() {
        return (vegan == 1);
    }

    public boolean kosher() {
        return (kosher == 1);
    }

    public boolean gluten_free() {
        return (gluten_free == 1);
    }

    @Override
    public String toString() {
        return "DBFoodModel{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", vegetarian=" + vegetarian +
                ", vegan=" + vegan +
                ", kosher=" + kosher +
                ", gluten_free=" + gluten_free +
                '}';
    }
}
