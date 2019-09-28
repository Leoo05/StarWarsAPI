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

    String json;
    ArrayList<Persona> lista_personas;
    Button sig;
    Button ant;
    TextView atr;
    int index = 0;
    String nextPage;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        sig = findViewById(R.id.button_siguiente);
        ant = findViewById(R.id.button_anterior);
        atr = findViewById(R.id.textView_atributos);
        this.lista_personas = new ArrayList<>();
        json = getIntent().getStringExtra("json");
        nextPage = getIntent().getStringExtra("nextPage");
        agregarPersonas();

        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = (index + 1) % (lista_personas.size() - 1);
                agregarPersonas();
                atr.setText(lista_personas.get(index).toString());
            }
        });

        ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarPersonas();
                index = (index - 1) % (lista_personas.size() - 1);
                atr.setText(lista_personas.get(index).toString());
            }
        });


    }

    public void agregarPersonas() {

        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("results"));
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject persona = jsonArray.getJSONObject(i);
                this.lista_personas.add(new Persona(persona.getString("name"), persona.getInt("height"),
                        persona.getInt("mass"), persona.getString("hair_color"), persona.getString("skin_color"), persona.getString("eye_color"), persona.getString("birth_year")
                        , persona.getString("gender")));

            }
        } catch (JSONException e) {
            e.printStackTrace();
            atr.setText(json);
        }
    }
}
