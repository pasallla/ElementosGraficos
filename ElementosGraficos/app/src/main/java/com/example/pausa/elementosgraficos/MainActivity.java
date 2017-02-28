package com.example.pausa.elementosgraficos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.opcion1:
                Toast.makeText(this,"Has seleccionado la opción 1",Toast.LENGTH_LONG).show();
                Log.e("Pau.ElementosGraficos","Has seleccionado la opción 1");
                return true;
            case R.id.opcion2:
                Toast.makeText(this,"Has seleccionado la opción 2",Toast.LENGTH_LONG).show();
                Log.e("Pau.ElementosGraficos","Has seleccionado la opción 2");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
