package com.example.alumno.examen2ev;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Empresa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout pantalla = (LinearLayout) inflater.inflate(R.layout.activity_empresa,null,false);
        TextView nombreEmpresa = (TextView) pantalla.findViewById(R.id.nombreEmpresa);
        TextView ventas = (TextView) pantalla.findViewById(R.id.ventas);
        // cogemos la informacion almacenada en el intent y se la ponemos a su casilla correspondiente
        nombreEmpresa.setText(getIntent().getStringExtra("nombre"));
        ventas.setText(getIntent().getStringExtra("ventas"));
        setContentView(pantalla);
    }
}
