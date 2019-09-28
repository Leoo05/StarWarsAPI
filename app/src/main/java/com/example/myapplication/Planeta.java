package com.example.myapplication;

import java.util.ArrayList;

public class Planeta {
    public String name;
    public int rotation_period;
    public int orbital_period;
    public int diameter;
    public String temperature;
    public String gravity;
    public String terrain;
    public int surface_water;
    public String population;

    public Planeta(String name, int rotation_period, int orbital_period, int diameter, String temperature, String gravity, String terrain, int surface_water, String population) {
        this.name = name;
        this.rotation_period = rotation_period;
        this.orbital_period = orbital_period;
        this.diameter = diameter;
        this.temperature = temperature;
        this.gravity = gravity;
        this.terrain = terrain;
        this.surface_water = surface_water;
        this.population = population;
    }

    @Override
    public String toString() {
        return "Planeta{" +
                "name='" + name + '\'' +
                ", rotation_period=" + rotation_period +
                ", orbital_period=" + orbital_period +
                ", diameter=" + diameter +
                ", temperature='" + temperature + '\'' +
                ", gravity='" + gravity + '\'' +
                ", terrain='" + terrain + '\'' +
                ", surface_water=" + surface_water +
                ", population='" + population + '\'' +
                '}';
    }
}
