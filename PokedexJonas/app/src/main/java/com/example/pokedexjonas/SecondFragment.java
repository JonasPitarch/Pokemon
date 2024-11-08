package com.example.pokedexjonas;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.pokedexjonas.Pokemon;
import com.example.pokedexjonas.R;
import com.example.pokedexjonas.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private ImageView imageView;
    private TextView nameTextView;
    private TextView descriptionTextView;

    private TextView idPkemonTextView;
    private FragmentSecondBinding binding;

    @Override
   public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState){

        binding = FragmentSecondBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();

        if(args != null) {
            Pokemon pokemon = (Pokemon) args.getSerializable("item");

            if (pokemon != null) {
                updateUi(pokemon);
            }
        }
    }

    private void updateUi(Pokemon pokemon) {
        Log.d("MOVIE", pokemon.toString());

        binding.pokename.setText(pokemon.getName());
        binding.pokeid.setText("#"+pokemon.getId());
        Glide.with(getContext()).load(pokemon.getSprite()).into(binding.pokeimage);

    }
}

