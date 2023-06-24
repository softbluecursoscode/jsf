package curso.jsf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Utilit�rios de data
 */
public class DateUtils {

	/**
	 * Obt�m o m�s de um objeto Date
	 */
	public static Integer getMes(Date date) {
		return get(Calendar.MONTH, date) + 1;
	}
	
	/**
	 * Obt�m o ano de um objeto Date
	 */
	public static Integer getAno(Date date) {
		return get(Calendar.YEAR, date);
	}
	
	/**
	 * Cria um objeto Date com base numa string que representa a data e um padr�o
	 */
	public static Date createDate(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * Retorna o n�mero de dias presentes em um m�s (28, 29, 30 ou 31)
	 */
	public static int getMaxDays(int month, int year) {
		switch(month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		
		case 2:
			return year % 4 == 0 ? 29 : 28;
			
		default:
			throw new IllegalArgumentException("O m�s " + month + " � inv�lido");
		}
	}
	
	/**
	 * Obt�m alguma informa��o da data a partir de um objeto Date
	 */
	private static Integer get(int field, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(field);
	}
}
