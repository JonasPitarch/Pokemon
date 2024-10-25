package com.example.pokedexjonas;

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

public class PokemonViewModel extends AndroidViewModel {
    private final Application app;
    private final AppDatabase appDatabase;
    private final PokemonDao pokemonDao;
    private LiveData<List<Pokemon>> movies;

    public PokemonViewModel(Application application) {
        super(application);

        this.app = application;
        this.appDatabase = AppDatabase.getDatabase(
                this.getApplication());
        this.pokemonDao = appDatabase.getPokemonDao();
    }

    public LiveData<List<Pokemon>> getMovies() {
        return pokemonDao.getPokemons();
    }


    public void reload() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Pokemon> bucapokemon = PokeApi.buscar();
            pokemonDao.deletePokemons();
            pokemonDao.addPokemons(bucapokemon);

        });
    }

}
