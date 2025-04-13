package autonoma.Simulador.models.componentes;

public class Motor2000 extends Motor {

    public Motor2000() {
        this.velocidadMaxima = 160;
    }

    @Override
    public int getCilindraje() {
        return 2000;
    }
}

