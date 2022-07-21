package lsf;

/**
 * Classe définie pour gérer le mauvais format de l'heure.
 * @author Arnaud Lesaffre
 * @version 1.0 - JavaSE-1.8
 */
public class HourException extends Exception {
	private static final long serialVersionUID = 8238980608836117013L;
	public HourException(String exception) {
		super(exception);
	}
}
