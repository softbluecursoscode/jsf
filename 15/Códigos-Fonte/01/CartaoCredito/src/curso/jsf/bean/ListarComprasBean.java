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
	 * Flag para controlar se os atributos do bean devem ser resetados. O valor desta flag � obtido atrav�s de um 
	 * par�metro passado na request ao clicar no link que abre a p�gina
	 */
	private boolean limpar;
	
	/**
	 * Reseta os atributos do bean. Este m�todo � chamado toda vez que a view � renderizada
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
	 * Obt�m a lista de lan�amentos abertos para um cart�o at� determinado m�s/ano
	 */
	public String pesquisar() throws Exception {
		// Obt�m a lista de lan�amentos
		lancamentos = compraService.pesquisarLancamentos(DateUtils.getMes(mesAno), DateUtils.getAno(mesAno), cartaoCredito.getId());
		
		// Calcula o valor total dos lan�amentos
		valorTotal = compraService.calcularValorTotal(lancamentos);
		
		// Calcula o valor ainda dispon�vel para gastar no cart�o
		valorDisponivel = compraService.calcularValorDisponivel(cartaoCredito.getId());
		
		// Carrega os dados do cart�o
		cartaoCredito = cartaoCreditoService.carregar(cartaoCredito.getId());
		return null;
	}
	
	/**
	 * Obt�m a lista de cart�es cadastrados
	 */
	public List<CartaoCredito> getCartoesCredito() throws Exception {
		if (cartoesCredito == null) {
			cartoesCredito = cartaoCreditoService.listarCartoesCredito();
		}
		return cartoesCredito;
	}
	
	/**
	 * Exclui uma compra e todos os lan�amentos atrelados a ela
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
