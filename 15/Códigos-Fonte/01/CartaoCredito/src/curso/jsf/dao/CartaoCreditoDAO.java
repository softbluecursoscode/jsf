package curso.jsf.dao;

import java.util.List;

import javax.persistence.Query;

import curso.jsf.model.CartaoCredito;

/**
 * Classe DAO da entidade CartaoCredito
 */
public class CartaoCreditoDAO extends DAO {

	/**
	 * Obtém a lista dos cartões cadastrados
	 */
	@SuppressWarnings("unchecked")
	public List<CartaoCredito> listarCartoesCredito() {
		Query q = criarQuery("SELECT c FROM CartaoCredito c ORDER BY c.bandeira, c.dataVencimento");
		return q.getResultList();
	}
	
	/**
	 * Verifica se existe um cartão de crédito. O parâmetro cartaoCreditoId pode ser null. Caso seja fornecido um valor,
	 * o método verifica se existe outro cartão cadastrado com um ID diferente do fornecido
	 */
	public boolean existeCartaoCredito(String numero, Integer cartaoCreditoId) {
		String query = "SELECT COUNT(c) FROM CartaoCredito C WHERE c.numero = '" + numero + "'";
		
		if (cartaoCreditoId != null) {
			query += " AND c.id != " + cartaoCreditoId;
		}
		
		Query q = criarQuery(query);
		long count = (Long) q.getResultList().get(0);
		return count > 0;
	}
}
