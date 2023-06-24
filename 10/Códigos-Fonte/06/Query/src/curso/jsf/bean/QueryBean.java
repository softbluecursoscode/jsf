package curso.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Named
@RequestScoped
public class QueryBean implements Serializable {
	
	@PersistenceContext
	private EntityManager em;
	
	private String query;
	
	private List<String> results;

	public String executar() {
		
		Query q = null;
		try {
			q = em.createQuery(query);
		
		} catch (IllegalArgumentException e) {
			FacesMessage msg = new FacesMessage("A query é inválida");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
		
		List<?> results = q.getResultList();
		
		this.results = new ArrayList<String>();
		for (Object result : results) {
			
			if (result instanceof Object[]) {
				Object[] array = (Object[]) result;
				StringBuilder sb = new StringBuilder();
				for (Object e : array) {
					sb.append("{ ").append(e).append(" } ");
				}
				this.results.add(sb.toString());
			} else {
				this.results.add(result.toString());
			}
		}
		
		return null;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<String> getResults() {
		return results;
	}

	public void setResults(List<String> results) {
		this.results = results;
	}
}
