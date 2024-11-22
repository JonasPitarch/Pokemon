package com.example.finalprojectt1;

import android.util.Log;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MetodosJugador {
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5iamlsb3JvYWZqZXJsdXpkYnR3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE1ODMwMjYsImV4cCI6MjA0NzE1OTAyNn0.8ew_Bf1mRT0K7dcABd3smpJtOQNCTjW9Mluf1YPBm2c";
    public void getJugador(int id, Consumer<Jugadores>callback){
        LlamaApi api=new LlamaApi();
        String idFilter="eq."+id;

        Call<List<Jugadores>>llamada=api.jugadorApi.getJugador(idFilter,API_KEY);
        llamada.enqueue(new Callback<List<Jugadores>>() {
            @Override
            public void onResponse(Call<List<Jugadores>> call, Response<List<Jugadores>> response) {
                if (response.isSuccessful()&&response.body() !=null && response.body().isEmpty()){
                    Jugadores jugadores=response.body().get(0);
                    callback.accept(jugadores);
                }
                else {
                    Log.d("error","ESta id no se encuentra");
                    callback.accept(null);
                }
            }

            @Override
            public void onFailure(Call<List<Jugadores>> call, Throwable t) {
                Log.d("XXXXX", "NO FURULA");
                callback.accept(null);
            }
        });
    }
}
