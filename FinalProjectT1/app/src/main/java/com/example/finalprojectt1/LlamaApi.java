package com.example.finalprojectt1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface LlamaApi {

    @GET("JugadoresNFL")
    Call<List<Jugadores>> getJugador(
            @Query("id") String idFilter,  // Filtro para el ID
            @Header("apikey") String apiKey  // Pasar la clave API en el encabezado
    );
}
