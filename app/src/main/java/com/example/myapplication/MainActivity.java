package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;


import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    public static final String URL_SWAPI = "https://swapi.co/api/";
    private TextView textTitle;
    String nameResponse;
    Button peopleButton;
    Button planetsButton;
    JSONObject obj;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textTitle = findViewById(R.id.textView_titulo);
        peopleButton = findViewById(R.id.button_personajes);
        planetsButton = findViewById(R.id.button_planetas);

        peopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(v, "people/");
                textTitle.setText(nameResponse);
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
        });
    }

    public void makeCall (View v, String s) {
        callWebService(s);
    }

    public void callWebService(final String serviceEndPoint) {

        AsyncTask.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {

                try {
                    URL urlService = new URL(URL_SWAPI + serviceEndPoint);
                    HttpsURLConnection connection = (HttpsURLConnection) urlService.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream responseBody = connection.getInputStream();

                    if (connection.getResponseCode() == 200) {
                        // Success
                         nameResponse = getStringFromInputStream(responseBody);
                        obj = new JSONObject(nameResponse);
/*
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        jsonReader.beginObject(); // Start processing the JSON object

                        String key = jsonReader.nextName(); // Fetch the next key
                        String value = jsonReader.nextString();
                        obj = new JSONObject(jsonReader.nextString());

                        Log.v("INFO", value);
                        nameResponse = value;*/
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

    static String toNewString(InputStream input) throws  IOException{
        StringBuilder sb = new StringBuilder();
        String line = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
        while((line = br.readLine()) !=  null){
            sb.append(line);
        }
        return sb.toString();
    }
    public static String getStringFromInputStream(InputStream stream) throws IOException
    {
        int n = 0;
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(stream, "UTF8");
        StringWriter writer = new StringWriter();
        while (-1 != (n = reader.read(buffer))) writer.write(buffer, 0, n);
        return writer.toString();
    }
}