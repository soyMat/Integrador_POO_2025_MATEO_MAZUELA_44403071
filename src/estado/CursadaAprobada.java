package estado;

import modelo.Cursada;

public class CursadaAprobada extends EstadoCursada {
    @Override
    public String getNombre() {
        return "Cursada aprobada";
    }

    @Override
    public void aprobarFinal(Cursada cursada) {
        // El contexto registra el resultado luego de validar el estado.
    }

    @Override
    public boolean tieneCursadaAprobada() {
        return true;
    }

    @Override
    public boolean tieneFinalAprobado(Cursada cursada) {
        return cursada.finalAprobadoRegistrado();
    }
}
