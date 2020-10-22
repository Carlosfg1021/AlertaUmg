package com.example.alertaumg.Funcionalidad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alertaumg.Contenedor;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.R;
import com.example.alertaumg.Utilidades.APIUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmitirAlerta extends AppCompatActivity {

    LocationManager locationManager;
    double longitudeBest, latitudeBest;
    double longitudeGPS, latitudeGPS;
    double longitudeNetwork, latitudeNetwork;

    ImageView imageView;
    Button botonfoto,btnUbicacion,btnCancelar,btnConfirmar;
    EditText txtDescripcion,txtLatitud,txtLongitud;
    TextView lblNombreAlerta;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private int id_tipo_alerta;
    private int id_usuario;
    private String imagenString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emitir_alerta);

        botonfoto = (Button) findViewById(R.id.botonfoto);
        btnUbicacion = (Button) findViewById(R.id.btnUbicacion);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);

        imageView = (ImageView) findViewById(R.id.imagenalerta);

        lblNombreAlerta = (TextView) findViewById(R.id.lblNombreAlerta);

        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtLatitud = findViewById(R.id.txtLatitud);
        txtLongitud = findViewById(R.id.txtLongitud);

        id_tipo_alerta=getIntent().getExtras().getInt("alerta");//Obtenemos alerta requerida
        id_usuario = getIntent().getExtras().getInt("id_usuario");//Obtenemos id del usuario

        lblNombreAlerta.setText(getIntent().getExtras().getString("tipoAlerta"));//Obtenemos nombre de la alerta

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);//Inicializamos servicio de ubicación

        botonfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamarIn();
            }
        });

        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Obteniendo ubicación...",Toast.LENGTH_SHORT).show();
                toggleBestUpdates();//Obtenemos ubicación
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//Cerramos activity
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Gson gson = new GsonBuilder().setLenient().create();

                    String descripcion = txtDescripcion.getText().toString();
                    String lat = txtLatitud.getText().toString();
                    String lon = txtLongitud.getText().toString();

                    APIUtils.getAlertaService().registrarAlerta(id_tipo_alerta,descripcion,lon,lat,id_usuario).enqueue(new Callback<RespuestaAPI<String>>() {
                        @Override
                        public void onResponse(Call<RespuestaAPI<String>> call, Response<RespuestaAPI<String>> response) {
                            if(response.isSuccessful()){

                                RespuestaAPI<String> respuesta = response.body();

                                if ( respuesta.getCodigo() == 1 ){
                                    String objeto = respuesta.getData();
                                    if ( objeto != null ){
                                        Toast.makeText(EmitirAlerta.this, respuesta.toString(), Toast.LENGTH_SHORT).show();
                                        finish();//Cerramos vista

                                    }else{
                                        Toast.makeText(getApplicationContext(), "¡Alerta enviada!", Toast.LENGTH_SHORT).show();
                                        finish();//Cerramos vista
                                    }
                                }else if ( respuesta.getCodigo() == 0 ){

                                    Toast.makeText(getApplicationContext(), respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                                }

                            } else{
                                Toast.makeText(getApplicationContext(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RespuestaAPI<String>> call, Throwable t) {

                        }
                    });
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                }
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
/*
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imagenBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] imagen = stream.toByteArray();
            imagenString = new String(imagen);
            Toast.makeText(getApplicationContext(),imagenString,Toast.LENGTH_SHORT).show();

 */
        }

    }

//----------------------------------PROCESO DE GEOLOCALIZACIÓN---------------------------

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void toggleGPSUpdates(View view) {
        if (!checkLocation())
            return;
        Button button = (Button) view;
        if (button.getText().equals(getResources().getString(R.string.pause))) {
            locationManager.removeUpdates(locationListenerGPS);
            button.setText(R.string.resume);
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 2 * 20 * 1000, 10, locationListenerGPS);
            button.setText(R.string.pause);
        }
    }

    public void toggleBestUpdates() {
        if (!checkLocation())
            return;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        //locationManager.removeUpdates(locationListenerBest);


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            locationManager.requestLocationUpdates(provider,  10 * 1000, 10, locationListenerBest);
            //button.setText(R.string.pause);
            Toast.makeText(this, "Proveedor más preciso: " + provider + " con un mínimo error de 10 metros", Toast.LENGTH_LONG).show();
        }

    }

    public void toggleNetworkUpdates(View view) {
        if (!checkLocation())
            return;
        Button button = (Button) view;
        if (button.getText().equals(getResources().getString(R.string.pause))) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            locationManager.removeUpdates(locationListenerNetwork);
            button.setText(R.string.resume);
        }
        else {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 10 * 1000, 10, locationListenerNetwork);
            Toast.makeText(this, "Network provider started running", Toast.LENGTH_LONG).show();
            button.setText(R.string.pause);
        }
    }

    private final LocationListener locationListenerBest = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeBest = location.getLongitude();
            latitudeBest = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtLongitud.setText(longitudeBest + "");
                    txtLatitud.setText(latitudeBest + "");
                    Toast.makeText(getApplicationContext(),longitudeBest+", "+latitudeBest,Toast.LENGTH_LONG).show();
                    Toast.makeText(EmitirAlerta.this, "Ubicación actualizada automáticamente, próxima actualización en 10 seg", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeNetwork = location.getLongitude();
            latitudeNetwork = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //longitudeValueNetwork.setText(longitudeNetwork + "");
                    //latitudeValueNetwork.setText(latitudeNetwork + "");
                    Toast.makeText(EmitirAlerta.this, "Network Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {

        }
        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeGPS = location.getLongitude();
            latitudeGPS = location.getLatitude();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //longitudeValueGPS.setText(longitudeGPS + "");
                    //latitudeValueGPS.setText(latitudeGPS + "");
                    Toast.makeText(EmitirAlerta.this, "Proveedor gps actualizado", Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }
        @Override
        public void onProviderDisabled(String s) {
        }
    };


}