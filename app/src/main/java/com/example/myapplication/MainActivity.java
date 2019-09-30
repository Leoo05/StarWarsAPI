package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView textTitle;
    Button peopleButton;
    Button planetsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textTitle = findViewById(R.id.textView_titulo);
        peopleButton = findViewById(R.id.button_personajes);
        planetsButton = findViewById(R.id.button_planetas);
    }

    public void makeCallPeople(View v){
        Intent next = new Intent(MainActivity.this, People.class);
        startActivity(next);
    }

    public void makeCallPlanets(View v) {
            Intent next = new Intent(MainActivity.this, Planets.class);
            startActivity(next);

    }
}