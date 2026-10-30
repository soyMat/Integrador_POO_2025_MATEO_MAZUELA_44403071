package modelo;

import estado.EstadoCursada;
import estado.Inscripto;

public class Cursada {
    private final Materia materia;
    private EstadoCursada estado;
    private boolean finalAprobado;

    public Cursada(Materia materia) {
        if (materia == null) {
            throw new ReglaDeNegocioException("La materia de la cursada es obligatoria");
        }
        this.materia = materia;
        this.estado = new Inscripto();
    }

    public void aprobarParcial() {
        estado = estado.aprobarParcial(this);
    }

    public void desaprobarParcial() {
        estado = estado.desaprobarParcial(this);
    }

    public void aprobarCursada() {
        estado = estado.aprobarCursada(this);
    }

    public void promocionar() {
        estado = estado.promocionar(this);
    }

    public void desaprobarCursada() {
        estado = estado.desaprobarCursada(this);
    }

    public void aprobarFinal() {
        estado.aprobarFinal(this);
        finalAprobado = true;
    }

    public boolean tieneCursadaAprobada() {
        return estado.tieneCursadaAprobada();
    }

    public boolean tieneFinalAprobado() {
        return estado.tieneFinalAprobado(this);
    }

    public boolean permiteRecursar() {
        return estado.permiteRecursar();
    }

    public Materia getMateria() {
        return materia;
    }

    public String getNombreEstado() {
        return estado.getNombre();
    }

    public boolean finalAprobadoRegistrado() {
        return finalAprobado;
    }

    @Override
    public String toString() {
        String detalleFinal = tieneFinalAprobado() ? " - final aprobado" : "";
        return materia + " - " + getNombreEstado() + detalleFinal;
    }
}
