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

public class Planets extends AppCompatActivity {

    static String json;
    static ArrayList<Planeta> planetas;
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

        this.planetas = new ArrayList<>();

        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = (index + 1) % (planetas.size() - 1);
                atr.setText(planetas.get(index).toString());
            }
        });

        ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = (index - 1) % (planetas.size() - 1);
            }
        });

        json = getIntent().getStringExtra("json");
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray arrPlanetas = new JSONArray(jsonObject.getJSONArray("results"));
            for(int i=0;i<arrPlanetas.length();i++){
                JSONObject planeta = arrPlanetas.getJSONObject(i);
                this.planetas.add(new Planeta(planeta.getString("name"),planeta.getInt("rotationPeriod"),
                        planeta.getInt("orbital_period"),planeta.getInt("diameter"),planeta.getString("temperature"),planeta.getString("gravity"),planeta.getString("terrain")
                        ,planeta.getInt("surface_water"),planeta.getString("population")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        atr.setText(this.planetas.get(0).toString());

    }
}
