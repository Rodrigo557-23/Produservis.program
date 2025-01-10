package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlUsuarios extends Conexion {

    // Método para registrar un usuario
    public boolean registrar(usuarios usr) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO usuarios (usuario, password, nombre, apellidos, correo, celular, edad, cargo, id_tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getUsuario());
            ps.setString(2, usr.getPassword());
            ps.setString(3, usr.getNombre());
            ps.setString(4, usr.getApellidos());
            ps.setString(5, usr.getCorreo());
            ps.setString(6, usr.getCelular());
            ps.setString(7, usr.getEdad());
            ps.setString(8, usr.getCargo());
            ps.setInt(9, usr.getId_tipo());
            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    // Método para iniciar sesión
    public boolean login(usuarios usr) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT id, usuario, password, nombre, id_tipo FROM usuarios WHERE usuario = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getUsuario());
            rs = ps.executeQuery();

            if (rs.next()) {
                if (usr.getPassword().equals(rs.getString("password"))) {
                    
                            String sqlUpdate = "UPDATE usuarios SET last_session = ? WHERE id = ?";
                            
                            ps = con.prepareStatement(sqlUpdate);
                            ps.setString(1, usr.getLast_session());
                            ps.setInt(2, rs.getInt(1));
                            ps.execute();
                    
                    usr.setId(rs.getInt("id"));
                    usr.setNombre(rs.getString("nombre"));
                    usr.setId_tipo(rs.getInt("id_tipo"));
                    return true;
                }
            }
            return false;

        } catch (SQLException ex) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    // Método para obtener la conexión
    private Connection getConexion() {
        Conexion conexion = new Conexion();
        return conexion.conectar();
    }
}

        

