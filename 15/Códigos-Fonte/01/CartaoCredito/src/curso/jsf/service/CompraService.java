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

			// A data da primeira parcela � a data da compra
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

				// A data da pr�xima parcela ser� 1 m�s adiante
				calendar.add(Calendar.MONTH, 1);
			}

			commitTransaction();
		
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Ot�m a lista de lan�amentos abertos (sem fatura) para o cart�o
	 */
	public List<Lancamento> pesquisarLancamentos(Integer cartaoCreditoId) {
		return pesquisarLancamentos(null, null, cartaoCreditoId);
	}
	
	/**
	 * Obt�m a lista de lan�amentos abertos (sem fatura) para o cart�o at� um m�s/ano
	 */
	public List<Lancamento> pesquisarLancamentos(Integer mes, Integer ano, Integer cartaoCreditoId) {
		return lancamentoDAO.pesquisarLancamentos(mes, ano, cartaoCreditoId);
	}
	
	/**
	 * Calcula a soma dos valores dos lan�amentos
	 */
	public double calcularValorTotal(List<Lancamento> lancamentos) {
		double total = 0.0;
		
		// Itera sobre os lan�amentos, acumulando o valor total
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
	 * Calcula o valor restante dispon�vel no cart�o
	 */
	public double calcularValorDisponivel(Integer cartaoCreditoId) {
		// Obt�m a soma total de valores dos lan�amentos abertos do cart�o
		double totalGasto = lancamentoDAO.somarLancamentosAbertos(cartaoCreditoId);
		
		// Obt�m os dados do cart�o
		CartaoCredito cartaoCredito = cartaoCreditoDAO.carregar(cartaoCreditoId, CartaoCredito.class);
		double limite = cartaoCredito.getLimite();
		
		// Subtrai o total gasto do limite do cart�o
		return limite - totalGasto;
	}
	
	/**
	 * Exclui uma compra e todos os lan�amentos associados
	 */
	public void excluirCompraELancamentos(Integer compraId) {
		try {
			beginTransaction();
		
			// Obt�m a lista de lan�amentos da compra
			List<Lancamento> lancamentos = lancamentoDAO.listarLancamentosCompra(compraId);
			
			// Exclui cada um dos lan�amentos
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
	 * Obt�m a lista de lan�amentos associados a uma fatura
	 */
	public List<Lancamento> listarLancamentosFatura(Integer faturaId) {
		return lancamentoDAO.listarLancamentosFatura(faturaId);
	}
}
