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
        return
                "Nombre=" + name + '\n' +
                "Periodo de Rotacion=" + rotation_period + '\n' +
                "Periodo de orbita=" + orbital_period + '\n' +
                "Diametro=" + diameter + '\n' +
                "Temperatura=" + temperature + '\n' +
                "Gravedad='" + gravity + '\n' +
                "Terreno=" + terrain + '\n' +
                "Superficie de Agua=" + surface_water + '\n' +
                "Poblacion=" + population + '\n';
    }
}
