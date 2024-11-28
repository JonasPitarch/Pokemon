package com.example.finalprojectt1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import com.example.finalprojectt1.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ArrayList<Jugadores> jugadores; // Lista que maneja el adaptador
    private JugadorAdapter adapter;
    private AppDatabase db; // Instancia de la base de datos

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

        // Inicializa la base de datos
        db = AppDatabase.getDatabase(getContext());

        // Configura la lista de jugadores y el adaptador
        jugadores = new ArrayList<>();
        adapter = new JugadorAdapter(getContext(), jugadores);
        binding.listaJugadores.setAdapter(adapter);

        // Maneja el clic en un jugador para navegar al SecondFragment
        binding.listaJugadores.setOnItemClickListener((parent, view1, position, id) -> {
            Jugadores jugadorSeleccionado = (Jugadores) parent.getItemAtPosition(position);
            Bundle args = new Bundle();
            args.putSerializable("jugadorseleccionado", jugadorSeleccionado);

            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment, args);
        });

        // Carga los jugadores desde la base de datos local
        cargarJugadoresDesdeDB();

        // Llama a la API si la base de datos está vacía
        verificaryactualizardesdedb();
    }


//      Carga jugadores desde la base de datos local.

    private void cargarJugadoresDesdeDB() {
        db.getJugadorDAO().getJugador().observe(getViewLifecycleOwner(), new Observer<List<Jugadores>>() {
            @Override
            public void onChanged(List<Jugadores> jugadoresDB) {
                jugadores.clear(); // Limpia la lista actual
                jugadores.addAll(jugadoresDB); // Agrega los jugadores de la base de datos
                adapter.notifyDataSetChanged(); // Actualiza el adaptador
            }
        });
    }


//     Verifica si la base de datos está vacía y, de ser así, descarga jugadores desde la API.

    private void verificaryactualizardesdedb() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            int count = db.getJugadorDAO().getCount(); // Obtiene el número de jugadores en la base de datos
            if (count == 0) {
                // Si la base de datos está vacía, carga los jugadores desde la API
                cargarJugadoresDesdeAPI(1, 24);
            }
        });
    }

    private void cargarJugadoresDesdeAPI(int id, int maxId) {
        if (id > maxId) {
            getActivity().runOnUiThread(() ->
                    Toast.makeText(getContext(), "Generación completada", Toast.LENGTH_SHORT).show()
            );
        } else {
            MetodosJugador metodosJugador = new MetodosJugador();
            ExecutorService executor = Executors.newSingleThreadExecutor(); // Crea un hilo secundario para la operación

            executor.execute(() -> {
                metodosJugador.getJugador(id, jugador -> {
                    if (jugador != null) {
                        // Guarda el jugador en la base de datos en un hilo de fondo
                        executor.execute(() -> db.getJugadorDAO().addJugador(jugador));
                    }
                    cargarJugadoresDesdeAPI(id + 1, maxId); // Llamada recursiva
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
