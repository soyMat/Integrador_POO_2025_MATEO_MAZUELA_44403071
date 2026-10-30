package estado;

import modelo.Cursada;

public class Promocionada extends EstadoCursada {
    @Override
    public String getNombre() {
        return "Promocionada";
    }

    @Override
    public boolean tieneCursadaAprobada() {
        return true;
    }

    @Override
    public boolean tieneFinalAprobado(Cursada cursada) {
        return true;
    }
}
