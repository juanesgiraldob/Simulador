package autonoma.Simulador.main;

import autonoma.Simulador.models.vehiculos.Vehiculo;
import autonoma.Simulador.views.VentanaConfiguracionInicial;
import autonoma.Simulador.views.VentanaPrincipal;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaConfiguracionInicial().setVisible(true);
        });
    }

    
    /**
     * Busca el archivo en múltiples ubicaciones posibles
     * @param nombreArchivo Nombre del archivo a buscar
     * @return El archivo encontrado o null si no existe
     */
    private static File buscarArchivo(String nombreArchivo) {
        // Lista de posibles ubicaciones para buscar el archivo
        String[] ubicacionesPosibles = {
            nombreArchivo,                              // En el directorio actual
            "src/" + nombreArchivo,                     // En la carpeta src
            "src/autonoma/Simulador/resources/" + nombreArchivo,  // En resources
            "./resources/" + nombreArchivo,             // En carpeta resources a nivel de proyecto
            System.getProperty("user.dir") + File.separator + nombreArchivo  // Ruta absoluta al directorio de trabajo
        };
        
        // Intenta cargar desde las posibles ubicaciones
        for (String ruta : ubicacionesPosibles) {
            File archivo = new File(ruta);
            System.out.println("Probando ruta: " + archivo.getAbsolutePath() + " - Existe: " + archivo.exists());
            
            if (archivo.exists()) {
                return archivo;
            }
        }
        
        // Intenta cargar desde los recursos del classpath
        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(nombreArchivo);
            if (inputStream == null) {
                inputStream = Main.class.getResourceAsStream("/autonoma/Simulador/resources/" + nombreArchivo);
            }
            
            if (inputStream != null) {
                // Crear archivo temporal con el contenido del recurso
                Path tempFile = Files.createTempFile("config", ".txt");
                Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
                inputStream.close();
                
                File archivoTemp = tempFile.toFile();
                archivoTemp.deleteOnExit(); // Se borrará al cerrar la aplicación
                System.out.println("Archivo cargado desde recursos en: " + archivoTemp.getAbsolutePath());
                return archivoTemp;
            }
        } catch (Exception e) {
            System.err.println("Error al cargar desde recursos: " + e.getMessage());
        }
        
        // Si no encuentra el archivo, crea uno de configuración por defecto
        try {
            File archivoDefault = new File(System.getProperty("user.dir") + File.separator + nombreArchivo);
            crearArchivoConfiguracionPorDefecto(archivoDefault);
            System.out.println("Se ha creado un archivo de configuración por defecto en: " + archivoDefault.getAbsolutePath());
            return archivoDefault;
        } catch (Exception e) {
            System.err.println("Error al crear archivo por defecto: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Crea un archivo de configuración por defecto
     * @param archivo Archivo a crear
     */
    private static void crearArchivoConfiguracionPorDefecto(File archivo) throws Exception {
        // Ejemplo de contenido por defecto - ajústalo según el formato esperado por tu método Vehiculo.desdeArchivo
        String contenidoDefault = "# Configuración por defecto del vehículo\n" +
                                 "tipoVehiculo=Automovil\n" +
                                 "velocidadMaxima=120\n" +
                                 "aceleracion=10\n" +
                                 "# Añade más parámetros según sea necesario\n";
        
        Files.write(archivo.toPath(), contenidoDefault.getBytes());
    }
}