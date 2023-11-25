package com.example.lab9.Beans;

import java.sql.Date;

public class Facultad {
    private int idFacutlad;
    private String nombreFacu;
    private int idUniversidad;

    private String fecha_registro;
    private String fecha_edicion;

    //GETTERS AND SETTERS

    public int getIdFacutlad() {
        return idFacutlad;
    }

    public void setIdFacutlad(int idFacutlad) {
        this.idFacutlad = idFacutlad;
    }

    public String getNombreFacu() {
        return nombreFacu;
    }

    public void setNombreFacu(String nombreFacu) {
        this.nombreFacu = nombreFacu;
    }

    public int getIdUniversidad() {
        return idUniversidad;
    }

    public void setIdUniversidad(int idUniversidad) {
        this.idUniversidad = idUniversidad;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getFecha_edicion() {
        return fecha_edicion;
    }

    public void setFecha_edicion(String fecha_edicion) {
        this.fecha_edicion = fecha_edicion;
    }
}
