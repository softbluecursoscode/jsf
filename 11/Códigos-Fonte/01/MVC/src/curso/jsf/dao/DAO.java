package curso.jsf.dao;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Superclasse das classes de DAO (Data Access Object) da aplicação
 */
@RequestScoped
public abstract class DAO implements Serializable {
	
	/**
	 * EntityManager para interagir com a JPA
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Carrega uma entidade com base na chave primária
	 */
	public <T> T carregar(Object key, Class<T> clazz) {
		return em.find(clazz, key);
	}

	/**
	 * Persiste uma entidade
	 */
	public <T> void salvar(T entity) {
		em.persist(entity);
	}
	
	/**
	 * Altera uma entidade existente
	 */
	public <T> void alterar(T entity) {
		em.merge(entity);
	}
	
	/**
	 * Exclui uma entidade
	 */
	public <T> void excluir(T entity) {
		em.remove(entity);
	}
	
	/**
	 * Cria um objeto Query para uma query JPQL
	 */
	protected Query criarQuery(String query) {
		return em.createQuery(query);
	}
}
