package com.example.project;



import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class Jugador extends ArrayList<Jugador> implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id;
    String nombre;

    String posicion;

    String img;
    int dorsal;

    public Jugador(int id, String nombre, String posicion, String img, int dorsal) {
        this.id = id;
        this.nombre = nombre;
        this.posicion = posicion;
        this.img = img;
        this.dorsal = dorsal;
    }

    public Jugador() {

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

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }
}
