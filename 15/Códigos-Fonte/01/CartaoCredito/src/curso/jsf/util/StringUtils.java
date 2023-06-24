package curso.jsf.util;

/**
 * Utilitários de Strings
 */
public class StringUtils {

	/**
	 * Verifica se uma String é composta apenas por caracteres numéricos 
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
