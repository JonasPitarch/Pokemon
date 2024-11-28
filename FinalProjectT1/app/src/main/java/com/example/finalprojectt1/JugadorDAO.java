package com.example.finalprojectt1;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JugadorDAO {
    @Query("SELECT * FROM jugadores")
    LiveData<List<Jugadores>> getJugador();

    @Insert
    void addJugador(Jugadores jugadores);

    @Insert
    void addJugador(List<Jugadores> jugadores);

    @Delete
    void deleteJugador(Jugadores jugadores);

    @Query("DELETE FROM jugadores")
    void deleteJugador();

    @Query("SELECT COUNT(*) FROM jugadores")
    int getCount(); // Método para contar registros
}
