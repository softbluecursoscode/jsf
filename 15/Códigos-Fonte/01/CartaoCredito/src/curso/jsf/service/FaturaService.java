package curso.jsf.service;

import java.util.List;

import javax.inject.Inject;

import curso.jsf.dao.CartaoCreditoDAO;
import curso.jsf.dao.FaturaDAO;
import curso.jsf.dao.LancamentoDAO;
import curso.jsf.model.CartaoCredito;
import curso.jsf.model.Compra.Tipo;
import curso.jsf.model.Fatura;
import curso.jsf.model.Lancamento;
import curso.jsf.util.DateUtils;

/**
 * Classe de neg�cio com opera��es envolvendo cart�es
 */
public class FaturaService extends Service {

	@Inject
	private FaturaDAO faturaDAO;
	
	@Inject
	private LancamentoDAO lancamentoDAO;
	
	@Inject
	private CartaoCreditoDAO cartaoCreditoDAO;

	/**
	 * Fecha a fatura do cart�o
	 */
	public void fecharFatura(Integer cartaoCreditoId, Integer mes, Integer ano, List<Integer[]> lancamentosIds) throws ValidationException {
		try {
			beginTransaction();
			
			// Verifica se j� existe uma fatura fechada
			if (faturaDAO.existeFatura(cartaoCreditoId, mes, ano)) {
				throw new ValidationException("J� existe uma fatura deste cart�o cadastrada para o m�s/ano escolhido");
			}
			
			Fatura fatura = new Fatura();
			
			// Carrega o cart�o
			CartaoCredito cartaoCredito = cartaoCreditoDAO.carregar(cartaoCreditoId, CartaoCredito.class);
			fatura.setCartaoCredito(cartaoCredito);
			
			// A data de vencimento da fatura � uma combina��o da data de vencimento do cart�o com o 
			// m�s/ano de fechamento
			int diaVenc = cartaoCredito.getDiaVencimentoFatura();
			String dataStr = String.format("%02d/%02d/%d", diaVenc, mes, ano);
			fatura.setDataVencimento(DateUtils.createDate(dataStr, "dd/MM/yyyy"));
			
			// Inicialmente, define a fatura como tendo valor 0.00
			fatura.setValor(0.0);
			
			// Grava a fatura
			faturaDAO.salvar(fatura);
			
			double valorFatura = 0.0;
			
			// Itera sobre os IDs de lan�amentos que far�o parte da fatura
			for (Integer[] id : lancamentosIds) {
				Integer compraId = id[0];
				Integer numParcela = id[1];
				
				// Carrega o lan�amento
				Lancamento lancamento = lancamentoDAO.carregarLancamento(compraId, numParcela);
				
				// Acumula o valor da fatura. Soma ou subtrai, dependendo do tipo do lan�amento
				if (lancamento.getCompra().getTipo() == Tipo.DEBITO) {
					valorFatura += lancamento.getValor();
				} else if (lancamento.getCompra().getTipo() == Tipo.CREDITO) {
					valorFatura -= lancamento.getValor();
				}
				
				lancamento.setFatura(fatura);
			}
			
			fatura.setValor(valorFatura);
						
			commitTransaction();
			
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	/**
	 * Obt�m a lista de faturas cadastradas para o cart�o
	 */
	public List<Fatura> listarFaturas(Integer cartaoCreditoId) {
		return faturaDAO.listarFaturas(cartaoCreditoId);

	}
	
	/**
	 * Carrega a fatura com base no ID
	 */
	public Fatura carregar(Integer faturaId) {
		return faturaDAO.carregar(faturaId, Fatura.class);
	}
}
