package com.example.alertaumg.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alertaumg.Entidades.Usuarios;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Modelos.Usuario;
import com.example.alertaumg.R;
import com.example.alertaumg.SeguirUsuario;
import com.example.alertaumg.Utilidades.APIUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.ViewHolder> implements  View.OnClickListener{

    public Usuario usuario;
    public int posicionPublica;
    public int miId;
    LayoutInflater inflater;
    ArrayList<Usuarios> model;
    private View.OnClickListener listener;


    public AdaptadorUsuarios(Context context, ArrayList<Usuarios> model ){

        this.inflater = LayoutInflater.from(context);
        this.model = model;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cardview_usuarios,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){

        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String nombre = model.get(position).getNombre();
        String direccion = model.get(position).getDireccion();

        usuario= model.get(position).getUsuario();

        holder.nombre.setText(nombre);
        holder.direccion.setText(direccion);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View view) {

        if(listener!=null){

            listener.onClick(view);

        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre,direccion;
        Button btnPerfil;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre_carview);
            direccion = itemView.findViewById(R.id.direccion_carview);
            //btnPerfil = itemView.findViewById(R.id.btnPerfil);

        /*    btnPerfil.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), SeguirUsuario.class);

                    intent.putExtra("id_usuario",miId);
                    intent.putExtra("id_confianza",usuario.getId());
                    intent.putExtra("nombre_confianza",usuario.getNombre());
                    intent.putExtra("apellido_confianza",usuario.getApellido());
                    intent.putExtra("telefono_confianza",usuario.getNumero_telefono());
                    intent.putExtra("direccion_confianza",usuario.getDireccion());

                    itemView.getContext().startActivity(intent);
                }
            });
          */
        /*

            btnSeguir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{

                        APIUtils.getUsuarioConfianzaService().registrarUsuarioConfianza(id_user,id_user_confianza).enqueue(new Callback<RespuestaAPI<String>>() {
                            @Override
                            public void onResponse(Call<RespuestaAPI<String>> call, Response<RespuestaAPI<String>> response) {
                                if (response.isSuccessful()) {
                                    RespuestaAPI<String> respuesta = response.body();

                                    if ( respuesta.getCodigo() == 1 ){

                                        Toast.makeText(view.getContext().getApplicationContext(),"Contacto de emergencia agregado",Toast.LENGTH_SHORT).show();

                                    }else if ( respuesta.getCodigo() == 0 ){
                                        Toast.makeText(view.getContext().getApplicationContext(), respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(view.getContext(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RespuestaAPI<String>> call, Throwable t) {

                            }
                        });

                    }catch (Exception e){

                    }

                }
            });
*/
        }
    }
}
