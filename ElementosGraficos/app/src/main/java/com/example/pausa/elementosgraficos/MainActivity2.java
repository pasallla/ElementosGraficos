package com.example.pausa.elementosgraficos;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {

    ListView lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String[] opciones = new String[] {"Opción1", "Opción 2", "Opción 3", "Opción 4"};

        lista = (ListView) findViewById(R.id.lista);

        ponerListaEnListView(this,lista,opciones);


        //Añadir onClick a los elementos
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 3) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                    builder.setTitle("Alerta!!!");
                    builder.setMessage("¿Quieres continuar?");
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity2.this.finish();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

                Toast.makeText(MainActivity2.this, "Has pulsado la opción " + (position + 1) + " de la lista", Toast.LENGTH_SHORT).show();

            }
        });

    }

    //Añadir array de stings a un ListView
    private void ponerListaEnListView(Context context, ListView listView, String[] opcionesLista){
        ArrayAdapter<String> adaptador;

        adaptador = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,opcionesLista);

        listView.setAdapter(adaptador);
    }


}
