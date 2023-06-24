package curso.jsf.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import curso.jsf.util.StringUtils;

/**
 * Validador customizado que valida se o n�mero do cart�o � v�lido
 */
@FacesValidator("validator.NumeroCartaoCredito")
public class NumeroCartaoCreditoValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		String numero = (String) value;
		
		if (numero == null) {
			return;
		}
		
		// Tira os espa�os em branco do n�mero
		numero = numero.trim();
		
		// Verifica se o n�mero � composto por 16 caracteres
		if (numero.length() != 16) {
			FacesMessage msg = new FacesMessage("O n�mero do cart�o de cr�dito deve possuir 16 d�gitos num�ricos.");
			throw new ValidatorException(msg);
		}
		
		// Verifica se todos os caracteres s�o num�ricos
		if (!StringUtils.isNumeric(numero)) {
			FacesMessage msg = new FacesMessage("O n�mero digitado n�o � um n�mero v�lido.");
			throw new ValidatorException(msg);
		}
	}
}
