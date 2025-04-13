package autonoma.Simulador.models;

import autonoma.Simulador.models.excepciones.ExcepcionVehiculo;
import autonoma.Simulador.models.componentes.Llantas;
import autonoma.Simulador.models.componentes.Motor;

public abstract class VehiculoBase implements IVehiculo {
    protected boolean encendido = false;
    protected int velocidad = 0;
    protected Llantas llantas;
    protected Motor motor;
    protected boolean patinando = false;
    protected boolean accidentado = false;

    public boolean isEncendido() { return encendido; }
    public int getVelocidad() { return velocidad; }
    public String getTipoLlantas() { return llantas.getTipo(); }
    public int getCilindrajeMotor() { return motor.getCilindraje(); }

    public abstract void encender() throws ExcepcionVehiculo;
    public abstract void apagar() throws ExcepcionVehiculo;
    public abstract void acelerar(int kmh) throws ExcepcionVehiculo;
    public abstract void frenar(int kmh) throws ExcepcionVehiculo;
    public abstract void frenadoBrusco(int kmh) throws ExcepcionVehiculo;
    public abstract void derrapar() throws ExcepcionVehiculo;
    public abstract void chocar() throws ExcepcionVehiculo;
}