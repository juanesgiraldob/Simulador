
package autonoma.Simulador.models.excepciones;

public class VehiculoPatinaException extends ExcepcionVehiculo {
    public VehiculoPatinaException(String mensaje) {
        super("⚠️ El vehiculo esta patinando: " + mensaje);
    }
}