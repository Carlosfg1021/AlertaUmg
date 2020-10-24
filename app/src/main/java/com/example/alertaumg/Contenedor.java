package com.example.alertaumg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Contenedor extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView menuNavegacion;
    com.google.android.material.navigation.NavigationView menuLateral;
    private androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    TextView emailMenu, nombreUsuarioMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor);
        menuNavegacion = (BottomNavigationView) findViewById(R.id.menu_navegacion);
        menuNavegacion.setSelectedItemId(R.id.btn_nav_alerta_rapida);

        //menuNavegacion.setOnNavigationItemSelectedListener(this);

        setToolBar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        menuNavegacion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                        fragment = new Notificaciones(getIntent().getExtras().getInt("id_usuario"));
                        break;

                    case R.id.btn_nav_usuariosconfianza:
                        fragment = new UsuarioConfianza();
                        break;

                }

                return cargarFragmento(fragment);
            }
        });

        menuLateral = findViewById(R.id.navigationId);//Obtenemos el objeto del xml

        View headerView = menuLateral.getHeaderView(0);
        nombreUsuarioMenu = (TextView) headerView.findViewById(R.id.menuNombreUsuario);
        emailMenu = (TextView) headerView.findViewById(R.id.menuCorreoUsuario);

        nombreUsuarioMenu.setText(getIntent().getStringExtra("nombre")+" "+ getIntent().getStringExtra("apellido"));
        emailMenu.setText(getIntent().getStringExtra("correo"));

    //Añadimos evento para cargar la opcion seleccionada del menu en el contenedor principal

        menuLateral.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.btn_nav_alerta_rapida_iz:
                        fragment = new BotonPanico();
                        menuNavegacion.setSelectedItemId(R.id.btn_nav_alerta_rapida);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.btn_nav_alerta_especifica_iz:
                        fragment = new TipoAlerta();
                        menuNavegacion.setSelectedItemId(R.id.btn_nav_alerta_especifica);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.btn_nav_mi_perfil_iz:
                        fragment = new Perfil();
                        menuNavegacion.setSelectedItemId(R.id.btn_nav_mi_perfil);
                        drawerLayout.closeDrawers();
                        break;

                        case R.id.btn_nav_notification_iz:
                        fragment = new Notificaciones(getIntent().getExtras().getInt("id_usuario"));
                        menuNavegacion.setSelectedItemId(R.id.btn_nav_notification);
                        drawerLayout.closeDrawers();
                        break;

                   /* case R.id.btn_nav_usuarios_iz:
                        fragment = new UsuarioConfianza();
                        menuNavegacion.setSelectedItemId(R.id.btn_nav_usuariosconfianza);
                        drawerLayout.closeDrawers();
                        break;*/

                    case R.id.btn_contactos_emergencia_iz:
                        Intent intent= new Intent (Contenedor.this, ContactosEmergenciaActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.btn_cerrar:
                         drawerLayout.closeDrawers();
                         break;

                }
                return cargarFragmento(fragment);
            }
        });

    }

    //Función para cargar el fragmento al contenedor principal

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
                fragment = new Notificaciones(getIntent().getExtras().getInt("id_usuario"));
                break;

            case R.id.btn_nav_usuariosconfianza:
                fragment = new UsuarioConfianza();
                break;

        }

        return cargarFragmento(fragment);

    }

//Construimos el boton para desplegar el menú de opciones
    private void setToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Sobreescribimos el método para redireccionar al menu de opciones desde el botón toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}