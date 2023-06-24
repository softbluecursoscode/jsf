package curso.jsf.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import curso.jsf.dao.ImpostoDAO;
import curso.jsf.dao.ParcelaDAO;
import curso.jsf.dao.VeiculoDAO;
import curso.jsf.model.Imposto;
import curso.jsf.model.Parcela;
import curso.jsf.model.Veiculo;

public class ImpostoService extends Service {

	@Inject
	private ImpostoDAO impostoDAO;
	
	@Inject
	private ParcelaDAO parcelaDAO;
	
	@Inject
	private VeiculoDAO veiculoDAO;

	/**
	 * Cadastra um imposto
	 */
	public void cadastrarImposto(Imposto imposto, String placa) {
		try {
			// inicia uma transação
			beginTransaction();
			
			// lê o veículo
			Veiculo veiculo = veiculoDAO.carregar(placa, Veiculo.class);
			
			// associa o veículo ao imposto
			imposto.setVeiculo(veiculo);
			
			// salva o imposto
			impostoDAO.salvar(imposto);
			
			// calcula parcelas e datas de vencimento
			
			double valorTotal = imposto.getValor();
			int numParcelas = imposto.getNumParcelas();
			
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, imposto.getDiaVencimento());
			
			double valorParcela = valorTotal / numParcelas;
			
			for (int i = 1; i <= numParcelas; i++) {
				// cria as pacelas necessárias e grava no banco de dados
				Parcela p = new Parcela();
				p.setNumParcela(i);
				p.setValor(valorParcela);
				p.setImposto(imposto);
				p.setDataVencimento(calendar.getTime());
				parcelaDAO.salvar(p);
				
				calendar.add(Calendar.MONTH, 1);
			}

			// commit na transação
			commitTransaction();
			
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	public boolean existeImpostoVeiculo(String placa) {
		return impostoDAO.existeImpostoVeiculo(placa);
	}
	
	public List<Parcela> listarParcelasImposto(String placa) {
		return parcelaDAO.listarParcelasImposto(placa);
	}
	
	/**
	 * Paga uma parcela
	 */
	public void pagarParcela(Integer parcelaId) {
		// inicia uma transação
		beginTransaction();
		
		try {
			// carrega a parcela a ser paga
			Parcela parcela = parcelaDAO.carregar(parcelaId, Parcela.class);
			
			// define a data de pagamento
			parcela.setDataPagamento(new Date());
			
			// commit na transação
			commitTransaction();
		
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
}
