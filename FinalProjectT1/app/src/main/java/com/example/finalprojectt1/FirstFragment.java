package com.example.finalprojectt1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.finalprojectt1.databinding.FragmentFirstBinding;

import java.io.Serializable;
import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ArrayList<Jugadores> jugadores;
    private JugadorAdapter adapter;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jugadores = new ArrayList<>();
        adapter=new JugadorAdapter(getContext(),jugadores);
        binding.listaJugadores.setAdapter(adapter);

        binding.listaJugadores.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Jugadores jugadorseleccionado = (Jugadores) parent.getItemAtPosition(position);
                Bundle args = new Bundle();
                args.putSerializable("jugadorseleccionado", (Serializable) jugadorseleccionado);

                // Navega al SecondFragment con los datos
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment, args);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}