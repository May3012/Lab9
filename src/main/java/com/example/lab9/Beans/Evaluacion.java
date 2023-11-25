package com.example.lab9.Beans;

import java.sql.Date;

public class Evaluacion {
    private int idEvalluaciones;
    private String nombre_estudiantes;
    private String codigo_estudiantes;
    private String correo_estudiantes;
    private int nota;
    private Curso curso;
    private Semestre semestre;
    private String fecha_registro;
    private String fecha_edicion;

    //GETTERS AND SETTERS
    public String getCodigo_estudiantes() {
        return codigo_estudiantes;
    }

    public void setCodigo_estudiantes(String codigo_estudiantes) {
        this.codigo_estudiantes = codigo_estudiantes;
    }

    public int getIdEvalluaciones() {
        return idEvalluaciones;
    }

    public void setIdEvalluaciones(int idEvalluaciones) {
        this.idEvalluaciones = idEvalluaciones;
    }

    public String getNombre_estudiantes() {
        return nombre_estudiantes;
    }

    public void setNombre_estudiantes(String nombre_estudiantes) {
        this.nombre_estudiantes = nombre_estudiantes;
    }

    public String getCorreo_estudiantes() {
        return correo_estudiantes;
    }

    public void setCorreo_estudiantes(String correo_estudiantes) {
        this.correo_estudiantes = correo_estudiantes;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
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
