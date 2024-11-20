package com.example.jugadorapi;

import android.util.Log;

import java.util.ArrayList;

public class DetalleJugador {






    private Jugador getJugadorporID(double id) {
        ArrayList<Jugador> jugadors = JugadorApi.buscar();

        if (jugadors != null) {
            for (Jugador jugador : jugadors) {
                if (jugador.getId() == id) {
                    return jugador;
                }
            }
        } else {
            Log.d("DetalleJugador", "La API no devolvió ninguna lista de Jugadores");
        }
        return null;
    }
}

