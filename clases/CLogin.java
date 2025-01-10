package clases;

import conexion1.Menuprinci;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class CLogin {
    public void validaUsuario(JTextField usuario, JPasswordField contraseña) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Obtener conexión
            clases.Conexion objetoConexion = new clases.Conexion();
            conn = objetoConexion.conectar();

            if (conn == null) {
                throw new Exception("La conexión con la base de datos no se pudo establecer.");
            }

            // Consulta SQL
            String consulta = "SELECT * FROM Usuarios WHERE usuario = ? AND password = ?";

            // Preparar la consulta
            ps = conn.prepareStatement(consulta);
            String contra = String.valueOf(contraseña.getPassword());
            ps.setString(1, usuario.getText());
            ps.setString(2, contra);

            // Ejecutar consulta
            rs = ps.executeQuery();

            // Validar resultados
            if (rs.next()) {
               JOptionPane.showMessageDialog(null, "El usuario es correcto");
                Menuprinci objetoMenu = new Menuprinci();
                objetoMenu.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "El usuario es INCORRECTO, VUELVE A INTENTAR");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ERROR al cerrar recursos: " + e.getMessage());
            }
        }
    }
}
