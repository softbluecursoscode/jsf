package curso.jsf;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("page")
@RequestScoped
public class PageBean implements Serializable {

	private String email = "abc@abc.com";

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
