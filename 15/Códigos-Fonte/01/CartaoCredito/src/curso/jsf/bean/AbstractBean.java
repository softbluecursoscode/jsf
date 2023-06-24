package curso.jsf.bean;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Superclasse de todos os beans CDI
 */
public class AbstractBean implements Serializable {

	/**
	 * Adiciona uma mensagem ao escopo da request. Esta mensagem pode ser exibida na tela através da chamada
	 * requestParam.<message>.
	 */
	protected void addMessageToRequest(String message) {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		request.setAttribute("msg", message);
	}
	
	/**
	 * A partir de um outcome, retorna o outcome como sendo um redirect ao invés de forward
	 */
	protected String redirect(String outcome) {
		return outcome + "?faces-redirect=true";
	}
}
