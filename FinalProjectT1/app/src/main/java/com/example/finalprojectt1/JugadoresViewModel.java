package com.example.finalprojectt1;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JugadoresViewModel {
    private final Application app;
    private final AppDatabase appDatabase;
    private final JugadorDAO jugadorDAO;
    private LiveData<List<Jugadores>> jugadores;

    public JugadoresViewModel(Application app, AppDatabase appDatabase, JugadorDAO jugadorDAO) {
        this.app = app;
        this.appDatabase = appDatabase;
        this.jugadorDAO = jugadorDAO;
    }

    public LiveData<List<Jugadores>>getJugadores(){return jugadorDAO.getJugador();}




}


