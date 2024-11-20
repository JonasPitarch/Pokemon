package com.example.project;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class JugadorAPI {
    private static final String API_URL = "";

    public static ArrayList<Jugador> buscar() {
        ArrayList<Jugador> jugadores = new ArrayList<>();

        try {
            // Llamada a la API principal
            String jsonResponse = doCall(API_URL);
            if (jsonResponse != null) {
                JSONArray resultado = new JSONArray(jsonResponse);

                for (int i = 0; i < resultado.length(); i++) {
                    JSONObject jugadorJson = resultado.getJSONObject(i);

                    // Obtener información del jugador
                    String name = jugadorJson.optString("name");
                    String imageUrl = jugadorJson.optString("img");
                    int id = jugadorJson.optInt("id");
                    int dorsal=jugadorJson.optInt("dorsal");

                    // Verificar y añadir a la lista
                    if (name != null && imageUrl != null && id != -1) {
                        Jugador jugador = new Jugador();
                        jugador.setNombre(name);
                        jugador.setImg(imageUrl);
                        jugador.setId(id);
                        jugador.setDorsal(dorsal);

                        jugadores.add(jugador);
                    } else {
                        System.out.println("Datos incompletos para el jugador: " + name);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jugadores;
    }

    private static String doCall(String urlStr) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                System.out.println("Error en la conexión: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
