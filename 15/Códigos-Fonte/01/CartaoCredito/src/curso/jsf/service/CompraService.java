package curso.jsf.service;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import curso.jsf.dao.CartaoCreditoDAO;
import curso.jsf.dao.CompraDAO;
import curso.jsf.dao.LancamentoDAO;
import curso.jsf.model.CartaoCredito;
import curso.jsf.model.Compra;
import curso.jsf.model.Compra.Tipo;
import curso.jsf.model.Lancamento;

public class CompraService extends Service {

	@Inject
	private LancamentoDAO lancamentoDAO;

	@Inject
	private CompraDAO compraDAO;
	
	@Inject
	private CartaoCreditoDAO cartaoCreditoDAO;

	/**
	 * Cadastra uma compra
	 */
	public void registrarCompra(Compra compra) {
		try {
			beginTransaction();

			// Salva a compra
			compraDAO.salvar(compra);

			int numParcelas = compra.getNumParcelas();
			double valorTotal = compra.getValor();
			double valorParcela = valorTotal / numParcelas;

			// A data da primeira parcela é a data da compra
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(compra.getData());

			// Salva uma parcela para cada parcela existente na compra
			for (int i = 1; i <= numParcelas; i++) {
				Lancamento lancamento = new Lancamento();
				lancamento.setCompra(compra);
				lancamento.setNumParcela(i);
				lancamento.setData(calendar.getTime());
				lancamento.setValor(valorParcela);
				lancamentoDAO.salvar(lancamento);

				// A data da próxima parcela será 1 mês adiante
				calendar.add(Calendar.MONTH, 1);
			}

			commitTransaction();
		
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Otém a lista de lançamentos abertos (sem fatura) para o cartão
	 */
	public List<Lancamento> pesquisarLancamentos(Integer cartaoCreditoId) {
		return pesquisarLancamentos(null, null, cartaoCreditoId);
	}
	
	/**
	 * Obtém a lista de lançamentos abertos (sem fatura) para o cartão até um mês/ano
	 */
	public List<Lancamento> pesquisarLancamentos(Integer mes, Integer ano, Integer cartaoCreditoId) {
		return lancamentoDAO.pesquisarLancamentos(mes, ano, cartaoCreditoId);
	}
	
	/**
	 * Calcula a soma dos valores dos lançamentos
	 */
	public double calcularValorTotal(List<Lancamento> lancamentos) {
		double total = 0.0;
		
		// Itera sobre os lançamentos, acumulando o valor total
		for (Lancamento lancamento : lancamentos) {
			if (lancamento.getCompra().getTipo() == Tipo.DEBITO) {
				total += lancamento.getValor();
			} else if (lancamento.getCompra().getTipo() == Tipo.CREDITO) {
				total -= lancamento.getValor();
			} 
		}
		return total;
	}
	
	/**
	 * Calcula o valor restante disponível no cartão
	 */
	public double calcularValorDisponivel(Integer cartaoCreditoId) {
		// Obtém a soma total de valores dos lançamentos abertos do cartão
		double totalGasto = lancamentoDAO.somarLancamentosAbertos(cartaoCreditoId);
		
		// Obtém os dados do cartão
		CartaoCredito cartaoCredito = cartaoCreditoDAO.carregar(cartaoCreditoId, CartaoCredito.class);
		double limite = cartaoCredito.getLimite();
		
		// Subtrai o total gasto do limite do cartão
		return limite - totalGasto;
	}
	
	/**
	 * Exclui uma compra e todos os lançamentos associados
	 */
	public void excluirCompraELancamentos(Integer compraId) {
		try {
			beginTransaction();
		
			// Obtém a lista de lançamentos da compra
			List<Lancamento> lancamentos = lancamentoDAO.listarLancamentosCompra(compraId);
			
			// Exclui cada um dos lançamentos
			for (Lancamento lancamento : lancamentos) {
				lancamento.setCompra(null);
				lancamentoDAO.excluir(lancamento);
			}
			
			Compra compra = compraDAO.carregar(compraId, Compra.class);
			
			// Exclui a compra
			compraDAO.excluir(compra);
			
			commitTransaction();
		
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	/**
	 * Obtém a lista de lançamentos associados a uma fatura
	 */
	public List<Lancamento> listarLancamentosFatura(Integer faturaId) {
		return lancamentoDAO.listarLancamentosFatura(faturaId);
	}
}
