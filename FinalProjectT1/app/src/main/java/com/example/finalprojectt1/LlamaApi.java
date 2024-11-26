package com.example.finalprojectt1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

import java.util.List;

public interface LlamaApi {

    // Método para obtener un jugador, pasando la clave API en los encabezados
    @GET("JugadoresNFL")
    Call<List<Jugadores>> getJugador(
            @Query("id") String idFilter,  // Filtro para el ID
            @Header("apikey") String apiKey  // Pasar la clave API aquí
    );
}
