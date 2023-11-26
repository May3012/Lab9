package com.example.lab9.Daos;

import com.example.lab9.Beans.Rol;
import com.example.lab9.Beans.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDao extends DaoBase {


    public boolean validarUsuarioPassword(String email, String password){

        String sql = "select u.idUsuario, u.nombre,u.correo,r.idrol, r.nombre from usuario u\n" +
                "inner join lab_9.rol r on (u.idrol= r.idrol)\n" +
                "where correo = ? and password = sha2(?,256)";

        boolean exito = false;

        try(Connection connection = getConection();
            PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setString(1,email);
            pstmt.setString(2,password);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    exito = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exito;
    }
/*
    public boolean validarUsuarioPasswordHashed(String username, String password){

        String sql = "SELECT * FROM usuarios_credentials where email = ? and password_hashed = sha2(?,256)";

        boolean exito = false;

        try(Connection connection = getConection();
            PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setString(1,username);
            pstmt.setString(2,password);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    exito = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exito;
    }
*/
    public Usuario obtenerUsuario(String email) {
        Usuario usuario = null;

        String sql = "select u.idUsuario, u.nombre,u.correo,r.idrol, r.nombre from usuario u\n" +
                "inner join lab_9.rol r on (u.idrol= r.idrol)\n" +
                "where u.correo = ?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt(1));
                    usuario.setNombre(rs.getString(2));
                    usuario.setCorreo(rs.getString(3));
                    Rol rol = new Rol();
                    rol.setIdRol(rs.getInt(4));
                    rol.setNameRol(rs.getString(5));
                    usuario.setRol(rol);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return usuario;
    }


}
