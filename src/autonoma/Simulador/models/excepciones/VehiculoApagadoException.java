/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.Simulador.models.excepciones;

/**
 *
 * @author Asus
 */
public class VehiculoApagadoException extends ExcepcionVehiculo {
    public VehiculoApagadoException() {
        super("No se puede realizar esta acción porque el vehículo está apagado.");
    }
}