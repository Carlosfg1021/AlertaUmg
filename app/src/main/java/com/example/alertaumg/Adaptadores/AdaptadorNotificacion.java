package com.example.alertaumg.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alertaumg.Entidades.NotificacionU;
import com.example.alertaumg.R;

import java.text.BreakIterator;
import java.util.ArrayList;

public class AdaptadorNotificacion extends RecyclerView.Adapter<AdaptadorNotificacion.ViewHolder> implements  View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<NotificacionU> model;
    private View.OnClickListener listener;

    public AdaptadorNotificacion(Context context,ArrayList<NotificacionU> model){

        this.inflater = LayoutInflater.from(context);
        this.model = model;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cardview_notificacion,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){

        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorNotificacion.ViewHolder holder, int position) {
        String nombre = model.get(position).getNombre();
        String alerta = model.get(position).getAlerta();

        holder.nombre.setText(nombre);
        holder.alerta.setText(alerta);
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

        TextView nombre,alerta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre_carview);
            alerta = itemView.findViewById(R.id.alerta_carview);


        }
    }
}
