package com.example.alumno.examen2ev;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(crearPantalla());
    }

    private LinearLayout crearPantalla() {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout principal = (LinearLayout) inflater.inflate(R.layout.activity_main, null, false);
        LinearLayout empresas_panel = (LinearLayout) principal.findViewById(R.id.empresas_panel);
        BD_Empresas BDEmpresas = new BD_Empresas(this, "empresas", null, 1);
        SQLiteDatabase db = BDEmpresas.getReadableDatabase();
        if (db != null) {
            View.OnClickListener evento = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button boton = (Button) view;
                    String empresa = (String) boton.getText();
                    new ConectarServidor().execute(empresa);
                }
            };
            String sql = "SELECT * FROM empresas";
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                Button botonEmpresa = new Button(this);
                botonEmpresa.setText(cursor.getString(0));
                botonEmpresa.setOnClickListener(evento);
                empresas_panel.addView(botonEmpresa);
            }
        }
        return principal;
    }

    class ConectarServidor extends AsyncTask<String, Void, Void> {
        JSONObject jsonObject;

        @Override
        protected Void doInBackground(String... nombre) {
            BufferedReader reader = null;
            try {
                // Creamos la URL
                URL url = new URL("http://10.0.2.2/infodb.php");
                // Creamos los parametros que vamos a pasar por POST
                String urlParameters = "nombre=" + URLEncoder.encode(nombre[0], "UTF-8");
                // Abrimos la conexion
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                // Preparamos que tipo de informacion y tamaño vamos a enviar por POST
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
                con.setRequestProperty("Content-Language", "en-US");
                con.setUseCaches(false);
                con.setDoInput(true);
                con.setDoOutput(true);
                // Abrimos la url y le metemos los bytes a la cadena
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();
                // Como estoy consultando una unica empresa, se que me devolvera una cadena JSON
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                // Cremo mi JSON array obtenido con esa cadena
                JSONArray jsonArray = new JSONArray(reader.readLine());
                // y obtengo el primer objeto del json que en la informacion que busco
                jsonObject = jsonArray.getJSONObject(0);
                // desconecto con la web
                con.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (jsonObject != null) {
                try {
                    Intent intent = new Intent(getApplicationContext(), Empresa.class);
                    // antes de lanzar el intent le pondremos la informacion que queremos que muestre
                    intent.putExtra("nombre", jsonObject.getString("nombre"));
                    intent.putExtra("ventas", jsonObject.getString("ventas"));
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
