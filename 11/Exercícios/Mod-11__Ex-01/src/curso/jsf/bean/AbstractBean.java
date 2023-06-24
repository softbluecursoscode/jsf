package curso.jsf.bean;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Superclasse de todos os CDI beans da aplicação
 */
public class AbstractBean implements Serializable {

	/**
	 * Adiciona uma mensagem na request
	 */
	protected void addMessage(String message) {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		request.setAttribute("msg", message);
	}
}
