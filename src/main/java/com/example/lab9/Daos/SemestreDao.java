package com.example.lab9.Daos;

import com.example.lab9.Beans.Evaluacion;
import com.example.lab9.Beans.Semestre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SemestreDao extends DaoBase{
    public ArrayList<Semestre> listarSemestres() {
        ArrayList<Semestre> listarSemestre = new ArrayList<>();

        String sql = "select idsemestre,nombre from semestre";
        try (Connection conn = this.getConection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Semestre s = new Semestre();
                s.setIdSemestre(rs.getInt(1));
                s.setNombre(rs.getString(2));
                listarSemestre.add(s);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listarSemestre;
    }
}
