package com.masanz.ut2.ejercicio5.dto;

public class Objeto {
    private int id;
    private String nombre;
    private double valor;
    private int userId;

    public Objeto(int id, String nombre, double valor, int userId) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Objeto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", valor=" + valor +
                ", userId=" + userId +
                '}';
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
