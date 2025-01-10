package conexion1;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Inicia el JFrame de Login
        SwingUtilities.invokeLater(() -> {
            new Inicio().setVisible(true);
        });
    }
}

