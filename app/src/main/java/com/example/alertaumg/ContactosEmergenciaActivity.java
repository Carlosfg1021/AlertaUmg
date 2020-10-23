package com.example.alertaumg;

import android.content.Intent;
import android.os.Bundle;

import com.example.alertaumg.Adaptadores.AdaptadorContacto;
import com.example.alertaumg.Adaptadores.AdaptadorNotificacion;
import com.example.alertaumg.Entidades.ConctactosU;
import com.example.alertaumg.Entidades.NotificacionU;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ContactosEmergenciaActivity extends AppCompatActivity {


   List<ConctactosU> elements;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos_emergencia);


        init();


    }


    public void init(){

        elements = new ArrayList<>();
        elements.add(new ConctactosU("Bomberos Vluntarios","79331103"));

        AdaptadorContacto adaptadorContacto = new AdaptadorContacto(elements,this);
        RecyclerView recyclerView = findViewById(R.id.recycleview_contactos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorContacto);
    }



}