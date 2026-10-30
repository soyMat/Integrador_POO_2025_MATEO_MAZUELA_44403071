package estado;

import modelo.Cursada;
import modelo.ReglaDeNegocioException;

public class ParcialAprobado extends EstadoCursada {
    @Override
    public String getNombre() {
        return "Parcial aprobado";
    }

    @Override
    public EstadoCursada aprobarCursada(Cursada cursada) {
        return new CursadaAprobada();
    }

    @Override
    public EstadoCursada promocionar(Cursada cursada) {
        if (!cursada.getMateria().permitePromocion()) {
            throw new ReglaDeNegocioException("La materia no permite promocion");
        }
        return new Promocionada();
    }

    @Override
    public EstadoCursada desaprobarCursada(Cursada cursada) {
        return new CursadaDesaprobada();
    }
}
