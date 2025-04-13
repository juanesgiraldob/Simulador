package autonoma.Simulador.models.componentes;

public abstract class Motor {
    protected int velocidadMaxima;

    public int getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public abstract int getCilindraje();
}


