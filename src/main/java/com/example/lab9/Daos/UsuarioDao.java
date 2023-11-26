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
                "where correo = ? and password = ?";

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

    //Para registrar docentes
    public void registrarDocente(String nombre, String correo, String password) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, correo, password, idrol,ultimo_ingreso,cantidad_ingresos fecha_registro, fecha_edicion) " +
                "VALUES (?, ?, ?, (SELECT idrol FROM rol WHERE nombre = 'Docente'),NOW(),0, NOW(), NOW())";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, correo);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
        }
    }

    // Método para editar el nombre de un docente
    public void editarNombreDocente(int idDocente, String nuevoNombre) throws SQLException {
        String sql = "UPDATE usuario SET nombre = ? WHERE idusuario = ? AND idrol = (SELECT idrol FROM rol WHERE nombre = 'Docente')";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nuevoNombre);
            pstmt.setInt(2, idDocente);
            pstmt.executeUpdate();
        }
    }

    // Método para obtener la lista de docentes según las condiciones dadas
    public ArrayList<Usuario> listarDocentes(int idDecano) {
        ArrayList<Usuario> listaDocentes = new ArrayList<>();

        // Implementa la lógica necesaria para obtener la lista de docentes según las condiciones dadas

        return listaDocentes;
    }

    // Método para eliminar un docente
    public void eliminarDocente(int idDocente) throws SQLException {
        String sql = "DELETE FROM usuario WHERE idusuario = ? AND idrol = (SELECT idrol FROM rol WHERE nombre = 'Docente')";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idDocente);
            pstmt.executeUpdate();
        }
    }


}
