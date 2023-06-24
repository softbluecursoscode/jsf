package curso.jsf.bean;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import curso.jsf.model.CartaoCredito;
import curso.jsf.model.Lancamento;
import curso.jsf.service.CartaoCreditoService;
import curso.jsf.service.CompraService;
import curso.jsf.util.DateUtils;

@Named
@SessionScoped
public class ListarComprasBean extends AbstractBean {
	
	@Inject
	private CompraService compraService;
	
	@Inject
	private CartaoCreditoService cartaoCreditoService;
	
	private Date mesAno;
	
	private CartaoCredito cartaoCredito;
	
	private List<Lancamento> lancamentos;
	
	private List<CartaoCredito> cartoesCredito;
	
	private Double valorTotal;
	
	private Double valorDisponivel;
	
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
			valorTotal = null;
			valorDisponivel = null;
			limpar = false;
		}
	}
	
	/**
	 * Obtém a lista de lançamentos abertos para um cartão até determinado mês/ano
	 */
	public String pesquisar() throws Exception {
		// Obtém a lista de lançamentos
		lancamentos = compraService.pesquisarLancamentos(DateUtils.getMes(mesAno), DateUtils.getAno(mesAno), cartaoCredito.getId());
		
		// Calcula o valor total dos lançamentos
		valorTotal = compraService.calcularValorTotal(lancamentos);
		
		// Calcula o valor ainda disponível para gastar no cartão
		valorDisponivel = compraService.calcularValorDisponivel(cartaoCredito.getId());
		
		// Carrega os dados do cartão
		cartaoCredito = cartaoCreditoService.carregar(cartaoCredito.getId());
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
	 * Exclui uma compra e todos os lançamentos atrelados a ela
	 */
	public String excluir(Integer compraId) throws Exception {
		compraService.excluirCompraELancamentos(compraId);
		pesquisar();
		return redirect("listar_compras");
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

	public Double getValorTotal() {
		return valorTotal;
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

	public Double getValorDisponivel() {
		return valorDisponivel;
	}
}
