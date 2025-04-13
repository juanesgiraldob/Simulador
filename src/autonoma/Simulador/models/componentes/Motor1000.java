/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.Simulador.models.componentes;

/**
 *
 * @author Asus
 */

public class Motor1000 extends Motor {

    public Motor1000() {
        this.velocidadMaxima = 100;
    }

    @Override
    public int getCilindraje() {
        return 1000;
    }
}
