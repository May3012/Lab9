package com.example.lab9.Daos;

import com.example.lab9.Beans.Curso;
import com.example.lab9.Beans.Evaluacion;
import com.example.lab9.Beans.Semestre;

import java.sql.*;
import java.util.ArrayList;

public class EvaluacionesDao extends DaoBase {

    public ArrayList<Evaluacion> listarEvaluaciones(int idDocente) {
        ArrayList<Evaluacion> listarEvaluaciones = new ArrayList<>();

        String sql = "SELECT e.idevaluaciones, e.nombre_estudiantes, e.codigo_estudiantes, e.correo_estudiantes, e.nota, e.idsemestre,s.nombre as nombre_semestre,\n" +
                "s.idadmistrador,s.habilitado,s.fecha_registro as fecha_registro_semestre,s.fecha_edicion, e.fecha_registro, e.fecha_edicion \n" +
                "FROM evaluaciones e \n" +
                "INNER JOIN curso_has_docente cd ON e.idcurso = cd.idcurso\n" +
                "INNER JOIN semestre s ON e.idsemestre = s.idsemestre\n" +
                "WHERE cd.iddocente = ?";
        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idDocente);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Evaluacion eva = new Evaluacion();
                    eva.setIdEvalluaciones(rs.getInt(1));
                    eva.setNombre_estudiantes(rs.getString(2));
                    eva.setCodigo_estudiantes(rs.getString(3));
                    eva.setCorreo_estudiantes(rs.getString(4));
                    eva.setNota(rs.getInt(5));
                    Semestre semestre = new Semestre();
                    semestre.setIdSemestre(rs.getInt(6));
                    semestre.setNombre(rs.getString(7));
                    semestre.setIdAdministrador(rs.getInt(8));
                    semestre.setHabilitado(rs.getInt(9));
                    semestre.setFecha_registro(rs.getString(10));
                    semestre.setFecha_edicion(rs.getString(11));

                    eva.setSemestre(semestre);
                    eva.setFecha_registro(rs.getString(12));
                    eva.setFecha_edicion(rs.getString(13));

                    listarEvaluaciones.add(eva);

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listarEvaluaciones;
    }

    public Evaluacion obtenerEvaluacion(int idDocente) {
        Evaluacion eva = null;
        String sql = "SELECT e.idevaluaciones, e.nombre_estudiantes, e.codigo_estudiantes, e.correo_estudiantes, e.nota, e.idsemestre,s.nombre as nombre_semestre,\n" +
                "s.idadmistrador,s.habilitado,s.fecha_registro as fecha_registro_semestre,s.fecha_edicion, e.fecha_registro, e.fecha_edicion FROM evaluaciones e \n" +
                "INNER JOIN curso_has_docente cd ON e.idcurso = cd.idcurso\n" +
                "INNER JOIN semestre s ON e.idsemestre = s.idsemestre\n" +
                "WHERE cd.iddocente = ?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idDocente);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    eva = new Evaluacion();
                    eva.setIdEvalluaciones(rs.getInt(1));
                    eva.setNombre_estudiantes(rs.getString(2));
                    eva.setCodigo_estudiantes(rs.getString(3));
                    eva.setCorreo_estudiantes(rs.getString(4));
                    eva.setNota(rs.getInt(5));
                    Semestre semestre = new Semestre();
                    semestre.setIdSemestre(rs.getInt(6));
                    semestre.setNombre(rs.getString(7));
                    semestre.setIdAdministrador(rs.getInt(8));
                    semestre.setHabilitado(rs.getInt(9));
                    semestre.setFecha_registro(rs.getString(10));
                    semestre.setFecha_edicion(rs.getString(11));

                    eva.setSemestre(semestre);
                    eva.setFecha_registro(rs.getString(12));
                    eva.setFecha_edicion(rs.getString(13));

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return eva;
    }
    public void guardarEvaluacion(String nombre, String codigo, String correo, int nota,int idCurso,int idSemestre) throws SQLException {

        String sql = "INSERT INTO evaluaciones (nombre_estudiantes, codigo_estudiantes, correo_estudiantes, nota, idcurso, idsemestre, fecha_registro, fecha_edicion)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, codigo);
            pstmt.setString(3, correo);
            pstmt.setInt(4, nota);
            pstmt.setInt(5, idCurso);
            pstmt.setInt(6, idSemestre);
            pstmt.executeUpdate();
        }
    }

    public void editarEvaluacion(int idEvaluacion, String nombre, String codigo, String correo, int nota){
        String sql = "update evaluacion set nombre_estudiante=?, correo_estudiante=?, codigo_estudiante=?, nota=?, fecha_edicion=NOW() where idevaluacion=?";
        try(Connection conn = this.getConection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,nombre);
            pstmt.setString(2,correo);
            pstmt.setString(3,codigo);
            pstmt.setInt(4,nota);
            pstmt.setInt(5,idEvaluacion);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*public void actualizarEmpleado(Employee employee) throws SQLException {

        String sql = "UPDATE evaluaciones\n" +
                "SET nombre_estudiantes = ?, codigo_estudiantes = ?, correo_estudiantes = ?, nota = ?, fecha_edicion = NOW()\n" +
                "WHERE idevaluaciones = ?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            setEmployeeParams(pstmt, employee);
            pstmt.setInt(11, employee.getEmployeeId());
            pstmt.executeUpdate();
        }
    }

    */
    public void borrarEvaluacion(int idEvaluacion) {
        String sql = "DELETE FROM evaluaciones WHERE idevaluaciones = ?";
        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idEvaluacion);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
