package curso.jsf.bean;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import curso.jsf.model.CartaoCredito;
import curso.jsf.model.Compra;
import curso.jsf.model.Compra.Tipo;
import curso.jsf.service.CartaoCreditoService;
import curso.jsf.service.CompraService;

@Named
@RequestScoped
public class CompraBean extends AbstractBean {

	@Inject
	private CompraService compraService;
	
	@Inject 
	private CartaoCreditoService cartaoCreditoService;
	
	private Compra compra;
	
	private List<CartaoCredito> cartoesCredito;
	
	public CompraBean() {
		// Quando o bean é construído, inicializa alguns valores com informações padrão
		compra = new Compra();
		compra.setCartaoCredito(new CartaoCredito());
		
		compra.setNumParcelas(1);
		compra.setTipo(Tipo.DEBITO);
	}
	
	/**
	 * Grava os dados da compra
	 */
	public String gravar() throws Exception {
		compraService.registrarCompra(compra);
		compra = null;
		return redirect("cadastrar_compra");
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
	 * Retorna os itens do enum Tipo como uma lista
	 */
	public List<Tipo> getTipos() {
		return Arrays.asList(Tipo.values());
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}
}
