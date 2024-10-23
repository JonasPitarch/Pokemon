package com.example.pokedexjonas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pokedexjonas.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    ArrayList<String> pokemon;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        pokemon = new ArrayList<>();
        pokemon.add("andres");
        pokemon.add("Iniesta");
        pokemon.add("marcos");

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> pokemonNames = new ArrayList<>(); // Esta es la lista que usará el adaptador
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.pokemonslista,
                R.id.Pokename,
                pokemonNames
        );
        binding.listaPokemon.setAdapter(adapter);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                ArrayList<Pokemon> pokemons = PokeApi.buscar();
                if (pokemons != null && !pokemons.isEmpty()) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            for (Pokemon p : pokemons) {
                                pokemonNames.add(p.getName());
                                Log.d("XXXXX", "Pokemon añadido: " + p.getName());
                            }
                            adapter.notifyDataSetChanged(); // Actualizar el adaptador
                        });
                    }
                } else {
                    Log.e("PokeApiError", "No se obtuvieron Pokémon de la API");
                }
            } catch (Exception e) {
                Log.e("PokeApiError", "Error al obtener Pokémon: ", e);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}