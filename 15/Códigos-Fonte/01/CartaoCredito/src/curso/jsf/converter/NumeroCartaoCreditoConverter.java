package curso.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Conversor customizado para números de cartão de crédito
 */
@FacesConverter("converter.NumeroCartaoCredito")
public class NumeroCartaoCreditoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		// Este método não precisa ser implementado porque não é usado, já que o dado a ser 
		// convertido não é usado em formulários
		return null;
	}

	/**
	 * Converte o número do cartão para uma representação onde apenas os 4 primeiros dígitos são exibidos
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String numero = (String) value;
		
		String inicio = numero.substring(0, 4);
		String secreto = "****";
		
		return inicio + " " + secreto + " " + secreto + " " + secreto;
	}
}
