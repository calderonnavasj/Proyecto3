package com.example.josue.p3;

import android.provider.BaseColumns;

/**
 * Created by josue on 26/10/2017.
 */

public class ContactoReaderContact {
    private ContactoReaderContact(){}//Constrcutor
    //Clase que posee variables
    public static class Categoria implements BaseColumns{
        //Declaración de variables
        public static final String TABLE_NAME = "CATEGORIA";
        public static final String IDENTIFICADOR = "IDENTIFICADOR";
        public static final String DESCRIPCION ="DESCRIPCION";
    }
}
