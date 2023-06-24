package curso.jsf.util;

/**
 * Utilit�rios de Strings
 */
public class StringUtils {

	/**
	 * Verifica se uma String � composta apenas por caracteres num�ricos 
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		
		char[] chars = str.toCharArray();
		for (char c : chars) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		
		return true;
	}
}
