package com.example.alertaumg.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alertaumg.Entidades.ConctactosU;
import com.example.alertaumg.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorContacto extends RecyclerView.Adapter<AdaptadorContacto.ViewHolder> implements View.OnClickListener {


   private List<ConctactosU> nData;
   private LayoutInflater nInflater;
   private Context context;
    private View.OnClickListener listener;

   public AdaptadorContacto(List<ConctactosU> itemList, Context context){

       this.nInflater = LayoutInflater .from(context);
       this.context = context;
       this.nData = itemList;

   }


    @Override
    public int getItemCount() {
        return nData.size();
    }

    @Override
    public AdaptadorContacto.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = nInflater.inflate(R.layout.cardview_contactos,null);
        view.setOnClickListener(this);
        return new AdaptadorContacto.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final AdaptadorContacto.ViewHolder holder,final int position) {

       holder.bindData(nData.get(position));

    }

    public void setItems(List<ConctactosU> items){

       nData = items;

    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;

    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

       TextView nombre,telefono;

       ViewHolder(View itemView){
           super(itemView);

           nombre = itemView.findViewById(R.id.nombre_carview);
           telefono = itemView.findViewById(R.id.contacto_carview);
       }

       void bindData(final  ConctactosU item){

           nombre.setText(item.getNombre());
           telefono.setText(item.getTelefono());
       }
    }


}
