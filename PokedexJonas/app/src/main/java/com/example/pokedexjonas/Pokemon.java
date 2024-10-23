package com.example.pokedexjonas;

public class Pokemon {
    private double id;
    private String name;
    private String species;
    private String weight;
    private String sprite;
    public Pokemon(){

    }
    public Pokemon(double id, String name, String species, String weight, String sprite) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.weight = weight;
        this.sprite = sprite;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", weight='" + weight + '\'' +
                ", sprite='" + sprite + '\'' +
                '}';
    }


}
