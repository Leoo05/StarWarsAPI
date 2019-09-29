package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;


import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    public static final String URL_SWAPI = "https://swapi.co/api/";
    private TextView textTitle;
    String JsonString;
    Button peopleButton;
    Button planetsButton;
    JSONObject obj;
    String nextPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textTitle = findViewById(R.id.textView_titulo);
        peopleButton = findViewById(R.id.button_personajes);
        planetsButton = findViewById(R.id.button_planetas);
/*
        peopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(v, "people/");
                    textTitle.setText(JsonString);
                    Intent next = new Intent(MainActivity.this, People.class);
                    next.putExtra("json", obj.toString());
                    startActivity(next);
            }
        });

        planetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(v, "planets/");
                Intent next = new Intent(MainActivity.this, Planets.class);
                next.putExtra("json", obj.toString());
                startActivity(next);
            }
        });*/
    }

    public void makeCallPeople(View v) throws JSONException {
        callWebService("people/");
        if (obj != null) {
            Intent next = new Intent(MainActivity.this, People.class);
            next.putExtra("json", obj.getString("results"));
            next.putExtra("nextPage", obj.getString("next"));
            startActivity(next);
        }
    }

    public void makeCallPlanets(View v) throws JSONException {
        callWebService("planets/");
        if(obj !=null) {
            Intent next = new Intent(MainActivity.this, Planets.class);
            next.putExtra("json", obj.getString("results"));
            next.putExtra("nextPage", obj.getString("next"));
            startActivity(next);
        }
    }

    public void callWebService(final String serviceEndPoint) {

        AsyncTask.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    String serv = serviceEndPoint;
                    URL urlService = new URL(URL_SWAPI + serviceEndPoint);
                    HttpsURLConnection connection = (HttpsURLConnection) urlService.openConnection();
                    connection.setRequestMethod("GET");
                    if (connection.getResponseCode() == 200) {
                        InputStream responseBody = connection.getInputStream();
                        // Success
                        JsonString = getStringFromInputStream(responseBody);
                        obj = new JSONObject(JsonString);
                    } else {
                        // Error handling code goes here
                        Log.v("ERROR", "ERROR");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public static String getStringFromInputStream(InputStream stream) throws IOException {
        int n = 0;
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
        StringWriter writer = new StringWriter();
        while (-1 != (n = reader.read(buffer))) writer.write(buffer, 0, n);
        return writer.toString();
    }
}