package com.example.jugadorapi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class JugadorAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Jugador> listaJugador;

    public JugadorAdapter(Context context, ArrayList<Jugador> listaJugador) {
        this.context = context;
        this.listaJugador = listaJugador;
    }

    @Override
    public int getCount() {
        return listaJugador.size();
    }

    @Override
    public Object getItem(int position) {
        return listaJugador.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listajugador, parent, false);
        }

        Jugador jugador = listaJugador.get(position);

        TextView nameTextView = convertView.findViewById(R.id.nombreJugador);
        TextView idTextView = convertView.findViewById(R.id.idJugador);
        ImageView imageView = convertView.findViewById(R.id.imagenJugador);
        TextView dorsalTextView = convertView.findViewById(R.id.dorsalJugador);

        nameTextView.setText(jugador.getNombre());
        dorsalTextView.setText(String.valueOf(jugador.getDorsal()));
        idTextView.setText("#" + jugador.getId());
        Glide.with(context)
                .load(jugador.getImg()) // URL de la imagen
                .into(imageView);

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleJugador.class);
            intent.putExtra("jugadorId", jugador.getId());
            context.startActivity(intent);
        });

        return convertView;
    }
}

