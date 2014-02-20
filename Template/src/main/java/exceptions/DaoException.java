package exceptions;

public class DaoException extends AppException {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6861731352555981651L;

    /**
     * Construtor canonico.
     * 
     * @param exception Exceção causadora (opicional)
     * @param codigoErro a chave da mensgem de erro no arquivo properties de mensagens (mandatorio)
     * @param parametros um array (possivelmente nulo) de possiveis parametros para substituicao na
     *        mensagem. (opicional)
     */
    public DaoException(Exception excecao, String codigoErro, Object... parametros) {
        super(excecao, codigoErro, parametros);
    }

    /**
     * Construtor sem excecao causadora.
     * 
     * @param codigoErro a chave da mensgem de erro no arquivo properties de mensagens (mandatorio)
     * @param parametros um array (possivelmente nulo) de possiveis parametros para substituicao na
     *        mensagem. (opicional)
     */
    public DaoException(String codigo, Object... parametros) {
        super(codigo, parametros);
    }

}