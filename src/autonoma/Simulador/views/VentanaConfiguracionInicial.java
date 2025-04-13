package autonoma.Simulador.views;

import autonoma.Simulador.models.componentes.*;
import autonoma.Simulador.models.vehiculos.Vehiculo;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class VentanaConfiguracionInicial extends JFrame {

    private JLabel fondo;

    public VentanaConfiguracionInicial() {
        setTitle("🚘 Simulador de Vehículos - Configuración Inicial");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Fondo redimensionado
        fondo = new JLabel();
        fondo.setIcon(escalarFondo("autonoma/Simulador/images/fondoPrincipal.png", 800, 600));
        fondo.setBounds(0, 0, 800, 600);
        fondo.setLayout(null);
        add(fondo);

        // Panel semi-transparente sobre fondo
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBounds(150, 100, 500, 400);
        panelCentral.setOpaque(true);
        panelCentral.setBackground(new Color(0, 0, 0, 180)); // Fondo negro con transparencia
        fondo.add(panelCentral);

        // Título
        JLabel titulo = new JLabel("🚗 Configura tu vehículo");
        titulo.setForeground(Color.RED);
        titulo.setFont(new Font("Verdana", Font.BOLD, 28));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(titulo);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botón: Cargar desde archivo
        JButton btnCargarArchivo = new JButton("📂 Cargar desde archivo");
        estilizarBoton(btnCargarArchivo);
        btnCargarArchivo.addActionListener(e -> cargarDesdeArchivo());
        panelCentral.add(btnCargarArchivo);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botón: Elegir manualmente
        JButton btnElegirManual = new JButton("⚙️ Elegir manualmente");
        estilizarBoton(btnElegirManual);
        btnElegirManual.addActionListener(e -> elegirManual());
        panelCentral.add(btnElegirManual);
    }

    private void estilizarBoton(JButton boton) {
        boton.setBackground(Color.RED);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 20));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(250, 50));
    }

    private void cargarDesdeArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo de configuración");

        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try {
                Vehiculo vehiculo = Vehiculo.desdeArchivo(archivo.getAbsolutePath());
                new VentanaPrincipal(vehiculo).setVisible(true);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error al cargar el archivo: " + ex.getMessage());
            }
        }
    }

    private void elegirManual() {
        String[] tiposLlantas = {"buenas", "bonitas", "baratas"};
        String[] tiposMotor = {"1000", "2000", "3000"};

        JComboBox<String> comboLlantas = new JComboBox<>(tiposLlantas);
        JComboBox<String> comboMotor = new JComboBox<>(tiposMotor);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("🛞 Tipo de llantas:"));
        panel.add(comboLlantas);
        panel.add(new JLabel("🔧 Cilindraje del motor:"));
        panel.add(comboMotor);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Configurar Vehículo", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            String tipoLlantas = (String) comboLlantas.getSelectedItem();
            int cilindraje = Integer.parseInt((String) comboMotor.getSelectedItem());

            try {
                // Guardamos config
                PrintWriter writer = new PrintWriter("configuracion.txt");
                writer.println("llantas " + tipoLlantas);
                writer.println("motor " + cilindraje);
                writer.close();

                // Creamos el vehículo
                Llantas llantas = switch (tipoLlantas) {
                    case "buenas" -> new LlantasBuenas();
                    case "bonitas" -> new LlantasBonitas();
                    case "baratas" -> new LlantasBaratas();
                    default -> throw new IllegalArgumentException("Tipo de llantas inválido");
                };

                Motor motor = switch (cilindraje) {
                    case 1000 -> new Motor1000();
                    case 2000 -> new Motor2000();
                    case 3000 -> new Motor3000();
                    default -> throw new IllegalArgumentException("Cilindraje inválido");
                };

                Vehiculo vehiculo = new Vehiculo(llantas, motor);
                new VentanaPrincipal(vehiculo).setVisible(true);
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error al crear el vehículo: " + ex.getMessage());
            }
        }
    }

    private ImageIcon escalarFondo(String ruta, int ancho, int alto) {
        try {
            ImageIcon icono = new ImageIcon(getClass().getClassLoader().getResource(ruta));
            Image imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagen);
        } catch (Exception e) {
            System.err.println("❌ No se pudo cargar/escalar la imagen: " + ruta);
            return null;
        }
    }
}


