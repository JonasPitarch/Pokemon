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
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5iamlsb3JvYWZqZXJsdXpkYnR3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE1ODMwMjYsImV4cCI6MjA0NzE1OTAy"; // Asegúrate de usar tu clave correcta

    private Retrofit retrofit;

    // Constructor: Configuración de Retrofit
    public MetodosJugador() {
        // Configuración de Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)  // URL base de la API
                .addConverterFactory(GsonConverterFactory.create())  // Conversión de JSON a objetos
                .build();
    }

    // Método para obtener un jugador
    public void getJugador(int id, Consumer<Jugadores> callback) {
        // Crear instancia de la API
        LlamaApi api = retrofit.create(LlamaApi.class);

        // Construir el filtro correctamente para la consulta de ID
        String idFilter = "id=eq." + id;  // Filtro en formato adecuado para Supabase

        // Realizar la llamada a la API pasando la clave API en los encabezados
        Call<List<Jugadores>> llamada = api.getJugador(idFilter, API_KEY);

        // Enviar la solicitud de forma asincrónica
        llamada.enqueue(new Callback<List<Jugadores>>() {
            @Override
            public void onResponse(Call<List<Jugadores>> call, Response<List<Jugadores>> response) {
                if (response.isSuccessful()) {
                    // Verificar si la respuesta tiene cuerpo y datos
                    if (response.body() != null && !response.body().isEmpty()) {
                        Jugadores jugador = response.body().get(0);  // Obtener el primer jugador
                        callback.accept(jugador);  // Pasar el jugador al callback
                    } else {
                        Log.e("API_ERROR", "No se encontraron jugadores para el ID: " + id);
                        callback.accept(null);  // No se encontró el jugador
                    }
                } else {
                    // Manejar error si la respuesta no es exitosa
                    Log.e("API_ERROR", "Error en la respuesta de la API: " + response.code() + " " + response.message());
                    callback.accept(null);  // Pasar null si hay error
                }
            }

            @Override
            public void onFailure(Call<List<Jugadores>> call, Throwable t) {
                // Manejar fallo de la solicitud
                Log.e("API_ERROR", "Error al llamar la API: " + t.getMessage());
                t.printStackTrace();  // Imprimir el stack trace
                callback.accept(null);  // Pasar null si ocurre un error
            }
        });
    }
}
