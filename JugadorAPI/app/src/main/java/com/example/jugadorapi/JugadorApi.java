package com.example.jugadorapi;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class JugadorApi {
    private static String API_URL = "https://nbjiloroafjerluzdbtw.supabase.co/rest/v1/JugadoresNFL?apikey=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5iamlsb3JvYWZqZXJsdXpkYnR3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE1ODMwMjYsImV4cCI6MjA0NzE1OTAyNn0.8ew_Bf1mRT0K7dcABd3smpJtOQNCTjW9Mluf1YPBm2c"; // URL base de la API

    public static ArrayList<Jugador> buscar() {
        ArrayList<Jugador> jugadorList = new ArrayList<>();
        Uri builtUri = Uri.parse(API_URL)
                .buildUpon()
                .appendPath("jugadores")
                .appendQueryParameter("limit", "20")
                .build();

        String url = builtUri.toString();

        // Llamada a la API
        String jsonResponse = doCall(url);
        if (jsonResponse != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray resultado = jsonObject.getJSONArray("results");

                for (int i = 0; i < resultado.length(); i++) {
                    JSONObject jugadorJson = resultado.getJSONObject(i);

                    // Obtener el nombre del jugador
                    String nombre = jugadorJson.getString("nombre");

                    // Obtener la URL de detalles del jugador
                    String URL_Detalle = jugadorJson.getString("url");

                    // Llamada a la API para obtener detalles del jugador
                    String detalles = doCall(URL_Detalle);
                    if (detalles != null) {
                        JSONObject detailsJson = new JSONObject(detalles);

                        // Obtener el ID del jugador
                        int id = detailsJson.getInt("id");

                        // Verificar si el campo de las imágenes existe y tiene una imagen
                        if (detailsJson.has("imagenes")) {
                            JSONObject imagenesJson = detailsJson.getJSONObject("imagenes");
                            if (imagenesJson.has("perfil")) {
                                String imageUrl = imagenesJson.getString("perfil");
                                // Crear y añadir el jugador a la lista
                                Jugador jugador = new Jugador();
                                jugador.setId(id);
                                jugador.setNombre(nombre);
                                jugador.setImg(imageUrl);

                                // Verificar y agregar la posición y dorsal
                                if (detailsJson.has("posicion")) {
                                    jugador.setPosicion(detailsJson.getString("posicion"));
                                }
                                if (detailsJson.has("dorsal")) {
                                    jugador.setDorsal(detailsJson.getInt("dorsal"));
                                }

                                jugadorList.add(jugador);
                            } else {
                                System.out.println("No se encontró la imagen para el jugador: " + nombre);
                            }
                        } else {
                            System.out.println("No se encontró el campo 'imagenes' para el jugador: " + nombre);
                        }
                    }
                }
            } catch (Exception e) {
               Log.d("Error llamada","Da error la llamada de la API");
                e.printStackTrace();
            }
        } else {
           Log.d("Respuesta", "La API no devuelve nada");
        }

        return jugadorList; // Devolver la lista de jugadores
    }

    private static String doCall(String url) {
        try {
            return HttpUtils.get(url);
        } catch (IOException e) {
            Log.d("XXXX","Error al cargar la API");
            e.printStackTrace();
        }
        return null; // Retorna nulo en caso de error
    }
}
