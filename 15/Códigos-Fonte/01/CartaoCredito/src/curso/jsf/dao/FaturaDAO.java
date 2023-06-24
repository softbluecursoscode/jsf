package curso.jsf.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import curso.jsf.model.Fatura;
import curso.jsf.util.DateUtils;

/**
 * Classe DAO da entidade Fatura
 */
public class FaturaDAO extends DAO {
	
	/**
	 * Obtém uma lista de faturas cadastradas para um cartão
	 */
	@SuppressWarnings("unchecked")
	public List<Fatura> listarFaturas(Integer cartaoCreditoId) {
		Query q = criarQuery("SELECT f FROM Fatura f ORDER BY f.dataVencimento DESC");
		q.setMaxResults(5);
		return q.getResultList();
	}
	
	/**
	 * Verifica se já existe uma fatura cadastrada para o cartão em determinado mês/ano
	 */
	public boolean existeFatura(Integer cartaoCreditoId, Integer mes, Integer ano) {
		
		Calendar dataInicio = Calendar.getInstance();
		dataInicio.set(ano, mes, 1);
		
		Calendar dataFim = Calendar.getInstance();
		dataFim.set(ano, mes, DateUtils.getMaxDays(mes, ano));
		
		Query q = criarQuery("SELECT COUNT(f) FROM Fatura f WHERE f.cartaoCredito.id = " + cartaoCreditoId + " AND f.dataVencimento >= :dataInicio AND f.dataVencimento <= :dataFim ");
		q.setParameter("dataInicio", dataInicio, TemporalType.DATE);
		q.setParameter("dataFim", dataFim, TemporalType.DATE);
		
		long count = (Long) q.getResultList().get(0);
		return count > 0;
	}
}
