package curso.jsf.service;

import java.util.List;

import javax.inject.Inject;

import curso.jsf.dao.CartaoCreditoDAO;
import curso.jsf.model.CartaoCredito;

/**
 * Classe de neg�cio com opera��es envolvendo cart�es
 */
public class CartaoCreditoService extends Service {

	@Inject
	private CartaoCreditoDAO cartaoCreditoDAO;

	/**
	 * Carrega um cart�o com base no seu ID
	 */
	public CartaoCredito carregar(Integer cartaoCreditoId) {
		return cartaoCreditoDAO.carregar(cartaoCreditoId, CartaoCredito.class);
	}
	
	/**
	 * Grava um cart�o
	 */
	public void salvar(CartaoCredito cartaoCredito) throws ValidationException {
		try {
			beginTransaction();
			
			// Verifica se um cart�o com o mesmo n�mero j� existe
			if (cartaoCreditoDAO.existeCartaoCredito(cartaoCredito.getNumero(), null)) {
				throw new ValidationException("Este n�mero de cart�o de cr�dito j� est� cadastrado");
			}
			
			cartaoCreditoDAO.salvar(cartaoCredito);
			
			commitTransaction();

		} catch(RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	/**
	 * Atualiza um cart�o cadastrado
	 */
	public void atualizar(CartaoCredito cartaoCredito) throws ValidationException {
		try {
			beginTransaction();

			// Verifica se um cart�o com o mesmo n�mero j� existe
			if (cartaoCreditoDAO.existeCartaoCredito(cartaoCredito.getNumero(), cartaoCredito.getId())) {
				throw new ValidationException("Este n�mero de cart�o de cr�dito j� est� cadastrado");
			}
			
			cartaoCreditoDAO.alterar(cartaoCredito);
			commitTransaction();
			
		} catch(RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	/**
	 * Exclui um cart�o
	 */
	public void excluir(Integer cartaoCreditoId) {
		try {
			beginTransaction();
			
			CartaoCredito cartaoCredito = cartaoCreditoDAO.carregar(cartaoCreditoId, CartaoCredito.class);
			cartaoCreditoDAO.excluir(cartaoCredito);
			
			commitTransaction();
		
		} catch(RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	/**
	 * Obt�m a lista de cart�es cadastrados
	 */
	public List<CartaoCredito> listarCartoesCredito() {
		return cartaoCreditoDAO.listarCartoesCredito();
	}
}
