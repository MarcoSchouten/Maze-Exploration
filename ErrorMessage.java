
/**
 * il suo compito è gestire i messaggi di errore
 * 
 * @author Marco Schouten
 *
 */
public class ErrorMessage extends Error {
    private static final long serialVersionUID = 1L;

    public ErrorMessage(String message) {
	super(message);
    }

}
