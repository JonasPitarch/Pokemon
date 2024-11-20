package com.example.project;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JugadorsViewModel extends AndroidViewModel {
    private final Application app;
    private final AppDatabase appDatabase;
    private final JugadorDAO jugadorDAO;
    private LiveData<List<Jugador>> jugadors;

    public JugadorsViewModel(Application application) {
        super(application);

        this.app = application;
        this.appDatabase = AppDatabase.getDatabase(
                this.getApplication());
        this.jugadorDAO = appDatabase.getJugadorDAO();
    }

    public LiveData<List<Jugador>> getPokemon() {
        return jugadorDAO.getJugador();
    }


    public void reload() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                ArrayList<Jugador> buscajugador = JugadorAPI.buscar();
                jugadorDAO.deleteJugador();
                jugadorDAO.addJugador(buscajugador);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
