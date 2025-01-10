package conexion1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class PanelUsuario extends JFrame {

    private JLabel lblNombre, lblCorreo;
    private JButton btnCerrarSesion;

    public PanelUsuario(String usuario) {
        // Configurar JFrame
        setTitle("Panel de Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.setBackground(new Color(230, 230, 250));

        // Consultar los datos del usuario desde la base de datos
        Usuario datosUsuario = obtenerDatosUsuario(usuario);
        if (datosUsuario == null) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Etiquetas para mostrar los datos
        lblNombre = new JLabel("Nombre: " + datosUsuario.getNombre());
        lblCorreo = new JLabel("Correo: " + datosUsuario.getCorreo());

        // Botón de cerrar sesión
        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Sesión cerrada");
                dispose();
            }
        });

        // Agregar los componentes al panel
        panel.add(lblNombre);
        panel.add(lblCorreo);
        panel.add(new JSeparator());
        panel.add(btnCerrarSesion);

        // Agregar el panel al JFrame
        add(panel);
        setVisible(true);
    }

    private Usuario obtenerDatosUsuario(String usuario) {
        Usuario usuarioEncontrado = null;
        try {
            // Conexión a la base de datos
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/usuarios", "root", "Rodri2305");
            String sql = "SELECT nombre, correo FROM usuarios WHERE usuario = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuarioEncontrado = new Usuario(rs.getString("nombre"), rs.getString("correo"));
            }

            rs.close();
            stmt.close();
            conexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioEncontrado;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PanelUsuario("RodrigoPrada");
        });
    }
}

class Usuario {
    private String nombre;
    private String correo;

    public Usuario(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }
}

