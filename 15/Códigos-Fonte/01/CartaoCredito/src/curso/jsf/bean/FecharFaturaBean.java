package curso.jsf.bean;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import curso.jsf.model.CartaoCredito;
import curso.jsf.model.Lancamento;
import curso.jsf.service.CartaoCreditoService;
import curso.jsf.service.CompraService;
import curso.jsf.service.FaturaService;
import curso.jsf.service.ValidationException;
import curso.jsf.util.DateUtils;

@Named
@SessionScoped
public class FecharFaturaBean extends AbstractBean {
	
	@Inject
	private CompraService compraService;
	
	@Inject
	private CartaoCreditoService cartaoCreditoService;
	
	@Inject
	private FaturaService faturaService;
	
	private Date mesAno;
	
	private Integer cartaoCreditoId;
	
	private List<Lancamento> lancamentos;
	
	private List<CartaoCredito> cartoesCredito;
	
	private List<String> lancamentosIds;
	
	/**
	 * Flag para controlar se os atributos do bean devem ser resetados. O valor desta flag é obtido através de um 
	 * parâmetro passado na request ao clicar no link que abre a página
	 */
	private boolean limpar;
	
	/**
	 * Reseta os atributos do bean. Este método é chamado toda vez que a view é renderizada
	 * (evento preRenderView)
	 */
	public void init(ComponentSystemEvent event) {
		if (limpar) {
			lancamentos = null;
			mesAno = null;
			cartaoCreditoId = null;
			cartoesCredito = null;
			limpar = false;
		}
	}
	
	/**
	 * Obtém a lista de todos os lançamentos abertos (sem fatura) de um cartão 
	 */
	public String pesquisar() throws Exception {
		lancamentos = compraService.pesquisarLancamentos(cartaoCreditoId);
		return null;
	}
	
	/**
	 * Obtém a lista de cartões cadastrados
	 */
	public List<CartaoCredito> getCartoesCredito() throws Exception {
		if (cartoesCredito == null) {
			cartoesCredito = cartaoCreditoService.listarCartoesCredito();
		}
		return cartoesCredito;
	}
	
	/**
	 * Fecha a fatura
	 */
	public String fecharFatura() throws Exception {
		List<Integer[]> ids = new ArrayList<Integer[]>();
		
		// É preciso fazer o parse dos IDs para separar o ID da compra do número de parcelas
		for (String id : lancamentosIds) {
			Integer compraId = new Integer(id.substring(0, id.indexOf("_")));
			Integer numParcela = new Integer(id.substring(id.indexOf("_") + 1));
			ids.add(new Integer[]{ compraId, numParcela });
		}
		
		try {
			faturaService.fecharFatura(cartaoCreditoId, DateUtils.getMes(mesAno), DateUtils.getAno(mesAno), ids);
			limpar = true;
			return redirect("fecharFatura");
		
		} catch (ValidationException e) {
			// Ocorreu um erro de validação de negócio
			addMessageToRequest(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Formata os dados de um lançamento para exibir na tela
	 * @param lancamento
	 * @return
	 */
	public String formatLancamento(Lancamento lancamento) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
		
		Date data = lancamento.getData();
		double valor = lancamento.getValor();
		String descricao = lancamento.getCompra().getDescricao();
		int numParcela = lancamento.getNumParcela();
		int totalParcelas = lancamento.getCompra().getNumParcelas();
		
		return String.format("%s - %s: %s (%d/%d)", sdf.format(data), nf.format(valor), descricao, numParcela, totalParcelas);
	}
	
	public List<Lancamento> getLancamentos() throws Exception {
		return lancamentos;
	}

	public Date getMesAno() {
		return mesAno;
	}

	public void setMesAno(Date mesAno) {
		this.mesAno = mesAno;
	}

	public Integer getCartaoCreditoId() {
		return cartaoCreditoId;
	}

	public void setCartaoCreditoId(Integer cartaoCreditoId) {
		this.cartaoCreditoId = cartaoCreditoId;
	}

	public boolean isLimpar() {
		return limpar;
	}

	public void setLimpar(boolean limpar) {
		this.limpar = limpar;
	}

	public List<String> getLancamentosIds() {
		return lancamentosIds;
	}

	public void setLancamentosIds(List<String> lancamentosIds) {
		this.lancamentosIds = lancamentosIds;
	}
}
