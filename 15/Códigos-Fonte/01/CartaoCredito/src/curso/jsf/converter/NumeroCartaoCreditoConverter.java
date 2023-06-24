package curso.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Conversor customizado para n�meros de cart�o de cr�dito
 */
@FacesConverter("converter.NumeroCartaoCredito")
public class NumeroCartaoCreditoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		// Este m�todo n�o precisa ser implementado porque n�o � usado, j� que o dado a ser 
		// convertido n�o � usado em formul�rios
		return null;
	}

	/**
	 * Converte o n�mero do cart�o para uma representa��o onde apenas os 4 primeiros d�gitos s�o exibidos
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String numero = (String) value;
		
		String inicio = numero.substring(0, 4);
		String secreto = "****";
		
		return inicio + " " + secreto + " " + secreto + " " + secreto;
	}
}
