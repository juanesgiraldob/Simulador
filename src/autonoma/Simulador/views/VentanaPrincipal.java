package autonoma.Simulador.views;

import autonoma.Simulador.models.vehiculos.Vehiculo;
import autonoma.Simulador.models.excepciones.ExcepcionVehiculo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.Timer;
import java.net.URL;

public class VentanaPrincipal extends JFrame {
    private Vehiculo vehiculo;
    private JLabel imagenCarro;
    private JLabel fondo;
    private Timer animacion;
    private JLabel panelVelocidad; // Nuevo panel de velocidad

    public VentanaPrincipal(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;

        setTitle("🚗 Simulador de Vehículo - Rápido y Furioso Edition");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        add(crearPanelBotones(), BorderLayout.NORTH);
        add(crearPanelPrincipal(), BorderLayout.CENTER);
    }

    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(crearPanelImagen(), BorderLayout.CENTER);
        panel.add(crearPanelVelocidad(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelVelocidad() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);

        panelVelocidad = new JLabel("🚘 Velocidad actual: 0 km/h");
        panelVelocidad.setForeground(Color.green);
        panelVelocidad.setFont(new Font("Arial", Font.BOLD, 18));

        panel.add(panelVelocidad);
        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new GridLayout(3, 3, 10, 10));
        panel.setBackground(Color.black);

        String[] acciones = {
            "Encender", "Apagar", "Acelerar", "Frenar",
            "Frenado Brusco", "Derrapar", "Patinar", "Chocar", "Salir"
        };

        for (String accion : acciones) {
            JButton boton = new JButton(accion);
            boton.setBackground(Color.red);
            boton.setForeground(Color.white);
            boton.setFont(new Font("Arial", Font.BOLD, 16));
            boton.addActionListener(new ManejadorBotones(accion));
            panel.add(boton);
        }

        JButton btnCargar = new JButton("📂 Cargar configuración");
        btnCargar.setBackground(Color.orange);
        btnCargar.setForeground(Color.black);
        btnCargar.setFont(new Font("Arial", Font.BOLD, 16));
        btnCargar.addActionListener(e -> cargarConfiguracion());
        panel.add(btnCargar);

        return panel;
    }

    private JPanel crearPanelImagen() {
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(900, 500));

        fondo = new JLabel();
        fondo.setBounds(0, 0, 900, 500);
        fondo.setLayout(null);
        fondo.setIcon(escalarImagen("autonoma/Simulador/images/fondo.jpg", 900, 500));

        imagenCarro = new JLabel();
        imagenCarro.setBounds(200, 100, 480, 360);
        imagenCarro.setIcon(escalarGif("autonoma/Simulador/images/apagar.gif", 480, 360));
        fondo.add(imagenCarro);

        panel.add(fondo);
        return panel;
    }

    private void cargarConfiguracion() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo de configuración");

        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try {
                vehiculo = Vehiculo.desdeArchivo(archivo.getAbsolutePath());
                JOptionPane.showMessageDialog(this,
                        "✅ Vehículo cargado correctamente:\n🛞 Llantas: " + vehiculo.getTipoLlantas() +
                                "\n🔧 Motor: " + vehiculo.getCilindrajeMotor() + " cc");
                actualizarVelocidad();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error al cargar vehículo: " + ex.getMessage());
            }
        }
    }

    private class ManejadorBotones implements ActionListener {
        private final String accion;

        public ManejadorBotones(String accion) {
            this.accion = accion;
        }

        public void actionPerformed(ActionEvent e) {
            try {
                detenerAnimacion();

                switch (accion) {
                    case "Encender":
                        vehiculo.encender();
                        cambiarGif("encender.gif");
                        mostrarMensaje("🔥 Vehículo encendido");
                        break;
                    case "Apagar":
                        vehiculo.apagar();
                        cambiarGif("apagar.gif");
                        mostrarMensaje("💤 Vehículo apagado");
                        break;
                    case "Acelerar":
                        vehiculo.acelerar(20);
                        cambiarGif("acelerar.gif");
                        moverCarroLateral();
                        mostrarMensaje("🚀 Acelerando...");
                        break;
                    case "Frenar":
                        vehiculo.frenar(15);
                        cambiarGif("frenado.gif");
                        mostrarMensaje("🛑 Frenando...");
                        break;
                    case "Frenado Brusco":
                        vehiculo.frenadoBrusco(40);
                        cambiarGif("frenadoBrusco.gif");
                        mostrarMensaje("💥 ¡Frenado brusco!");
                        break;
                    case "Derrapar":
                        vehiculo.derrapar();
                        cambiarGif("derrapar.gif");
                        moverCarroVertical();
                        mostrarMensaje("🔄 ¡Derrapando!");
                        break;
                    case "Patinar":
                        vehiculo.patinar();
                        cambiarGif("patinar.gif");
                        moverCarroLateral();
                        mostrarMensaje("🌀 ¡Patinando!");
                        break;
                    case "Chocar":
                        vehiculo.chocar();
                        cambiarGif("chocar.gif");
                        mostrarMensaje("💥 ¡Has chocado!");
                        break;
                    case "Salir":
                        System.exit(0);
                        break;
                }

                actualizarVelocidad();

            } catch (ExcepcionVehiculo ex) {
                mostrarMensaje("⚠️ " + ex.getMessage());
            }
        }
    }

    private void cambiarGif(String nombreGif) {
        String ruta = "autonoma/Simulador/images/" + nombreGif;
        imagenCarro.setIcon(escalarGif(ruta, 480, 360));
        imagenCarro.setLocation(200, 100);
    }

    private ImageIcon escalarImagen(String rutaRelativa, int ancho, int alto) {
        URL recurso = getClass().getClassLoader().getResource(rutaRelativa);
        if (recurso != null) {
            ImageIcon icono = new ImageIcon(recurso);
            Image imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagen);
        } else {
            System.err.println("❌ No se pudo cargar imagen: " + rutaRelativa);
            return null;
        }
    }

    private ImageIcon escalarGif(String rutaRelativa, int ancho, int alto) {
        URL recurso = getClass().getClassLoader().getResource(rutaRelativa);
        if (recurso != null) {
            ImageIcon icono = new ImageIcon(recurso);
            Image imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT);
            return new ImageIcon(imagen);
        } else {
            System.err.println("❌ No se pudo cargar GIF: " + rutaRelativa);
            return null;
        }
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void actualizarVelocidad() {
        panelVelocidad.setText("🚘 Velocidad actual: " + vehiculo.getVelocidad() + " km/h");
    }

    private void moverCarroLateral() {
        final int[] x = { imagenCarro.getX() };
        animacion = new Timer(10, e -> {
            x[0] += 5;
            if (x[0] > getWidth()) x[0] = -200;
            imagenCarro.setLocation(x[0], imagenCarro.getY());
        });
        animacion.start();
    }

    private void moverCarroVertical() {
        final int[] y = { imagenCarro.getY() };
        animacion = new Timer(10, e -> {
            y[0] += 5;
            if (y[0] > getHeight()) y[0] = 0;
            imagenCarro.setLocation(imagenCarro.getX(), y[0]);
        });
        animacion.start();
    }

    private void detenerAnimacion() {
        if (animacion != null && animacion.isRunning()) {
            animacion.stop();
        }
    }
}