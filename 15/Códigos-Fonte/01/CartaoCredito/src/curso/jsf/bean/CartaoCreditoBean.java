package curso.jsf.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import curso.jsf.model.CartaoCredito;
import curso.jsf.service.CartaoCreditoService;
import curso.jsf.service.ValidationException;


@Named("cartaoBean")
@RequestScoped
public class CartaoCreditoBean extends AbstractBean {
	
	@Inject
	private CartaoCreditoService cartaoCreditoService;

	private CartaoCredito cartaoCredito;
	
	private List<CartaoCredito> cartoesCredito;
	
	/**
	 * Salva ou atualiza o cartão
	 */
	public String gravar() throws Exception {
		try {
			// A operação a ser realizada depende da existência ou não do ID
			if (cartaoCredito.getId() == null) {
				cartaoCreditoService.salvar(cartaoCredito);
			} else {
				cartaoCreditoService.atualizar(cartaoCredito);
			}
			
			cartaoCredito = null;
			return redirect("cartao_credito");
		
		} catch (ValidationException e) {
			// Ocorreu um erro de validação de negócio
			addMessageToRequest(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Carrega um cartão existente para que ele possa ser alterado
	 */
	public String alterar(Integer cartaoCreditoId) throws Exception {
		cartaoCredito = cartaoCreditoService.carregar(cartaoCreditoId);
		return null;
	}
	
	/**
	 * Exclui um cartão
	 */
	public String excluir(Integer cartaoCreditoId) throws Exception {
		cartaoCreditoService.excluir(cartaoCreditoId);
		cartoesCredito = null;
		return redirect("cartao_credito");
	}
	
	/**
	 * Limpa o formulário
	 */
	public String limpar() throws Exception {
		cartaoCredito = null;
		return null;
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

	/**
	 * Obtém a lista de cartões cadastrados
	 */
	public List<CartaoCredito> getCartoesCredito() throws Exception {
		if (cartoesCredito == null) {
			cartoesCredito = cartaoCreditoService.listarCartoesCredito();
		}
		return cartoesCredito;
	}
}
