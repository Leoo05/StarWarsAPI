package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class Planets extends AppCompatActivity {

    ArrayList<Planeta> listaPlanetas;
    Button sig;
    Button ant;
    public final String URL_SWAPI = "https://swapi.co/api/planets/";
    TextView atr;
    int index=0;
    String JsonString;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planets);

        sig = findViewById(R.id.button_siguiente);
        ant = findViewById(R.id.button_anterior);
        atr = findViewById(R.id.textView_atributos_planetas);

        this.listaPlanetas = new ArrayList<>();
        callWebService(URL_SWAPI);

        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //index = (index + 1) % (listaPlanetas.size() - 1);
                index++;
                if(index>listaPlanetas.size()-1){
                    index=0;
                }
                try {
                    atr.setText(listaPlanetas.get(index).toString());
                }  catch (IndexOutOfBoundsException e){
                    atr.setText("Se salio del arreglo >:v");
                }
            }
        });

        ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index--;
                if(index<0){
                    index=listaPlanetas.size()-1;
                }
                try {
                    atr.setText(listaPlanetas.get(index).toString());
                }  catch (IndexOutOfBoundsException e){
                    atr.setText("Se salio del arreglo >:v");
                }
            }
        });

    }

    public void callWebService(final String serviceEndPoint) {
        AsyncTask.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    URL urlService = new URL(serviceEndPoint);
                    HttpsURLConnection connection = (HttpsURLConnection) urlService.openConnection();
                    connection.setRequestMethod("GET");
                    if (connection.getResponseCode() == 200) {
                        // Success
                        InputStream responseBody = connection.getInputStream();
                        JsonString = getStringFromInputStream(responseBody);
                        JSONObject obj = new JSONObject(JsonString);
                        int x = 0;
                        while(!obj.getString("next").equals("null") && x < 11){
                            agregarPlanetas(obj.getString("results"));
                            urlService = new URL(obj.getString("next"));
                            connection = (HttpsURLConnection) urlService.openConnection();
                            connection.setRequestMethod("GET");
                            if (connection.getResponseCode() == 200) {
                                // Success
                                responseBody = connection.getInputStream();
                                JsonString = getStringFromInputStream(responseBody);
                                obj = new JSONObject(JsonString);
                            } else {
                                // Error handling
                                Log.v("ERROR", "ERROR");
                            }
                        }
                    } else {
                        // Error handling
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
        BufferedReader r = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder total = new StringBuilder();
        for (String line; (line = r.readLine()) != null; ) {
            total.append(line).append('\n');
        }
        return total.toString();
    }

    public void agregarPlanetas(String s) {
        try {
            JSONArray jsonArray = new JSONArray(s);
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
        }
    }
}
