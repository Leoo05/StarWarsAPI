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

    String json;
    ArrayList<Planeta> listaPlanetas;
    Button sig;
    Button ant;
    TextView atr;
    int index=0;
    String nextPage;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        sig = findViewById(R.id.button_siguiente);
        ant = findViewById(R.id.button_anterior);
        atr = findViewById(R.id.textView_atributos);

        this.listaPlanetas = new ArrayList<>();
        json = getIntent().getStringExtra("json");
        nextPage = getIntent().getStringExtra("nextPage");
        this.agregarPlanetas();


        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = (index + 1) % (listaPlanetas.size() - 1);
                try {
                    atr.setText(listaPlanetas.get(index).toString());
                }  catch (IndexOutOfBoundsException e){
                    atr.setText("F");
                }
            }
        });

        ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = (index - 1) % (listaPlanetas.size() - 1);
                try {
                    atr.setText(listaPlanetas.get(index).toString());
                }  catch (IndexOutOfBoundsException e){
                    atr.setText("F");
                }
            }
        });

    }

    public void agregarPlanetas() {
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject planeta = jsonArray.getJSONObject(i);
                this.listaPlanetas.add(new Planeta(planeta.getString("name"), planeta.getInt("rotation_period"),
                        planeta.getInt("orbital_period"), planeta.getInt("diameter"),
                        planeta.getString("climate"),
                        planeta.getString("gravity"),
                        planeta.getString("terrain"),
                        planeta.getInt("surface_water"),
                        planeta.getString("population")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            atr.setText(nextPage);
        }
    }
}
