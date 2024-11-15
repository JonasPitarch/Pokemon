package com.example.project;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JugadorViewModel extends AndroidViewModel {
    private final Application app;
    private final AppDatabase appDatabase;
    private final JugadorDAO jugadorDAO;
    private LiveData<List<Jugador>> jugadors;

    public JugadorViewModel(Application application) {
        super(application);

        this.app = application;
        this.appDatabase = AppDatabase.getDatabase(
                this.getApplication());
        this.jugadorDAO = appDatabase.getPokemonDao();
    }

    public LiveData<List<Jugador>> getPokemon() {
        return jugadorDAO.getJugador();
    }


    public void reload() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Jugador> buscajugador = JugadorAPI.buscar();
            jugadorDAO.deletejugadors();


        });
    }
}
