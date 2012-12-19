package es.steria.rest.excepciones;

public class DAOLogException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    
    public DAOLogException(String mensaje) {
	super(mensaje);
    }
}
