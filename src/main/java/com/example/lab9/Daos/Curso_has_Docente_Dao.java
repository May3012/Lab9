package com.example.lab9.Daos;
import com.example.lab9.Beans.Curso;
import com.example.lab9.Beans.Curso_has_docente;
import com.example.lab9.Beans.Usuario;

import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;

public class Curso_has_Docente_Dao extends DaoBase{

    public Integer obtenerCursoXDocente(int idDocente) {
        Integer idCurso = 0;

        String sql = "SELECT c.idcurso, c.codigo, c.nombre, d.idUsuario, d.nombre AS nombreDocente, d.correo " +
                "FROM curso_has_docente cd " +
                "INNER JOIN curso c ON cd.idCurso = c.idCurso " +
                "INNER JOIN usuario d ON cd.idDocente = d.idUsuario " +
                "WHERE d.idusuario = ?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, idDocente);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    idCurso = rs.getInt("idCurso");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idCurso;
    }
}
