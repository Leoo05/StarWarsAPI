package com.example.myapplication;

public class Persona {

    public String nombre;
    public String height;
    public String mass;
    public String hair_color;
    public String skin_color;
    public String eye_color;
    public String birth_year;
    public String gender;

    public Persona(String nombre, String height, String mass, String hair_color, String skin_color, String eye_color, String birth_year, String gender) {
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
                "Nombre=" + nombre + '\n' + "Altura=" + height + '\n' +
                "Peso=" + mass + '\n' +
                "Color del Cabello=" + hair_color + '\n' +
                "Color de Piel=" + skin_color + '\n' +
                "Color de Ojos=" + eye_color + '\n' +
                "Año de Nacimiento=" + birth_year + '\n' +
                "Genero=" + gender;
    }
}
