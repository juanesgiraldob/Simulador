/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.Simulador.models.excepciones;

/**
 *
 * @author Asus
 */
public class VehiculoPatinaException extends ExcepcionVehiculo {
    public VehiculoPatinaException(String mensaje) {
        super("⚠️ El vehículo está patinando: " + mensaje);
    }
}