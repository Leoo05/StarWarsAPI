package com.example.myapplication;

public class Persona {

    public String nombre;
    public int height;
    public int mass;
    public String hair_color;
    public String skin_color;
    public String eye_color;
    public String birth_year;
    public String gender;

    public Persona(String nombre, int height, int mass, String hair_color, String skin_color, String eye_color, String birth_year, String gender) {
        this.nombre = nombre;
        this.height = height;
        this.mass = mass;
        this.hair_color = hair_color;
        this.skin_color = skin_color;
        this.eye_color = eye_color;
        this.birth_year = birth_year;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return
                " Nombre='" + nombre + '\'' +
                ", height=" + height +
                ", mass=" + mass +
                ", hair_color='" + hair_color + '\'' +
                ", skin_color='" + skin_color + '\'' +
                ", eye_color='" + eye_color + '\'' +
                ", birth_year='" + birth_year + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
