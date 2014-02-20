package exceptions;

public class NonUniqueReferenceException extends AppException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public NonUniqueReferenceException(Exception excecao, String codigoErro, Object... parametros) {
        super(excecao, codigoErro, parametros);
    }

    public NonUniqueReferenceException(String codigo, Object... parametros) {
        super(codigo, parametros);
    }

}
