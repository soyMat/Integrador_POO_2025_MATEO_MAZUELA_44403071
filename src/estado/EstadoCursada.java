package estado;

import modelo.Cursada;
import modelo.ExcepcionTransicionInvalida;

public abstract class EstadoCursada {
    public abstract String getNombre();

    public EstadoCursada aprobarParcial(Cursada cursada) {
        throw operacionInvalida("aprobar el parcial");
    }

    public EstadoCursada desaprobarParcial(Cursada cursada) {
        throw operacionInvalida("desaprobar el parcial");
    }

    public EstadoCursada aprobarCursada(Cursada cursada) {
        throw operacionInvalida("aprobar la cursada");
    }

    public EstadoCursada promocionar(Cursada cursada) {
        throw operacionInvalida("promocionar la materia");
    }

    public EstadoCursada desaprobarCursada(Cursada cursada) {
        throw operacionInvalida("desaprobar la cursada");
    }

    public void aprobarFinal(Cursada cursada) {
        throw operacionInvalida("aprobar el final");
    }

    public boolean tieneCursadaAprobada() {
        return false;
    }

    public boolean tieneFinalAprobado(Cursada cursada) {
        return false;
    }

    public boolean permiteRecursar() {
        return false;
    }

    private ExcepcionTransicionInvalida operacionInvalida(String operacion) {
        return new ExcepcionTransicionInvalida(
                "No se puede " + operacion + " desde el estado " + getNombre());
    }
}
