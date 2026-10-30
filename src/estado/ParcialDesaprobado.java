package estado;

import modelo.Cursada;

public class ParcialDesaprobado extends EstadoCursada {
    @Override
    public String getNombre() {
        return "Parcial desaprobado";
    }

    @Override
    public EstadoCursada aprobarParcial(Cursada cursada) {
        return new ParcialAprobado();
    }

    @Override
    public EstadoCursada desaprobarCursada(Cursada cursada) {
        return new CursadaDesaprobada();
    }
}
