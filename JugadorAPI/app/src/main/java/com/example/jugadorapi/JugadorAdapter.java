package com.example.jugadorapi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class JugadorAdapter {
    private Context context;
    private ArrayList<Jugador>listaJugador;

    public JugadorAdapter(Context context,ArrayList<Jugador>listaJugador){
        this.context=context;
        this.listaJugador=listaJugador;
    }
    public int getCount(){return listaJugador.size();}
    public Object getItem(int position) {
        return listaJugador.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listajugador, parent, false);
        }

        Jugador jugador = listaJugador.get(position);

        TextView nameTextView = convertView.findViewById(R.id.);
        TextView idTextView = convertView.findViewById(R.id.);
        ImageView imageView = convertView.findViewById(R.id.);

        nameTextView.setText(jugador.getNombre());
        idTextView.setText("#" + jugador.getId()); // Mostrar el ID
        Glide.with(context)
                .load(jugador.getImg()) // URL de la imagen
                .into(imageView); // ImageView donde se mostrará la imagen

        // Agregar OnClickListener para abrir la actividad de detalles del jugador
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleJugador.class);
            intent.putExtra("pokemonId", jugador.getId()); // Pasar el ID del Pokémon
            context.startActivity(intent);
        });

        return convertView;
    }

}
