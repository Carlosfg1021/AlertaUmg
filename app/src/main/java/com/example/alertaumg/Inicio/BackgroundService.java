package com.example.alertaumg.Inicio;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.alertaumg.Entidades.Usuarios;
import com.example.alertaumg.Modelos.Alerta;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Modelos.Usuario;
import com.example.alertaumg.R;
import com.example.alertaumg.Utilidades.APIUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BackgroundService extends Service {

    private Context thisContext=this;
    public int id_usuario;
    @Override
    public void onCreate(){

    }

    @Override
    public int onStartCommand(Intent intent, int flag,int idProcess){
        //Aquí debemos iniciar lo que queremos ejecutar en se
//---------En esta ocasión será un hilo en segundo plano que se seguirá ejecutando cada 10 segundos
        //Aunque cerremos la aplicación.....

       Toast.makeText(thisContext,"Iniciado proceso consulta avanzada de alertas",Toast.LENGTH_SHORT).show();
       try{

           while(true) {
               Thread.sleep(1000);
               //Toast.makeText(thisContext, "Proceso 2", Toast.LENGTH_SHORT).show();
               APIUtils.getAlertaService().obtenerAlertasNoVistas(17).enqueue(new Callback<RespuestaAPI<List<Alerta>>>() {
                   @Override
                   public void onResponse(Call<RespuestaAPI<List<Alerta>>> call, Response<RespuestaAPI<List<Alerta>>> response) {
                       if (response.isSuccessful()) {
                           RespuestaAPI<List<Alerta>> respuesta = response.body();

                           if ( respuesta.getCodigo() == 1 ){
                               List<Alerta> alertaLista = respuesta.getData();
                               if ( alertaLista != null ){

                                   for(int i=0; i<alertaLista.size(); i++){
                                       NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                                       Notification notify=new Notification.Builder
                                               (getApplicationContext()).setContentTitle("Alerta").setContentText("Un amigo está en peligro").
                                               setContentTitle(alertaLista.get(i).getNombre_usuario()).setSmallIcon(R.drawable.ic_notificacion).build();

                                       notify.flags |= Notification.FLAG_AUTO_CANCEL;
                                       notif.notify(0, notify);
                                   }

                               }else{

                               }
                           }else if ( respuesta.getCodigo() == 0 ){
                               Toast.makeText(thisContext, respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                           }
                       }else{
                           Toast.makeText(thisContext, "Error en el servidor.", Toast.LENGTH_SHORT).show();
                       }

                   }

                   @Override
                   public void onFailure(Call<RespuestaAPI<List<Alerta>>> call, Throwable t) {

                   }
               });
              return  START_STICKY;
          }

       }catch (Exception e){

       }
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
            //Aquí destruimos el proceso
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void proceso(){
        new Thread(new Runnable() {
            public void run() {
                try {
                    while(true){
                        Toast.makeText(thisContext,"Hilo de ejecución",Toast.LENGTH_SHORT).show();
                        Thread.sleep(1000);
                    }

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private int devolver(){
        return START_STICKY;
    }

}
