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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class People extends AppCompatActivity {

    ArrayList<Persona> lista_personas;
    Button sig;
    Button ant;
    TextView atr;
    public final String URL_SWAPI = "https://swapi.co/api/people/";
    String JsonString;
    int index = 0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        sig = findViewById(R.id.button_siguiente);
        ant = findViewById(R.id.button_anterior);
        atr = findViewById(R.id.textView_atributos);
        this.lista_personas = new ArrayList<>();
        callWebService(URL_SWAPI);

        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                if(index>lista_personas.size()-1){
                    index=0;
                }
                try {
                    atr.setText(lista_personas.get(index).toString());
                }  catch (IndexOutOfBoundsException e){
                    atr.setText("Se salio del arreglo");
                }
            }
        });

        ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index--;
                if(index<0){
                    index=lista_personas.size()-1;
                }
                try {
                    atr.setText(lista_personas.get(index).toString());
                }  catch (IndexOutOfBoundsException e){
                    atr.setText("Se salio del arreglo");
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
                            agregarPersonas(obj.getString("results"));
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
        int n = 0;
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
        StringWriter writer = new StringWriter();
        while (-1 != (n = reader.read(buffer))) writer.write(buffer, 0, n);
        return writer.toString();
    }

    public void agregarPersonas(String s) {
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject persona = jsonArray.getJSONObject(i);
                this.lista_personas.add(new Persona(persona.getString("name"), persona.getString("height"),
                        persona.getString("mass"), persona.getString("hair_color"),
                        persona.getString("skin_color"),
                        persona.getString("eye_color"),
                        persona.getString("birth_year"),
                        persona.getString("gender")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
