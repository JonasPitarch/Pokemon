package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class JugadoresAdapter {
    private final Context context;

    private final ArrayList<Jugador> jugadorslist;

    public JugadoresAdapter(Context context, ArrayList<Jugador> jugadorslist) {
        this.context = context;
        this.jugadorslist = jugadorslist;
    }
    public int getCount() {
        return jugadorslist.size();
    }

    public Object getItem(int position) {
        return jugadorslist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.jugadorlista, parent, false);
        }

        Jugador jugador = jugadorslist.get(position);

        TextView nameTextView = convertView.findViewById(R.id.Jugadornombre);
        TextView idTextView = convertView.findViewById(R.id.Jugadorid); // TextView para el ID
        ImageView imageView = convertView.findViewById(R.id.jugadorview);
        TextView dorsalTextView=convertView.findViewById(R.id.jugadordorsal);


        nameTextView.setText(jugador.getNombre());
        idTextView.setText("#" + jugador.getId()); // Mostrar el ID
        Glide.with(context)
                .load(jugador.getImg()) // URL de la imagen
                .into(imageView); // ImageView donde se mostrará la imagen
        dorsalTextView.setText(jugador.getDorsal());

        // Agregar OnClickListener para abrir la actividad de detalles del Jugador
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetallesJugador.class);
            intent.putExtra("pokemonId", jugador.getId()); // Pasar el ID del Jugador
            context.startActivity(intent);
        });

        return convertView;
    }


}
