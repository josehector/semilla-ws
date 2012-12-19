package es.steria.rest.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Ocurrio un error en el servidor")
public class InternalServerErrorException extends RuntimeException{

    private static final long serialVersionUID = -5203458855176747888L;

}
