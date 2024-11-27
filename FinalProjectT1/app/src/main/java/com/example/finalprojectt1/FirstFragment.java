package com.example.finalprojectt1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.finalprojectt1.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jugadores = new ArrayList<>();
        adapter = new JugadorAdapter(getContext(), jugadores);
        binding.listaJugadores.setAdapter(adapter);

        binding.listaJugadores.setOnItemClickListener((parent, view1, position, id) -> {
            Jugadores jugadorSeleccionado = (Jugadores) parent.getItemAtPosition(position);
            Bundle args = new Bundle();
            args.putSerializable("jugadorseleccionado", jugadorSeleccionado);

            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment, args);
        });

        cargarJugadores(1, 20);
    }

    private void cargarJugadores(int id, int maxId) {
        if (id > maxId) {
            Toast.makeText(getContext(), "Generación completada", Toast.LENGTH_SHORT).show();
        } else {
            MetodosJugador metodosJugador = new MetodosJugador();
            ExecutorService executor = Executors.newSingleThreadExecutor();

            executor.execute(() -> {
                metodosJugador.getJugador(id, jugador -> {
                    if (jugador != null) {
                        getActivity().runOnUiThread(() -> {
                            jugadores.add(jugador);
                            adapter.notifyDataSetChanged();
                        });
                    } else {
                        getActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), "No se encontraron datos para el ID: " + id, Toast.LENGTH_SHORT).show()
                        );
                    }

                    cargarJugadores(id + 1, maxId);
                });
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
