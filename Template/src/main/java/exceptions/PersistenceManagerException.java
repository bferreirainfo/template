package exceptions;

public class PersistenceManagerException extends AppException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public PersistenceManagerException(Exception causa, Object... parametros) {
        super(causa, causa.getMessage(), parametros);
    }

    public PersistenceManagerException(Exception causa, String message) {
        super(causa, message);
    }

    public PersistenceManagerException(String message, Object... parametros) {
        super(null, message, parametros);
    }

}