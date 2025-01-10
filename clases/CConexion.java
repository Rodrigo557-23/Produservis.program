package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CConexion {
    private static Connection conn;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = "root";
    private static final String pass = "Rodri2305";
    private static final String url = "jdbc:mysql://localhost:3306/login";

    public Connection conectar() {
        conn = null;
        try {
            // Cargar el controlador JDBC
            Class.forName(driver);
            
            // Establecer la conexión
            conn = DriverManager.getConnection(url, user, pass);
            //JOptionPane.showMessageDialog(null, "Conexión establecida");

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el controlador JDBC: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage());
        }
        
        return conn; // Devuelve la conexión, sea nula o válida
    }
}

