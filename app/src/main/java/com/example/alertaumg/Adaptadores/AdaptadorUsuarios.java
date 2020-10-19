package com.example.alertaumg.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alertaumg.Entidades.Usuarios;
import com.example.alertaumg.R;

import java.util.ArrayList;

public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.ViewHolder> implements  View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<Usuarios> model;
    private View.OnClickListener listener;

    public AdaptadorUsuarios(Context context,ArrayList<Usuarios> model ){

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

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre_carview);
            direccion = itemView.findViewById(R.id.direccion_carview);
        }
    }
}
