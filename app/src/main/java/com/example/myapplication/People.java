package com.example.myapplication;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class People extends AppCompatActivity {

    static String json;
    static ArrayList<Persona> personas;
    static Button sig;
    static Button ant;
    static TextView atr;
    static int index=0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        sig = findViewById(R.id.button_siguiente);
        ant = findViewById(R.id.button_anterior);
        atr = findViewById(R.id.textView_atributos);
        this.personas = new ArrayList<>();

        json = getIntent().getStringExtra("json");
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray arrpersonas = new JSONArray(jsonObject.getJSONArray("results"));
            for(int i=0;i<arrpersonas.length();i++){
                JSONObject persona = arrpersonas.getJSONObject(i);
                this.personas.add(new Persona(persona.getString("name"),persona.getInt("height"),
                        persona.getInt("mass"),persona.getString("hair_color"),persona.getString("skin_color"),persona.getString("eye_color"),persona.getString("birth_year")
                        ,persona.getString("gender")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        atr.setText(this.personas.get(0).toString());

        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = (index + 1) % (personas.size() - 1);
                atr.setText(personas.get(index).toString());
            }
        });

        ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = (index - 1) % (personas.size() - 1);
            }
        });


    }
}
