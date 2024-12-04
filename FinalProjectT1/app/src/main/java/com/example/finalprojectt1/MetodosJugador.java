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
        LlamaApi api = retrofit.create(LlamaApi.class);
        String filtro = "eq." + id;
        Call<List<Jugadores>> llamada = api.getJugador(filtro, API_KEY);
        llamada.enqueue(new Callback<List<Jugadores>>() {
            @Override
            public void onResponse(Call<List<Jugadores>> call, Response<List<Jugadores>> response) {
                Log.d("API_RESPONSE", "Código de respuesta: " + response.code());
                Log.d("API_RESPONSE", "Cuerpo de respuesta: " + response.body());
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Jugadores jugador = response.body().get(0);
                    callback.accept(jugador);
                } else {
                    Log.e("API_ERROR", "No se encontraron jugadores para el ID: " + id);
                    callback.accept(null);
                }
            }
            @Override
            public void onFailure(Call<List<Jugadores>> call, Throwable t) {
                Log.e("API_ERROR", "Error al llamar a la API: " + t.getMessage());
                t.printStackTrace();
                callback.accept(null);
            }
        });
    }

}