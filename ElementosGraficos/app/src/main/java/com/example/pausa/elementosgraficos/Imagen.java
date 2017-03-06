package com.example.pausa.elementosgraficos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;


public class Imagen extends AppCompatActivity {

    final private int SELECT_IMAGE = 1234;
    final private int TAKE_IMAGE = 4321;

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen);

        image = (ImageView) findViewById(R.id.image);
        image.setVisibility(View.INVISIBLE);

    }

    public void buscarImagen(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    public void hacerFoto(View v){
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,TAKE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent respuesta){
        Log.e("Pau.Actividades","dentro de onActivityResult");
        if(requestCode==SELECT_IMAGE && resultCode == RESULT_OK && respuesta != null) {
            Uri ruta = respuesta.getData();
            Toast.makeText(this, "Tengo la imagen: " + ruta, Toast.LENGTH_SHORT).show();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), ruta);
                image.setImageBitmap(bitmap);
                image.setVisibility(View.VISIBLE);
            } catch (IOException ex) {
                Toast.makeText(this, "Estoy en el catch", Toast.LENGTH_SHORT).show();
            }

        }else if(requestCode==TAKE_IMAGE && resultCode == RESULT_OK && respuesta != null){

            Bitmap imageBitmap = (Bitmap) respuesta.getExtras().get("data");
            image.setImageBitmap(imageBitmap);
            image.setVisibility(View.VISIBLE);

        }else{
            Toast.makeText(this,"Algo no ha ido bien", Toast.LENGTH_SHORT).show();
        }
    }

}
