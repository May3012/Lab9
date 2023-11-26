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

    /*
    public Employee obtenerEmpleado(int employeeId) {

        Employee employee = null;

        String sql = "SELECT * FROM employees e \n"
                + "left join jobs j ON (j.job_id = e.job_id) \n"
                + "left join departments d ON (d.department_id = e.department_id)\n"
                + "left  join employees m ON (e.manager_id = m.employee_id)\n"
                + "WHERE e.employee_id = ?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employeeId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    employee = new Employee();
                    fetchEmployeeData(employee, rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return employee;
    }

    */
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
