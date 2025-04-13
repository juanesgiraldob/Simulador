/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.Simulador.models.excepciones;

/**
 *
 * @author Asus
 */
public class VehiculoAccidentadoException extends ExcepcionVehiculo {
    public VehiculoAccidentadoException(String mensaje) {
        super("🚨 Accidente: " + mensaje);
    }
}
