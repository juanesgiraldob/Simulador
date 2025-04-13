
package autonoma.Simulador.models.excepciones;

public class VehiculoAccidentadoException extends ExcepcionVehiculo {
    public VehiculoAccidentadoException(String mensaje) {
        super("🚨 Accidente: " + mensaje);
    }
}