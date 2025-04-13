package autonoma.Simulador.models.excepciones;


public class VehiculoApagadoException extends ExcepcionVehiculo {
    public VehiculoApagadoException() {
        super("No se puede realizar esta acción porque el vehiculo esta apagado");
    }
}