package curso.jsf;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("page")
@RequestScoped
public class PageBean implements Serializable {

	private int valor1;
	private int valor2;
	
	public PageBean() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		
		//HttpSession session = request.getSession();
		
		try {
			this.valor1 = Integer.parseInt(request.getParameter("v1"));
		} catch (NumberFormatException e) {
		}
		
		try {
			this.valor2 = Integer.parseInt(request.getParameter("v2"));
		} catch (NumberFormatException e) {
		}
	}

	public int getValor1() {
		return valor1;
	}

	public int getValor2() {
		return valor2;
	}

	public int getSoma() {
		return valor1 + valor2;
	}
}
