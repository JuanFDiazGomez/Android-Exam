package com.example.alumno.examen2ev;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alumno on 3/03/17.
 */

public class BD_Empresas extends SQLiteOpenHelper {
    String sqlCreate = "CREATE TABLE empresas(nombre TEXT)";

    public BD_Empresas(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // creamos la tabla con unos valores iniciales
        if(sqLiteDatabase!=null) {
            sqLiteDatabase.execSQL(sqlCreate);
            sqLiteDatabase.execSQL("INSERT INTO empresas (nombre) VALUES ('Mi Empresa, Inc.')");
            sqLiteDatabase.execSQL("INSERT INTO empresas (nombre) VALUES ('The roaring trump, S.L.')");
            sqLiteDatabase.execSQL("INSERT INTO empresas (nombre) VALUES ('Patatas Bravas Corp.')");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS empresas");
            sqLiteDatabase.execSQL(sqlCreate);
        }
    }
}
