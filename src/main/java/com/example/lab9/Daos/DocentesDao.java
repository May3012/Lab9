package com.example.lab9.Daos;

import com.example.lab9.Beans.Evaluacion;
import com.example.lab9.Beans.Rol;
import com.example.lab9.Beans.Semestre;
import com.example.lab9.Beans.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DocentesDao extends DaoBase {

    public void registrarDocente(String nombre, String correo, String password) {
        String sql = "INSERT INTO usuario (nombre, correo, password, idrol,ultimo_ingreso,cantidad_ingresos, fecha_registro, fecha_edicion) " +
                "VALUES (?, ?, ?, 4,NOW(),0, NOW(), NOW())";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, correo);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para editar el nombre de un docente
    public void editarNombreDocente(int idDocente, String nuevoNombre){
                String sql = "UPDATE usuario SET nombre = ?,fecha_edicion = NOW() WHERE idusuario = ?";

                try (Connection conn = this.getConection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, nuevoNombre);
                    pstmt.setInt(2, idDocente);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
        }
    }

    // Método para obtener la lista de docentes
    public ArrayList<Usuario> listarDocentes(int idDecano) {
        ArrayList<Usuario> listaDocentes = new ArrayList<>();

        String sql = "SELECT u.idusuario, u.nombre, u.correo, u.fecha_registro,u.fecha_edicion FROM usuario u\n" +
                "LEFT JOIN curso_has_docente cd ON u.idusuario = cd.iddocente \n" +
                "LEFT JOIN curso c ON cd.idcurso = c.idcurso\n" +
                "WHERE (cd.idcurso IS NULL)\n" +
                "AND u.idrol = (SELECT idrol FROM rol WHERE nombre = 'Docente')\n" +
                "UNION\n" +
                "SELECT u.idusuario, u.nombre, u.correo, u.fecha_registro,u.fecha_edicion FROM usuario u\n" +
                "LEFT JOIN curso_has_docente cd ON u.idusuario = cd.iddocente \n" +
                "LEFT JOIN curso c ON cd.idcurso = c.idcurso\n" +
                "WHERE c.idfacultad IN (SELECT idfacultad FROM facultad_has_decano WHERE iddecano = ?)\n" +
                "AND u.idrol = (SELECT idrol FROM rol WHERE nombre = 'Docente');";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idDecano);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Usuario docente = new Usuario();
                    docente.setIdUsuario(rs.getInt("idusuario"));
                    docente.setNombre(rs.getString("nombre"));
                    docente.setCorreo(rs.getString("correo"));
                    docente.setFecha_registro(rs.getString("fecha_registro"));
                    docente.setFecha_edicion(rs.getString("fecha_edicion"));
                    listaDocentes.add(docente);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaDocentes;
    }

    // Método para eliminar un docente
    public void eliminarDocente(int idDocente) {
        String sql = "DELETE FROM usuario WHERE idusuario = ? AND idrol = (SELECT idrol FROM rol WHERE nombre = 'Docente')";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idDocente);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int cantidadCursosAsignados(int idDocente) throws SQLException {
        int cantidadCursos = 0;
        String sql = "SELECT COUNT(*) FROM curso_has_docente WHERE iddocente = ?";
        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idDocente);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    cantidadCursos = resultSet.getInt(1);
                }
            }
        }
        return cantidadCursos;
    }

    public ArrayList<Usuario> listaDocentesSinCurso(){
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.idusuario, u.nombre FROM usuario u\n" +
                "INNER JOIN rol r ON u.idrol = r.idrol\n" +
                "WHERE r.nombre='Docente' AND u.idusuario NOT IN (SELECT iddocente FROM curso_has_docente)";

        try(Connection conn = this.getConection();
            ResultSet rs = conn.prepareStatement(sql).executeQuery()){
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt(1));
                usuario.setNombre(rs.getString(2));
                lista.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public Usuario obtenerDocentePorId(int idUsuario){
        String sql = "select idusuario, nombre, correo, ultimo_ingreso, cantidad_ingresos, fecha_registro, fecha_edicion from usuario where idrol = 4 and idusuario=?";
        try(Connection conn = this.getConection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idUsuario);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt(1));
                    usuario.setNombre(rs.getString(2));
                    usuario.setCorreo(rs.getString(3));
                    usuario.setUltimo_ingreso(rs.getString(4));
                    usuario.setCantidad_ingresos(rs.getInt(5));
                    usuario.setFecha_registro(rs.getString(6));
                    usuario.setFecha_edicion(rs.getString(7));
                    return usuario;
                }else{
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
