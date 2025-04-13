package autonoma.Simulador.models.vehiculos;

import autonoma.Simulador.models.*;
import autonoma.Simulador.models.componentes.*;
import autonoma.Simulador.models.excepciones.*;

import java.io.*;

public class Vehiculo extends VehiculoBase {

    public Vehiculo(Llantas llantas, Motor motor) {
        this.llantas = llantas;
        this.motor = motor;
    }

    public static Vehiculo desdeArchivo(String ruta) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(ruta));
        String linea;
        String tipoLlantas = "";
        int cilindraje = 0;

        while ((linea = lector.readLine()) != null) {
            if (linea.toLowerCase().startsWith("llantas")) {
                tipoLlantas = linea.split("\\s+")[1].trim();
            } else if (linea.toLowerCase().startsWith("motor")) {
                cilindraje = Integer.parseInt(linea.split("\\s+")[1].trim());
            }
        }
        lector.close();

        Llantas llantas;
        switch (tipoLlantas.toLowerCase()) {
            case "buenas": llantas = new LlantasBuenas(); break;
            case "bonitas": llantas = new LlantasBonitas(); break;
            case "baratas": llantas = new LlantasBaratas(); break;
            default: throw new IllegalArgumentException("Tipo de llantas desconocido: " + tipoLlantas);
        }

        Motor motor;
        switch (cilindraje) {
            case 1000: motor = new Motor1000(); break;
            case 2000: motor = new Motor2000(); break;
            case 3000: motor = new Motor3000(); break;
            default: throw new IllegalArgumentException("Cilindraje de motor no soportado: " + cilindraje);
        }

        return new Vehiculo(llantas, motor);
    }

    public void encender() throws ExcepcionVehiculo {
        if (encendido) throw new VehiculoYaEncendidoException();
        encendido = true;
    }

    public void apagar() throws ExcepcionVehiculo {
        if (!encendido) throw new VehiculoYaApagadoException();
        if (velocidad > 60) {
            accidentado = true;
            encendido = false;
            velocidad = 0;
            throw new VehiculoAccidentadoException("Se apagó el vehículo en movimiento");
        }
        encendido = false;
    }

    public void acelerar(int kmh) throws ExcepcionVehiculo {
        if (!encendido) throw new VehiculoApagadoException();
        if (accidentado) throw new VehiculoAccidentadoException("El vehículo ya está accidentado");
        if (patinando) throw new VehiculoPatinaException("Está patinando");
        velocidad += kmh;
        if (velocidad > motor.getVelocidadMaxima()) {
            accidentado = true;
            encendido = false;
            velocidad = 0;
            throw new VehiculoAccidentadoException("Excedió la capacidad del motor");
        }
    }

    public void frenar(int kmh) throws ExcepcionVehiculo {
        if (!encendido) throw new VehiculoApagadoException();
        if (velocidad == 0) throw new VehiculoDetenidoException();
        if (kmh > velocidad) {
            patinando = true;
            throw new VehiculoPatinaException("Frenó con intensidad mayor a la velocidad actual");
        }
        velocidad -= kmh;
        if (velocidad == 0) patinando = false;
    }

    public void frenadoBrusco(int kmh) throws ExcepcionVehiculo {
        if (!encendido) throw new VehiculoApagadoException();
        if (kmh > 30 && velocidad > llantas.getVelocidadMaxima()) {
            patinando = true;
            velocidad -= kmh;
            throw new VehiculoPatinaException("Frenado brusco sobre el límite de las llantas");
        }
        frenar(kmh);
    }

    public void derrapar() throws ExcepcionVehiculo {
        patinando = true;
        throw new VehiculoPatinaException("El conductor realizó una maniobra peligrosa");
    }

    public void chocar() throws ExcepcionVehiculo {
        accidentado = true;
        encendido = false;
        velocidad = 0;
        throw new VehiculoAccidentadoException("Impacto directo contra un objeto");
    }

    /**
     * Hace que el vehículo patine, reduciendo drásticamente la velocidad.
     * @throws ExcepcionVehiculo si el vehículo no está encendido o si ya está detenido.
     */
    public void patinar() throws ExcepcionVehiculo {
        if (!encendido) {
            throw new ExcepcionVehiculo("🚫 No puedes patinar con el vehículo apagado.");
        }

        if (velocidad == 0) {
            throw new ExcepcionVehiculo("🛑 No puedes patinar si el vehículo está detenido.");
        }

        patinando = true;
        int reduccion = (int)(velocidad * 0.4); // Reduce un 40% de la velocidad
        velocidad -= reduccion;
        if (velocidad < 0) velocidad = 0;

        System.out.println("🔁 El vehículo está patinando. Nueva velocidad: " + velocidad + " km/h");
    }
}
