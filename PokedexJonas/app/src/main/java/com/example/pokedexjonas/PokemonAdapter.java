package com.example.pokedexjonas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pokedexjonas.Pokemon;

import java.util.ArrayList;

public class PokemonAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Pokemon> pokemonList;

    public PokemonAdapter(Context context, ArrayList<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
    }

    @Override
    public int getCount() {
        return pokemonList.size();
    }

    @Override
    public Object getItem(int position) {
        return pokemonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pokemonslista, parent, false);
        }

        Pokemon pokemon = pokemonList.get(position);

        TextView nameTextView = convertView.findViewById(R.id.Pokename);
        TextView idTextView = convertView.findViewById(R.id.PokeId); // TextView para el ID
        ImageView imageView = convertView.findViewById(R.id.pokeimagen);

        nameTextView.setText(pokemon.getName());
        idTextView.setText("#" + pokemon.getId()); // Mostrar el ID
        Glide.with(context)
                .load(pokemon.getSprite()) // URL de la imagen
                .into(imageView); // ImageView donde se mostrará la imagen

        return convertView;
    }
}


