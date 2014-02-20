package exceptions;

public class ObjectNotFoundException extends AppException {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2694702059779736439L;

    public ObjectNotFoundException(Exception excecao, String codigoErro, Object... parametros) {
        super(excecao, codigoErro, parametros);
    }

    public ObjectNotFoundException(String codigo, Object... parametros) {
        super(codigo, parametros);
    }

}