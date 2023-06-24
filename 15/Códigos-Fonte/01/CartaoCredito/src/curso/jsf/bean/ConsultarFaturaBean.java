package curso.jsf.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import curso.jsf.model.CartaoCredito;
import curso.jsf.model.Fatura;
import curso.jsf.model.Lancamento;
import curso.jsf.service.CartaoCreditoService;
import curso.jsf.service.CompraService;
import curso.jsf.service.FaturaService;

@Named
@SessionScoped
public class ConsultarFaturaBean extends AbstractBean {
	
	@Inject
	private CartaoCreditoService cartaoCreditoService;
	
	@Inject
	private FaturaService faturaService;
	
	@Inject
	private CompraService compraService;
	
	private Date mesAno;
	
	private CartaoCredito cartaoCredito;
	
	private Fatura fatura;
	
	private List<Lancamento> lancamentos;
	
	private List<CartaoCredito> cartoesCredito;
	
	private List<Fatura> faturas;
	
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
			cartaoCredito = null;
			cartoesCredito = null;
			faturas = null;
			fatura = null;
			limpar = false;
		}
	}
	
	/**
	 * Consulta os dados da fatura
	 */
	public String consultar() throws Exception {
		// Obtém a lista de lançamentos da fatura
		lancamentos = compraService.listarLancamentosFatura(fatura.getId());
		
		// Carrega o cartão de crédito
		cartaoCredito = cartaoCreditoService.carregar(cartaoCredito.getId());
		
		// Carrega a fatura
		fatura = faturaService.carregar(fatura.getId());
		return null;
	}
	
	/**
	 * Carrega a lista de faturas para o cartão selecionado. Este método é chamado a partir de um
	 * value change event
	 */
	public void listarFaturas(ValueChangeEvent event) throws Exception {
		Integer cartaoCreditoId = (Integer) event.getNewValue();
		
		// Obtém a lista de faturas
		faturas = faturaService.listarFaturas(cartaoCreditoId);
		lancamentos = null;
		
		// Pula para a fase render response, evitando que a validação seja executada
		FacesContext.getCurrentInstance().renderResponse();
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
	 * Método utilizado para formatar a data de vencimento da fatura
	 */
	public String formatarDataFatura(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
		return sdf.format(data);
	}
	
	public List<Fatura> getFaturas() {
		return faturas;
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

	public CartaoCredito getCartaoCredito() {
		if (cartaoCredito == null) {
			cartaoCredito = new CartaoCredito();
		}
		return cartaoCredito;
	}

	public void setCartaoCredito(CartaoCredito cartaoCredito) {
		this.cartaoCredito = cartaoCredito;
	}

	public boolean isLimpar() {
		return limpar;
	}

	public void setLimpar(boolean limpar) {
		this.limpar = limpar;
	}

	public Fatura getFatura() {
		if (fatura == null) {
			 fatura = new Fatura();
		}
		return fatura;
	}

	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
	}
}
