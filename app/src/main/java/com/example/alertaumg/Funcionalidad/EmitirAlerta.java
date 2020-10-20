package com.example.alertaumg.Funcionalidad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.alertaumg.R;

public class EmitirAlerta extends AppCompatActivity {

    ImageView imageView;
    Button botonfoto;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emitir_alerta);

        botonfoto = (Button) findViewById(R.id.botonfoto);
        imageView = (ImageView) findViewById(R.id.imagenalerta);

        botonfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamarIn();
            }
        });

    }

    public void llamarIn(){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imagenBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imagenBitmap);
        }

    }
}