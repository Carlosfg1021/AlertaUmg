package com.example.alertaumg;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alertaumg.Funcionalidad.EmitirAlerta;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Utilidades.APIUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BotonPanico#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BotonPanico extends Fragment {

    TextView textView;
    Button btnAlertaRapida;

    LocationManager locationManager;
    double longitudeBest, latitudeBest;
    double longitudeGPS, latitudeGPS;
    double longitudeNetwork, latitudeNetwork;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BotonPanico() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment boton_panico.
     */
    // TODO: Rename and change types and number of parameters
    public static BotonPanico newInstance(String param1, String param2) {
        BotonPanico fragment = new BotonPanico();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_boton_panico, container, false);

        textView = view.findViewById(R.id.txtalerta);
        btnAlertaRapida = view.findViewById(R.id.btnAlertaRapida);

        textView.setText(getActivity().getIntent().getExtras().getString("nombre")+" "+ getActivity().getIntent().getExtras().getString("apellido"));

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);//Inicializamos servicio de ubicación

        toggleBestUpdates();//Obtenemos ubicación

        btnAlertaRapida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //locationManager.removeUpdates(locationListenerBest);//Terminamos proceso de geolocalización

                //Validador para saber si la ubicación ha sido obtenida


                if (String.valueOf(latitudeBest).equals("0.0") == false && String.valueOf(latitudeBest).equals("")==false
                        && String.valueOf(longitudeBest).equals("0.0") == false && String.valueOf(longitudeBest).equals("")==false) {

                        Toast.makeText(getContext(),latitudeBest+" "+longitudeBest,Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                        dialogo1.setTitle("¡Advertencia!");
                        dialogo1.setMessage("¿Está seguro de enviar la alerta?"+"\n"+
                                "                  ¡RECUERDA!"+"\n"+"      ENVIAR ALERTAS FALSAS"+"\n"+"           PUEDE SER MOTIVO"+"\n"+
                                "     DE SUSPENSIÓN DE CUENTA");
                        dialogo1.setCancelable(false);//evitamos que se cancele el dialogo

                        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialogo1, int id) {
                                //----------INICIO DE PROCESO DE EMISIÓN DE ALERTA RÁPIDA---------------------------------------
                                try {
                                    Gson gson = new GsonBuilder().setLenient().create();

                                    APIUtils.getAlertaService().registrarAlerta(12, "¡Necesito ayuda, es una emergencia!", String.valueOf(longitudeBest), String.valueOf(latitudeBest), getActivity().getIntent().getExtras().getInt("id_usuario")).enqueue(new Callback<RespuestaAPI<String>>() {
                                        @Override
                                        public void onResponse(Call<RespuestaAPI<String>> call, Response<RespuestaAPI<String>> response) {

                                            if (response.isSuccessful()) {

                                                RespuestaAPI<String> respuesta = response.body();

                                                if (respuesta.getCodigo() == 1) {
                                                    String objeto = respuesta.getData();
                                                    if (objeto != null) {
                                                        Toast.makeText(getContext(), respuesta.toString(), Toast.LENGTH_SHORT).show();


                                                    } else {
                                                        Toast.makeText(getContext(), "¡Alerta enviada!", Toast.LENGTH_SHORT).show();

                                                    }
                                                } else if (respuesta.getCodigo() == 0) {

                                                    Toast.makeText(getContext(), respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                                                }

                                            } else {
                                                Toast.makeText(getContext(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<RespuestaAPI<String>> call, Throwable t) {

                                        }
                                    });

                                } catch (Exception e) {

                                }
//------------------FIN DE PROCESO DE ENVÍO DE ALERTA RÁPIDA----------------------------------------
                            }
                        });


                        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {

                            }
                        });

                        dialogo1.show();


                }else{
                    Toast.makeText(getContext(),"No se pudo obtener la ubicación",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }



    //----------------------------------PROCESO DE GEOLOCALIZACIÓN---------------------------

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Activa la ubicación gps")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "para usar este servicio")
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
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 2 * 20 * 1000, 10, locationListenerGPS);
            button.setText(R.string.pause);
        }
    }

    public void toggleBestUpdates() {
        if (!checkLocation())
            return;


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            Toast.makeText(getContext(), "Proveedor más preciso: " + provider + " con un mínimo error de 10 metros", Toast.LENGTH_LONG).show();
        }

    }

    public void toggleNetworkUpdates(View view) {
        if (!checkLocation())
            return;
        Button button = (Button) view;
        if (button.getText().equals(getResources().getString(R.string.pause))) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            locationManager.removeUpdates(locationListenerNetwork);
            button.setText(R.string.resume);
        }
        else {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 10 * 1000, 10, locationListenerNetwork);
            Toast.makeText(getContext(), "Network provider started running", Toast.LENGTH_LONG).show();
            button.setText(R.string.pause);
        }
    }

    private final LocationListener locationListenerBest = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeBest = location.getLongitude();
            latitudeBest = location.getLatitude();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //txtLongitud.setText(longitudeBest + "");
                    //txtLatitud.setText(latitudeBest + "");
                    Toast.makeText(getContext(),longitudeBest+", "+latitudeBest,Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), "Ubicación actualizada automáticamente, próxima actualización en 10 seg", Toast.LENGTH_SHORT).show();
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

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //longitudeValueNetwork.setText(longitudeNetwork + "");
                    //latitudeValueNetwork.setText(latitudeNetwork + "");
                    Toast.makeText(getContext(), "Network Provider update", Toast.LENGTH_SHORT).show();
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
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //longitudeValueGPS.setText(longitudeGPS + "");
                    //latitudeValueGPS.setText(latitudeGPS + "");
                    Toast.makeText(getContext(), "Proveedor gps actualizado", Toast.LENGTH_SHORT).show();
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