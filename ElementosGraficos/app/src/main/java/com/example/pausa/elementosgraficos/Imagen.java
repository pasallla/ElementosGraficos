package com.example.pausa.elementosgraficos;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Imagen extends AppCompatActivity {

    final private int SELECT_IMAGE = 1234;
    final private int TAKE_IMAGE = 4321;

    private ImageView image;

    Uri mPhotoUri;
    Uri fileUri;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen);

        image = (ImageView) findViewById(R.id.image);
        image.setVisibility(View.INVISIBLE);

    }

    public void buscarImagen(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    public void hacerFoto(View v) {

        //ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //mPhotoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        try {
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            Log.d("Pau.ElementosGraficos","fileUri: " + fileUri);
        } catch (Exception ex) {
            Log.e("Pau.ElementosGraficos", ex.toString());
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, TAKE_IMAGE);

    }

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = null;
        mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        Log.d("Pau.ElementosGraficos", "getExternalStorage: "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());
        Log.d("Pau.ElementosGraficos", "mediaStorageDir: "+mediaStorageDir.toString());

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Pau.ElementosGraficos", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
            Log.e("Pau.ElementosGraficos", "type=MEDIA_TYPE_IMAGE");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
            Log.e("Pau.ElementosGraficos", "type=MEDIA_TYPE_VIDEO");
        } else {
            Log.e("Pau.ElementosGraficos", "type=(null)");
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent respuesta) {
        Log.e("Pau.Actividades", "dentro de onActivityResult");
        Uri ruta = null;

        Log.d("Pau.ElementosGraficos", "SELECT_IMAGE: "+SELECT_IMAGE);
        Log.d("Pau.ElementosGraficos", "TAKE_IMAGE: "+TAKE_IMAGE);
        Log.d("Pau.ElementosGraficos", "RESULT_OK: "+RESULT_OK);
        Log.d("Pau.ElementosGraficos", "requestCode: "+requestCode);
        Log.d("Pau.ElementosGraficos", "resultCode: "+resultCode);
        Log.d("Pau.ElementosGraficos", "respuesta: "+respuesta);

        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && respuesta != null) {
            ruta = respuesta.getData();
        } else if (requestCode == TAKE_IMAGE && resultCode == RESULT_OK) {
            ruta = fileUri;
        } else {
            Toast.makeText(this, "Algo no ha ido bien", Toast.LENGTH_SHORT).show();
        }
        if (ruta != null) {
            Toast.makeText(this, "Tengo la imagen: " + ruta, Toast.LENGTH_SHORT).show();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), ruta);
                image.setImageBitmap(bitmap);
                image.setVisibility(View.VISIBLE);
            } catch (IOException ex) {
                Toast.makeText(this, "Estoy en el catch", Toast.LENGTH_SHORT).show();
            }

            enviarFotoPorEmail(ruta,new String[]{"pasallla@epsg.upv.es"},"Envio de foto por email","Esto es el texto");
        } else {
            Toast.makeText(this, "ruta = null !!!!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void enviarFotoPorEmail(Uri foto, String[] emails, String asunto, String texto){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("image/jpeg");
        i.putExtra(Intent.EXTRA_STREAM, foto);

        i.putExtra(Intent.EXTRA_EMAIL, emails);
        i.putExtra(Intent.EXTRA_SUBJECT, asunto);
        i.putExtra(Intent.EXTRA_TEXT, texto);

    }

}
