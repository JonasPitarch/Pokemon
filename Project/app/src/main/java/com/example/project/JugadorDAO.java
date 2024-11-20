package com.example.project;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface JugadorDAO {


    void addJugador(ArrayList<Jugador> jugador);

    void addJugadors(List<Jugador> jugadors);

    void deletejugadors();

    @Query("SELECT * FROM Jugador")
    LiveData<List<Jugador>> getJugador();


    @Insert
    void addJugadors(Jugador jugador);


    @Insert
    void addJugador(List<Jugador> jugadores);

    @Query("DELETE FROM Jugador")
    void deleteJugador();
}
