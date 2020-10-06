package com.example.alertaumg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Contenedor extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView menuNavegacion;
    com.google.android.material.navigation.NavigationView menuLateral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor);
        menuNavegacion = (BottomNavigationView) findViewById(R.id.menu_navegacion);
        menuNavegacion.setSelectedItemId(R.id.btn_nav_alerta_rapida);
        menuNavegacion.setOnNavigationItemSelectedListener(this);

        menuLateral = findViewById(R.id.navigationId);
        menuLateral.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.btn_nav_alerta_rapida_iz:
                        fragment = new BotonPanico();
                        menuNavegacion.setSelectedItemId(R.id.btn_nav_alerta_rapida);
                        break;
                    case R.id.btn_nav_alerta_especifica_iz:
                        fragment = new TipoAlerta();
                        menuNavegacion.setSelectedItemId(R.id.btn_nav_alerta_especifica);
                        break;
                    case R.id.btn_nav_mi_perfil_iz:
                        fragment = new Perfil();
                        menuNavegacion.setSelectedItemId(R.id.btn_nav_mi_perfil);
                        break;
                    case R.id.btn_nav_notification_iz:
                        fragment = new Notificaciones();
                        menuNavegacion.setSelectedItemId(R.id.btn_nav_notification);
                        break;

                }
                return cargarFragmento(fragment);

            }
        });


    }

    public boolean cargarFragmento(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.framContenedor, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.btn_nav_alerta_rapida:
                fragment = new BotonPanico();
                break;
            case R.id.btn_nav_alerta_especifica:
                fragment = new TipoAlerta();
                break;
            case R.id.btn_nav_mi_perfil:
                fragment = new Perfil();
                break;
            case R.id.btn_nav_notification:
                fragment = new Notificaciones();
                break;

        }
        return cargarFragmento(fragment);

    }
}