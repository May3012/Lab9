package com.example.lab9.Daos;

import com.example.lab9.Beans.Curso;
import com.example.lab9.Beans.Facultad;

import java.sql.*;
import java.util.ArrayList;

public class CursoDao extends DaoBase {

    public void registrarCurso(String nombre, String codigo, int idDocente, int idFacultad){
        String sql = "insert into curso (codigo,nombre,idfacultad,fecha_registro,fecha_edicion) values (?,?,?,NOW(),NOW())";
        try(Connection conn = this.getConection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,codigo);
            pstmt.setString(2,nombre);
            pstmt.setInt(3,idFacultad);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int idCurso = obtenerIdCurso(idFacultad);
        String sql2 = "insert into curso_has_docente (idcurso, iddocente) values (?,?)";
        try(Connection conn2 = this.getConection();
            PreparedStatement pstmt2 = conn2.prepareStatement(sql2)){
            pstmt2.setInt(1,idCurso);
            pstmt2.setInt(2,idDocente);
            pstmt2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int obtenerIdCurso(int idFacultad){
        String sql = "SELECT MAX(idcurso) AS idcurso FROM curso WHERE idfacultad = ?";
        try(Connection conn = this.getConection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idFacultad);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1);
                }else{
                    return 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Curso> listarCursos(int idDecano) {
        ArrayList<Curso> cursos = new ArrayList<>();
        String sql = "SELECT c.idcurso, c.nombre, c.fecha_registro, c.fecha_edicion, c.codigo, c.idfacultad,f.nombre,f.iduniversidad,f.fecha_registro,f.fecha_edicion FROM curso c\n" +
                "INNER JOIN facultad f ON c.idfacultad = f.idfacultad \n" +
                "INNER JOIN facultad_has_decano fhd ON f.idfacultad = fhd.idfacultad \n" +
                "WHERE fhd.iddecano = ?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idDecano);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Curso curso = new Curso();
                    curso.setIdCurso(rs.getInt("idcurso"));
                    curso.setNombre(rs.getString("nombre"));
                    curso.setFecha_registro(rs.getString(3));
                    curso.setFecha_edicion(rs.getString(4));
                    curso.setCodigo(rs.getString("codigo"));
                    Facultad facultad = new Facultad();
                    facultad.setIdFacutlad(rs.getInt(6));
                    facultad.setNombreFacu(rs.getString(7));
                    facultad.setIdUniversidad(rs.getInt(8));
                    facultad.setFecha_registro(rs.getString(9));
                    facultad.setFecha_edicion(rs.getString(10));

                    curso.setFacultad(facultad);
                    cursos.add(curso);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cursos;
    }


    public void editarNombreCurso(int idCurso, String nuevoNombre) {
        String sql = "UPDATE curso SET nombre = ?,fecha_edicion=NOW() WHERE idcurso = ?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nuevoNombre);
            pstmt.setInt(2, idCurso);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void borrarCurso(int idCurso) {
        String sql = "DELETE curso_has_docente, curso FROM curso_has_docente \n" +
                "JOIN curso ON curso_has_docente.idcurso = curso.idcurso \n" +
                "WHERE curso.idcurso = ?";
        try(Connection conn = this.getConection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idCurso);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int cantidadEvaluacionesPorCurso(int idCurso) {
        String sql = "SELECT COUNT(*) AS cantidad FROM evaluaciones WHERE idcurso = ?";
        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCurso);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cantidad");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public Curso obtenerCursoPorID(int idCurso){
        String sql = "SELECT c.*,f.idfacultad, f.nombre AS nombre_facultad FROM curso c \n" +
                "INNER JOIN facultad f ON c.idfacultad = f.idfacultad\n" +
                "WHERE c.idcurso = ?";
        Curso curso = null;

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCurso);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    curso = new Curso();
                    curso.setIdCurso(rs.getInt("idcurso"));
                    curso.setCodigo(rs.getString("codigo"));
                    curso.setNombre(rs.getString("nombre"));
                    Facultad f = new Facultad();
                    f.setIdFacutlad(rs.getInt("idfacultad"));
                    f.setNombreFacu(rs.getString("nombre_facultad"));
                    curso.setFacultad(f);
                    curso.setFecha_registro(rs.getString("fecha_registro"));
                    curso.setFecha_edicion(rs.getString("fecha_edicion"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return curso;
    }

}
