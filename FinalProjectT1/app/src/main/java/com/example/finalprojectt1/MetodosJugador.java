package com.example.finalprojectt1;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;
import java.util.function.Consumer;

public class MetodosJugador {
    private static final String BASE_URL = "https://nbjiloroafjerluzdbtw.supabase.co/rest/v1/";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5iamlsb3JvYWZqZXJsdXpkYnR3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE1ODMwMjYsImV4cCI6MjA0NzE1OTAyNn0.8ew_Bf1mRT0K7dcABd3smpJtOQNCTjW9Mluf1YPBm2c";

    private Retrofit retrofit;

    public MetodosJugador() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getJugador(int id, Consumer<Jugadores> callback) {
        // Crea una instancia de la interfaz LlamaApi a través de Retrofit.
        LlamaApi api = retrofit.create(LlamaApi.class);

        // Construimos el filtro de búsqueda para obtener el jugador con el ID especificado.
        String filtro = "eq." + id; // 'eq.' se usa para crear un filtro de igualdad en la consulta.

        // Crea una llamada a la API usando el método definido en LlamaApi, pasando el filtro de ID y la clave de la API.
        Call<List<Jugadores>> llamada = api.getJugador(filtro, API_KEY);

        // Encola la llamada de manera asíncrona para no bloquear el hilo principal.
        llamada.enqueue(new Callback<List<Jugadores>>() {

            @Override
            public void onResponse(Call<List<Jugadores>> call, Response<List<Jugadores>> response) {
                // Log para depurar la respuesta HTTP.
                Log.d("API_RESPONSE", "Código de respuesta: " + response.code());
                Log.d("API_RESPONSE", "Cuerpo de respuesta: " + response.body());

                // Verifica si la respuesta es exitosa, tiene cuerpo y no está vacía.
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    // Obtiene el primer jugador de la lista de resultados.
                    Jugadores jugador = response.body().get(0);
                    // Llama al callback pasando el jugador obtenido.
                    callback.accept(jugador);
                } else {
                    // Si no se encontraron datos, registra un error y pasa null al callback.
                    Log.e("API_ERROR", "No se encontraron jugadores para el ID: " + id);
                    callback.accept(null);
                }
            }

            @Override
            public void onFailure(Call<List<Jugadores>> call, Throwable t) {
                // Si ocurre un error en la llamada a la API, se registra en el log.
                Log.e("API_ERROR", "Error al llamar a la API: " + t.getMessage());
                t.printStackTrace();
                // Llama al callback con null para indicar el fallo.
                callback.accept(null);
            }
        });
    }

}