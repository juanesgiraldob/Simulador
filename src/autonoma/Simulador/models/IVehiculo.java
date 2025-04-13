
package autonoma.Simulador.models;
import autonoma.Simulador.models.excepciones.ExcepcionVehiculo;

public interface IVehiculo {
    void encender() throws ExcepcionVehiculo;
    void apagar() throws ExcepcionVehiculo;
    void acelerar(int kmh) throws ExcepcionVehiculo;
    void frenar(int kmh) throws ExcepcionVehiculo;
    void frenadoBrusco(int kmh) throws ExcepcionVehiculo;
    void derrapar() throws ExcepcionVehiculo;
    void chocar() throws ExcepcionVehiculo;
    boolean isEncendido();
    int getVelocidad();
    String getTipoLlantas();
    int getCilindrajeMotor();
}