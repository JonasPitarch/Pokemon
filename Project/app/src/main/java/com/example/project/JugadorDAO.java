package com.example.project;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JugadorDAO {
    @Query("select * from Jugador")
    LiveData<List<Jugador>> getJugador();

    @Insert
    void addJugador(Jugador jugador);

    @Insert
    void addJugadors(List<Jugador> jugadors);

    @Delete
    void deletePJugador();

    @Query("DELETE FROM jugador")
    void deletejugadors();
}