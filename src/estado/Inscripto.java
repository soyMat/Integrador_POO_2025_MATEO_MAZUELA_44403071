package estado;

import modelo.Cursada;

public class Inscripto extends EstadoCursada {
    @Override
    public String getNombre() {
        return "Inscripto";
    }

    @Override
    public EstadoCursada aprobarParcial(Cursada cursada) {
        return new ParcialAprobado();
    }

    @Override
    public EstadoCursada desaprobarParcial(Cursada cursada) {
        return new ParcialDesaprobado();
    }
}
