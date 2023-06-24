package curso.jsf.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import curso.jsf.util.StringUtils;

/**
 * Validador customizado que valida se o número do cartão é válido
 */
@FacesValidator("validator.NumeroCartaoCredito")
public class NumeroCartaoCreditoValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		String numero = (String) value;
		
		if (numero == null) {
			return;
		}
		
		// Tira os espaços em branco do número
		numero = numero.trim();
		
		// Verifica se o número é composto por 16 caracteres
		if (numero.length() != 16) {
			FacesMessage msg = new FacesMessage("O número do cartão de crédito deve possuir 16 dígitos numéricos.");
			throw new ValidatorException(msg);
		}
		
		// Verifica se todos os caracteres são numéricos
		if (!StringUtils.isNumeric(numero)) {
			FacesMessage msg = new FacesMessage("O número digitado não é um número válido.");
			throw new ValidatorException(msg);
		}
	}
}
