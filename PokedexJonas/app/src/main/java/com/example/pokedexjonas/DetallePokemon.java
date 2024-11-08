package com.example.pokedexjonas;

import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetallePokemon extends AppCompatActivity {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detallespokemons);

        ImageView imageView = findViewById(R.id.ImagenPokemon);
        TextView nameTextView = findViewById(R.id.Pokename1);
        TextView descriptionTextView = findViewById(R.id.pokespecie2);
        double pokemonId = getIntent().getDoubleExtra("pokemonId", -1.0);

        if (pokemonId != -1.0) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Pokemon pokemon = getPokemonDetailsById(pokemonId);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (pokemon != null) {
                                // Si se encuentra el Pokémon, mostrar los detalles
                                nameTextView.setText(pokemon.getName());
                                descriptionTextView.setText(pokemon.getSpecies());
                                Glide.with(DetallePokemon.this)
                                        .load(pokemon.getSprite())
                                        .into(imageView);
                            } else {
                                // Si no se encuentra, mostrar un mensaje
                                Log.d("DetallePokemon", "No se encontraron detalles para el Pokémon con ID: " + pokemonId);
                                Toast.makeText(DetallePokemon.this, "No se encontraron detalles para este Pokémon", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } else {
            Log.d("DetallePokemon", "ID de Pokémon inválido.");
            Toast.makeText(this, "ID de Pokémon inválido", Toast.LENGTH_SHORT).show();
        }
    }

    private Pokemon getPokemonDetailsById(double id) {
        ArrayList<Pokemon> pokemons = PokeApi.buscar();

        if (pokemons != null) {
            for (Pokemon pokemon : pokemons) {
                if (pokemon.getId() == id) {
                    return pokemon;
                }
            }
        } else {
            Log.d("DetallePokemon", "La API no devolvió ninguna lista de Pokémon.");
        }
        return null;
    }
}
