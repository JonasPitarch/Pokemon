package com.example.pokedexjonas;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class PokeApi {
    private static final String apiurl = "https://pokeapi.co/api/v2";

    public static ArrayList<Pokemon> buscar() {
        ArrayList<Pokemon> pokemonList = new ArrayList<>();
        Uri builtUri = Uri.parse(apiurl)
                .buildUpon()
                .appendPath("pokemon")
                .appendQueryParameter("limit", "151")
                .build();

        String url = builtUri.toString();

        // Llamada a la API
        String jsonResponse = doCall(url);
        if (jsonResponse != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray resultado = jsonObject.getJSONArray("results");

                for (int i = 0; i < resultado.length(); i++) {
                    JSONObject pokemonJson = resultado.getJSONObject(i);

                    // Obtener el nombre del Pokémon
                    String name = pokemonJson.getString("name");

                    // Obtener la URL de detalles del Pokémon
                    String det = pokemonJson.getString("url");

                    // Hacer otra llamada a la API para obtener más detalles del Pokémon
                    String detailsResponse = doCall(det);
                    if (detailsResponse != null) {
                        JSONObject detailsJson = new JSONObject(detailsResponse);

                        // Obtener el ID del Pokémon desde detailsJson
                        double id = detailsJson.getDouble("id");

                        // Verificar si el campo de las imágenes existe y tiene la imagen
                        if (detailsJson.has("sprites")) {
                            JSONObject spritesJson = detailsJson.getJSONObject("sprites");
                            if (spritesJson.has("front_default")) {
                                String imageUrl = spritesJson.getString("front_default");

                                Pokemon pokemon = new Pokemon();
                                pokemon.setName(name);
                                pokemon.setSprite(imageUrl); // Establecer la URL de la imagen
                                pokemon.setId(id); // Colocamos el id a la pokeapi
                                // Añadir Pokémon a la lista
                                pokemonList.add(pokemon);
                            } else {
                                System.out.println("No se encontró la imagen para el Pokémon: " + name);
                            }
                        } else {
                            System.out.println("No se encontró el campo 'sprites' para el Pokémon: " + name);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error al procesar los detalles del Pokémon: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("Error: No se recibió respuesta de la API.");
        }

        return pokemonList;  // Devolver la lista de Pokémon
    }

    private static String doCall(String url) {
        try {
            return HttpUtils.get(url);
        } catch (IOException e) {
            System.err.println("Error al realizar la llamada a la API: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // retorna nulo en caso de error
    }
}




