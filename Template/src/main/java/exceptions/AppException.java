package exceptions;

import java.io.Serializable;

public class AppException extends Exception implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3022746772959873175L;
    // parametros da mensagem de erro
    private Object[] parametros;

    /**
     * Construtor canonico.
     * 
     * @param exception Exceção causadora (opicional)
     * @param codigoErro a chave da mensgem de erro no arquivo properties de mensagens (mandatorio)
     * @param parametros um array (possivelmente nulo) de possiveis parametros para substituicao na
     *        mensagem. (opicional)
     */
    protected AppException(Exception excecao, String codigoErro, Object... parametros) {

        super(codigoErro, excecao);
        this.parametros = parametros;

    }

    /**
     * Variacao do construtor. Neste caso, nao ha uma excecao causadora. e a mensagem possui
     * parametros
     * 
     * @param codigoErro a chave da mensgem de erro no arquivo properties de mensagens (mandatorio)
     * @param parametros um array (possivelmente nulo) de possiveis parametros para substituicao na
     *        mensagem. (opicional)
     */
    protected AppException(String codigo, Object... parametros) {
        this(null, codigo, parametros);
    }

    public Object[] getParametros() {
        return this.parametros;
    }

}
