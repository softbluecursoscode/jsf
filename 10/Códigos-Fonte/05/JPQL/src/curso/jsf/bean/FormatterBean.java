package curso.jsf.bean;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class FormatterBean implements Serializable {

	public String formatarMoeda(double valor) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		return nf.format(valor);
	}
}
