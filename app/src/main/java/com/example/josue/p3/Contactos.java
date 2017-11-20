package com.example.josue.p3;
//Importación de librerías
import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Contactos extends AppCompatActivity {
    //Entradas: savedInstanceState
    //Salidas: No aplica
    //Restricciones: Entrada válida
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Refrescar();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Contactos.this);
                View vie = getLayoutInflater().inflate(R.layout.dialog_contact, null);
                final EditText iden = (EditText) vie.findViewById(R.id.ident);
                final EditText desc = (EditText) vie.findViewById(R.id.descripcion);
                Button button = (Button) vie.findViewById(R.id.button);
                builder.setView(vie);
                final AlertDialog dialog = builder.create();
                dialog.show();
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (!iden.getText().toString().isEmpty() && !desc.getText().toString().isEmpty()) {
                            try {
                                ContactoOpenHelper helper= new ContactoOpenHelper(Contactos.this);
                                SQLiteDatabase database = helper.getWritableDatabase();
                                ContentValues values= new ContentValues();
                                values.put(ContactoReaderContact.Categoria.IDENTIFICADOR,iden.getText().toString());
                                values.put(ContactoReaderContact.Categoria.DESCRIPCION, desc.getText().toString());
                                long insert = database.insert(ContactoReaderContact.Categoria.TABLE_NAME, null, values);
                                database.close();
                                Toast.makeText(Contactos.this, "Categoría " + iden.getText() + " ha sido agregado exitosamente.", Toast.LENGTH_SHORT).show();
                                Refrescar();
                                dialog.dismiss();
                            } catch (Exception e) {
                                Toast.makeText(Contactos.this, "Ha ocurrido un error al agregar este dato. Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Contactos.this, "Rellene todos los campos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    //Entradas: Ninguna
    //Salidas: No aplica
    //Restricciones: Ninguna
    public void Refrescar() {
        ContactoOpenHelper helper = new ContactoOpenHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        TableLayout stk = (TableLayout) findViewById(R.id.table);
        stk.removeAllViews();
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText("Identificador");
        tv0.setTextColor(Color.rgb(0, 69, 124));
        tv0.setGravity(Gravity.CENTER);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText("Descripción");
        tv1.setTextColor(Color.rgb(0, 69, 124));
        tv1.setGravity(Gravity.CENTER);
        tbrow0.addView(tv1);
        TextView tv = new TextView(this);
        tv.setText("Opción");
        tv.setTextColor(Color.rgb(0, 69, 124));
        tv.setGravity(Gravity.CENTER);
        tbrow0.addView(tv);
        TextView tve = new TextView(this);
        tve.setText("Opción");
        tve.setTextColor(Color.rgb(0, 69, 124));
        tve.setGravity(Gravity.CENTER);
        tbrow0.addView(tve);
        stk.addView(tbrow0);
        Cursor c = database.rawQuery("SELECT " + ContactoReaderContact.Categoria.IDENTIFICADOR+ "," + ContactoReaderContact.Categoria.DESCRIPCION + " FROM " + ContactoReaderContact.Categoria.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                final String id = c.getString(0);
                String descrip = c.getString(1);
                TableRow tbrow = new TableRow(this);
                TextView t1v = new TextView(this);
                t1v.setText(id);
                t1v.setTextColor(Color.BLACK);
                t1v.setGravity(Gravity.CENTER);
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                t2v.setText(descrip);
                t2v.setTextColor(Color.BLACK);
                t2v.setGravity(Gravity.CENTER);
                tbrow.addView(t2v);
                Button EliminarBtn2 = new Button(this);
                EliminarBtn2.setText("Eliminar");
                EliminarBtn2.setOnClickListener(handleOnClickDelete(id));
                tbrow.addView(EliminarBtn2);
                Button EditarBtn2 = new Button(this);
                EditarBtn2.setText("Editar");
                EditarBtn2.setOnClickListener(handleOnClickEditContact(id,descrip));
                tbrow.addView(EditarBtn2);
                stk.addView(tbrow);
            } while (c.moveToNext());
        }
        c.close();
    }
    //Entradas: db
    //Salidas: No aplica
    //Restricciones: Entrada válida
    View.OnClickListener handleOnClickDelete(final String id) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                ContactoOpenHelper helper= new ContactoOpenHelper(Contactos.this);
                SQLiteDatabase database = helper.getReadableDatabase();
                database.delete(ContactoReaderContact.Categoria.TABLE_NAME,ContactoReaderContact.Categoria.IDENTIFICADOR+"=?",new String[]{id});
                Toast.makeText(Contactos.this,"La categoría "+id+" ha sido eliminada correctamente.",Toast.LENGTH_SHORT).show();
                Refrescar();
            }
        };

    }

    //Entradas: id
    //Salidas: No aplica
    //Restricciones: Entrada válida
    View.OnClickListener handleOnClickEditContact(final String id, final String descrip) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Contactos.this);
                View vie = getLayoutInflater().inflate(R.layout.dialog_contact,null);
                final EditText iden = (EditText) vie.findViewById(R.id.ident);
                final EditText desc = (EditText) vie.findViewById(R.id.descripcion);
                iden.setEnabled(false);
                Button button = (Button) vie.findViewById(R.id.button);
                iden.setText(id);
                desc.setText(descrip);

                ContactoOpenHelper helper= new ContactoOpenHelper(Contactos.this);
                final SQLiteDatabase database = helper.getReadableDatabase();

                String[] campos = new String[] {ContactoReaderContact.Categoria.IDENTIFICADOR,ContactoReaderContact.Categoria.DESCRIPCION};
                String[] args = new String[] {id};

                /*Cursor c = database.query(ContactoReaderContact.Categoria.TABLE_NAME, campos, ContactoReaderContact.Categoria.IDENTIFICADOR+"=?", args, null, null, null);
                if(c.moveToFirst()){
                    do{
                        final String id1 = c.getString(0);
                        desc.setText(id);
                        iden.setText(id1);
                    }while(c.moveToNext());
                }*/
                builder.setView(vie);
                final AlertDialog dialog = builder.create();
                dialog.show();
                button.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View v) {
                        if(!iden.getText().toString().isEmpty()&&!desc.getText().toString().isEmpty()){
                            try {
                                database.execSQL("UPDATE "+ContactoReaderContact.Categoria.TABLE_NAME+" SET "+ContactoReaderContact.Categoria.DESCRIPCION+"='"+desc.getText().toString()+"' WHERE "+ContactoReaderContact.Categoria.IDENTIFICADOR+"='"+id+"'");
                                Toast.makeText(Contactos.this,"Categoría "+id+" ha sido editado exitosamente.",Toast.LENGTH_SHORT).show();
                                Refrescar();
                                dialog.dismiss();
                            }catch (Exception e){
                                Toast.makeText(Contactos.this,"Ha ocurrido un error al editar este dato. Error: "+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Contactos.this,"Rellene todos los campos.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
    }
}
