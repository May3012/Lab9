package com.example.lab9.Beans;

public class Curso_has_docente {
    private Curso curso;
    private Usuario docente;

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Usuario getDocente() {
        return docente;
    }

    public void setDocente(Usuario docente) {
        this.docente = docente;
    }
}
