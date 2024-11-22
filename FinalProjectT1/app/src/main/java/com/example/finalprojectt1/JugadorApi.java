package com.example.finalprojectt1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JugadorApi {
    @GET("JugadoresNFL")
    Call<List<Jugadores>>getJugador(@Query("id")String id, @Query("apikey") String apikey);
}
