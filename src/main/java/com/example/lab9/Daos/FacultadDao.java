package com.example.lab9.Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultadDao extends DaoBase {
    public int obtenerIdFacultadXIdDecano(int idDecano){
        Integer idFacultad = 0;
        String sql = "select idfacultad from facultad_has_decano where iddecano=?";
        try(Connection conn = this.getConection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idDecano);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    idFacultad = rs.getInt("idFacultad");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idFacultad;
    }

}
