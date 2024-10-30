package com.example.pokedexjonas;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DetallePokemon extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detallespokemons);

        ImageView imageView = findViewById(R.id.ImagenPokemon);
        TextView nameTextView = findViewById(R.id.Pokename1);
        TextView descriptionTextView = findViewById(R.id.pokespecie2);

        int pokemonId = getIntent().getIntExtra("pokemonId", -1);

        if (pokemonId != -1) {
            Pokemon pokemon = getPokemonDetailsById(pokemonId);
            if (pokemon != null) {
                nameTextView.setText(pokemon.getName());
                descriptionTextView.setText(pokemon.getSpecies());
                Glide.with(this)
                        .load(pokemon.getSprite())
                        .into(imageView);
            } else {
                Log.d("Detalles en Detalles pokemon", "No se encontraron Pokémon.");
            }
        } else {
            Log.d("Detalles2 en Detalles pokemon", "Error muy gordo aqui");
        }
    }

    private Pokemon getPokemonDetailsById(int id) {
        ArrayList<Pokemon> pokemons = PokeApi.buscar();

        for (Pokemon pokemon : pokemons) {
            if (pokemon.getId() == id) {
                return pokemon;
            }
        }
        return null;
    }
}

