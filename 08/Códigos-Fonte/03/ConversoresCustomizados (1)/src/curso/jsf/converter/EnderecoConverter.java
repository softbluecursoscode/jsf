package curso.jsf.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import curso.jsf.model.Endereco;

@FacesConverter(forClass = Endereco.class)
public class EnderecoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String value) {
		if (value == null) {
			return null;
		}
		
		try {
			String[] tokens = value.split("\\,");
			Endereco e = new Endereco();
			e.setRua(tokens[0].trim());
			e.setNumero(tokens[1].trim());
			return e;
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("A conversão não pode ser feita");
			throw new ConverterException(message);
		}
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object value) {
		Endereco e = (Endereco) value;
		String s = e.getRua() + ", " + e.getNumero();
		return s;
	}

}
