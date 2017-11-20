package com.example.josue.p3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by josue on 26/10/2017.
 */

public class ContactoOpenHelper extends SQLiteOpenHelper {

    //Declaración de atributos
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="p3.db";
    private static final String create = "CREATE TABLE "+ ContactoReaderContact.Categoria.TABLE_NAME
            + " (" + ContactoReaderContact.Categoria.IDENTIFICADOR +" TEXT PRIMARY KEY, "+ ContactoReaderContact.Categoria.DESCRIPCION
            + " TEXT);";
    private static final String upgrade ="DROP TABLE IF EXISTS " + ContactoReaderContact.Categoria.TABLE_NAME;
    //Constructor
    public ContactoOpenHelper(Context context){super(context, DATABASE_NAME,null,DATABASE_VERSION);}
    //Método de creación de la BD
    //Entradas: db
    //Salidas: No aplica
    //Restricciones: Entrada válida
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create);
    }
    //Método de actualización de la BD
    //Entradas: db,oldVersion,newVersion
    //Salidas: No aplica
    //Restricciones: Entrada válida
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(upgrade);
    }

}
