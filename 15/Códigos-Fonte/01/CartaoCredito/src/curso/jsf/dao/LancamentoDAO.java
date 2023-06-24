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
	 * Carrega o lan�amento a partir de um ID de compra e n�mero de parcela
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
	 * Pesquisa lan�amentos abertos (sem fatura) para um cart�o at� o m�s/ano fornecido.
	 * Se m�s/ano forem null, lista todos os lan�amentos abertos, sem filtrar pela data
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
			// Se um m�s/ano for fornecido, cria um Calendar para compara��o de datas
			Calendar calendar = Calendar.getInstance();
			calendar.set(ano, mes, DateUtils.getMaxDays(mes, ano));
			q.setParameter("data", calendar, TemporalType.DATE);
		}
		
		return q.getResultList();
	}
	
	/**
	 * Obt�m a lista de lan�amentos de uma compra
	 */
	@SuppressWarnings("unchecked")
	public List<Lancamento> listarLancamentosCompra(Integer compraId) {
		Query q = criarQuery("SELECT l FROM Lancamento l WHERE l.compra.id = " + compraId);
		return q.getResultList();
	}
	
	/**
	 * Obt�m a lista de lan�amentos de uma fatura
	 */
	@SuppressWarnings("unchecked")
	public List<Lancamento> listarLancamentosFatura(Integer faturaId) {
		Query q = criarQuery("SELECT l FROM Lancamento l WHERE l.fatura.id = " + faturaId);
		return q.getResultList();
	}
	
	/**
	 * Soma os valores dos lan�amentos abertos de um cart�o
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
