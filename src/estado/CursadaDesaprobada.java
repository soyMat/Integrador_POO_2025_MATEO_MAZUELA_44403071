package estado;

public class CursadaDesaprobada extends EstadoCursada {
    @Override
    public String getNombre() {
        return "Cursada desaprobada";
    }

    @Override
    public boolean permiteRecursar() {
        return true;
    }
}
