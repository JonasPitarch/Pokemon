package com.example.finalprojectt1;

public class Jugadores {
    int id;
    String nombre;
    String img;
    String posicion;
    int dorsal;

    public Jugadores(int id, String nombre, String img, String posicion, int dorsal) {
        this.id = id;
        this.nombre = nombre;
        this.img = img;
        this.posicion = posicion;
        this.dorsal = dorsal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }
}
