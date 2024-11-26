package com.example.finalprojectt1;

import android.content.Context;
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
    private ArrayList<Jugadores> jugadores;

    public JugadorAdapter(Context context, ArrayList<Jugadores> jugadores) {
        this.context = context;
        this.jugadores = jugadores;
    }

    @Override
    public int getCount() {
        return jugadores.size();
    }

    @Override
    public Object getItem(int position) {
        return jugadores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listadojugador, parent, false);
        }

        Jugadores jugador = jugadores.get(position);

        TextView nombreTextView = convertView.findViewById(R.id.nombrep);
        TextView dorsalTextView = convertView.findViewById(R.id.dorp);
        TextView posicionTextView = convertView.findViewById(R.id.posp);
        ImageView imageView = convertView.findViewById(R.id.iamgenp);

        nombreTextView.setText(jugador.getNombre());
        dorsalTextView.setText(String.valueOf(jugador.getDorsal()));
        posicionTextView.setText(jugador.getPosicion());

        // Cargar la imagen con Glide
        Glide.with(context)
                .load(jugador.getImg())
                .into(imageView);

        return convertView;
    }
}
