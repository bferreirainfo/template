package exceptions;

public class ObjectExistsException extends AppException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1771622381363448140L;

    public ObjectExistsException(Exception excecao, String codigoErro, Object... parametros) {
        super(excecao, codigoErro, parametros);
    }

    public ObjectExistsException(String codigo, Object... parametros) {
        super(codigo, parametros);
    }

    public ObjectExistsException(Object objeto) {
        super(objeto.getClass().getName(), objeto);
    }

    public Object getObjeto() {
        Object[] parametros = super.getParametros();
        return parametros == null ? null : parametros[0];
    }

}