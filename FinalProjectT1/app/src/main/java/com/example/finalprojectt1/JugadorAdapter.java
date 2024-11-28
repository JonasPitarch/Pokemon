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
    private final Context context;
    private final ArrayList<Jugadores> jugadores;

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
        // Verifica si la convertView es nula
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listadojugador, parent, false);
        }

        // Obtiene el jugador correspondiente a la posición actual de la lista
        Jugadores jugador = jugadores.get(position);

        // Encuentra las vistas dentro del diseño inflado que se usarán para mostrar la información del jugador
        TextView nombreTextView = convertView.findViewById(R.id.nombrep); // TextView para el nombre del jugador
        TextView dorsalTextView = convertView.findViewById(R.id.dorp);    // TextView para el dorsal del jugador
        TextView posicionTextView = convertView.findViewById(R.id.posp);  // TextView para la posición del jugador
        ImageView imageView = convertView.findViewById(R.id.iamgenp);     // ImageView para mostrar la imagen del jugador

        nombreTextView.setText(jugador.getNombre());                      // Establece el nombre del jugador
        dorsalTextView.setText(String.valueOf(jugador.getDorsal()));      // Establece el dorsal del jugador convertido a texto
        posicionTextView.setText(jugador.getPosicion());                  // Establece la posición del jugador
        Glide.with(context)
                .load(jugador.getImg())                                      // Carga la URL de la imagen
                .into(imageView);                                            // Establece la imagen en el ImageView
        // Devuelve la vista completamente configurada para ser mostrada en la lista.
        return convertView;
    }

}
