/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.Simulador.models.componentes;

/**
 *
 * @author Asus
 */

public class Motor3000 extends Motor {

    public Motor3000() {
        this.velocidadMaxima = 220;
    }

    @Override
    public int getCilindraje() {
        return 3000;
    }
}

