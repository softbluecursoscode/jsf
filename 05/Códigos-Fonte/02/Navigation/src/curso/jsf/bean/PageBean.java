package curso.jsf.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class PageBean implements Serializable {
	
	public enum Pages {
		page_a,
		page_b
	}

	private String page;

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
	public Pages goTo() {
		if ("a".equals(page)) {
			return Pages.page_a;
		} else if ("b".equals(page)) {
			return Pages.page_b;
		} else {
			return null;
		}
	}
}
