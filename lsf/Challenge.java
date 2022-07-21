package lsf;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Arnaud Lesaffre
 * @version 1.0 - JavaSE-1.8
 */
public class Challenge {
	// Attributes
	// ==========
	private static final Map<Integer, String> number = new HashMap<>();
	private static final String[] cinq = new String[] {"pile",
			"cinq", "dix", "et quart",
			"vingt", "vingt cinq", "et demi",
			"moins vingt cinq", "moins vingt", "moins le quart",
			"moins dix", "moins cinq"};
	private String letterTime;
	
	// Initialisations
	// ===============
	static {
		number.put(1, "une");
		number.put(2, "deux");
		number.put(3, "trois");
		number.put(4, "quatre");
		number.put(5, "cinq");
		number.put(6, "six");
		number.put(7, "sept");
		number.put(8, "huit");
		number.put(9, "neuf");
		number.put(10, "dix");
		number.put(11, "onze");
	}
	
	// Constructors
	// ============
	/**
	 * Instancie un objet Challenge et convertit en toutes lettres, l'heure passée en paramètre.
	 * @param heure
	 * 		Haure au format hh:mm sur un horodatage de 24h.
	 */
	public Challenge(String heure) {
		this.letterTime = "";
		String[] segment = heure.split(":", -1);
		boolean pm = false; // Indicateur après midi.
		boolean sp = false; // Indicateur deuxième moitié d'heure.
		boolean multiple = false; // Indicateur minutes multiples de cinq.
		try {
			if(segment.length != 2) {
				throw new HourException("Le format n'est pas respecté.");
			}
			int hour = Integer.parseInt(segment[0]);
			int minute = Integer.parseInt(segment[1]);
			this.letterTime = "Il est ";
			if(hour < 0 || hour > 23) { // Heures hors bornes
				throw new HourException("Les heures ne peuvent être négatives ou supérieures à 23.");
			}
			if(minute < 0 || minute > 59) { // minutes hors bornes
				throw new HourException("Les minutes ne peuvent être négatives ou supérieures à 59.");
			}
			if(minute > 30) { // Deuxième moitié d'heure.
				sp = true;
			}
			if(minute % 5 == 0) { // Les minutes sont multiples de cinq.
				multiple = true;
			}
			if(multiple && sp) { // Si multiple de cinq et seconde demie heure => heure sup avec indicateur "moins".
				hour++;
			}
			if(hour > 11 ) { // horodatage 24h ramené à 12h.
				pm = true;
				hour-= 12;
			}
			if(hour == 0) {
				this.letterTime += ((pm) ? "midi" : "minuit") + " ";
			} else {
				this.letterTime += number.get(hour) + " heure" + ((hour == 1) ? "" : "s") + " ";
			}
			if(multiple) { // Multiple de cinq, donc autre attribution des minutes
				this.letterTime+= cinq[minute / 5];
			} else { // Sinon, indication des minutes en toutes lettres.
				int decade = minute / 10;
				if(decade == 1 && minute % 10 > 6) {
					this.letterTime+= "dix-";
				} else if(decade == 2) {
					this.letterTime+= "vingt-";
				} else if(decade == 3) {
					this.letterTime+= "trente-";
				} else if(decade == 4) {
					this.letterTime+= "quarante-";
				} else if(decade == 5) {
					this.letterTime+= "cinquante-";
				}
				if(minute > 16 && minute % 10 == 1) { // On ajoute le "et" d'union de l'unité
					this.letterTime+= "et-";
				}
				if(minute < 10 || minute > 16) { // On ajoute l'unité de minute
					this.letterTime+= number.get(minute % 10);
				} else {
					switch(minute) {
					case 11:
						this.letterTime+= "onze";
						break;
					case 12:
						this.letterTime+= "douze";
						break;
					case 13:
						this.letterTime+= "treize";
						break;
					case 14:
						this.letterTime+= "quatorze";
						break;
					case 15:
						this.letterTime+= "quinze";
						break;
					case 16:
						this.letterTime+= "seize";
						break;
					default:
						break;
					}
				}
			}
			if(hour != 0) { // Différent de minuit ou midi
				this.letterTime+= (pm) ? " de l'après-midi" : "";
			}
			this.letterTime+= "."; // On termine par une ponctuation.
		} catch (Exception exception) {
			this.letterTime = exception.getMessage();
		}
	}

	// Getters
	// =======
	/**
	 * Accesseur à l'heure convertie.
	 * @return
	 * 		Renvoie l'heure convertie en toutes lettre.
	 */
	public String getLetterTime() {
		return this.letterTime;
	}
	
	// Entry
	// =====
	/**
	 * Point d'entrée dans l'application.
	 * @param args
	 * 		liste des heures à convertir.
	 */
	public static void main(String[] args) {
		for(String arg : args) {
			System.out.println(arg + " => " + new Challenge(arg).getLetterTime());
		}
	}
}
