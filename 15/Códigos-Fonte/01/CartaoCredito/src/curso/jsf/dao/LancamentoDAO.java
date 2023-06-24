package curso.jsf.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import curso.jsf.model.Lancamento;
import curso.jsf.util.DateUtils;

/**
 * Classe DAO da entidade Lancamento
 */
public class LancamentoDAO extends DAO {
	
	/**
	 * Carrega o lançamento a partir de um ID de compra e número de parcela
	 */
	@SuppressWarnings("unchecked")
	public Lancamento carregarLancamento(Integer compraId, int numParcela) {
		Query q = criarQuery("SELECT l FROM Lancamento l WHERE l.compra.id = " + compraId + " AND l.numParcela = " + numParcela);
		List<Lancamento> results = q.getResultList();
		
		if (results.size() == 0) {
			return null;
		}
		
		return results.get(0);
	}
	
	/**
	 * Pesquisa lançamentos abertos (sem fatura) para um cartão até o mês/ano fornecido.
	 * Se mês/ano forem null, lista todos os lançamentos abertos, sem filtrar pela data
	 */
	@SuppressWarnings("unchecked")
	public List<Lancamento> pesquisarLancamentos(Integer mes, Integer ano, Integer cartaoCreditoId) {
		
		String query = "SELECT l FROM Lancamento l WHERE l.fatura IS NULL AND l.compra.cartaoCredito.id = " + cartaoCreditoId;
		
		if (mes != null && ano != null) {	
			query += " AND l.data <= :data";
		}
		
		query += " ORDER BY l.data";
		
		Query q = criarQuery(query);
		
		if (mes != null && ano != null) {
			// Se um mês/ano for fornecido, cria um Calendar para comparação de datas
			Calendar calendar = Calendar.getInstance();
			calendar.set(ano, mes, DateUtils.getMaxDays(mes, ano));
			q.setParameter("data", calendar, TemporalType.DATE);
		}
		
		return q.getResultList();
	}
	
	/**
	 * Obtém a lista de lançamentos de uma compra
	 */
	@SuppressWarnings("unchecked")
	public List<Lancamento> listarLancamentosCompra(Integer compraId) {
		Query q = criarQuery("SELECT l FROM Lancamento l WHERE l.compra.id = " + compraId);
		return q.getResultList();
	}
	
	/**
	 * Obtém a lista de lançamentos de uma fatura
	 */
	@SuppressWarnings("unchecked")
	public List<Lancamento> listarLancamentosFatura(Integer faturaId) {
		Query q = criarQuery("SELECT l FROM Lancamento l WHERE l.fatura.id = " + faturaId);
		return q.getResultList();
	}
	
	/**
	 * Soma os valores dos lançamentos abertos de um cartão
	 */
	@SuppressWarnings("unchecked")
	public double somarLancamentosAbertos(Integer cartaoCreditoId) {
		Query q = criarQuery("SELECT SUM(l.valor) FROM Lancamento l WHERE l.compra.cartaoCredito.id = " + cartaoCreditoId + " AND l.fatura IS NULL");
		List<Double> results = q.getResultList();
		
		if (results.size() == 0) {
			return 0;
		}
		
		return results.get(0);
	}
}
