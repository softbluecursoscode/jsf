package curso.jsf.validator;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("dateValidator")
public class DateValidator implements Validator {
	
	private String msg;

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		Date dataNasc = (Date) value;
		Date now = new Date();
		
		if (dataNasc != null && dataNasc.compareTo(now) > 0) {
			FacesMessage msg = new FacesMessage(this.msg);
			throw new ValidatorException(msg);
		}
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
