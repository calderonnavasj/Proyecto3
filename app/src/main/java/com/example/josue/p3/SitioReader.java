package com.example.josue.p3;

import android.provider.BaseColumns;

/**
 * Created by Yordi on 20/11/2017.
 */

public class SitioReader {
    public SitioReader() {
    }
    public static class Sitio implements BaseColumns{
        public static final String TABLE_NAME = "SITIO";
        public static final String NOMBRE = "NOMBRE";
        public static final String CATEGORIA ="CATEGORIA";
        public static final String TELEFONO ="TELEFONO";
    }
}
