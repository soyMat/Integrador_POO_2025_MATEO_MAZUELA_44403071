package modelo;

public class ExcepcionTransicionInvalida extends ReglaDeNegocioException {
    public ExcepcionTransicionInvalida(String mensaje) {
        super(mensaje);
    }
}
