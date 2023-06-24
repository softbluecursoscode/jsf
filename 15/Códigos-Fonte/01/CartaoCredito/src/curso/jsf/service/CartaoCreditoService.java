package curso.jsf.service;

import java.util.List;

import javax.inject.Inject;

import curso.jsf.dao.CartaoCreditoDAO;
import curso.jsf.model.CartaoCredito;

/**
 * Classe de negócio com operações envolvendo cartões
 */
public class CartaoCreditoService extends Service {

	@Inject
	private CartaoCreditoDAO cartaoCreditoDAO;

	/**
	 * Carrega um cartão com base no seu ID
	 */
	public CartaoCredito carregar(Integer cartaoCreditoId) {
		return cartaoCreditoDAO.carregar(cartaoCreditoId, CartaoCredito.class);
	}
	
	/**
	 * Grava um cartão
	 */
	public void salvar(CartaoCredito cartaoCredito) throws ValidationException {
		try {
			beginTransaction();
			
			// Verifica se um cartão com o mesmo número já existe
			if (cartaoCreditoDAO.existeCartaoCredito(cartaoCredito.getNumero(), null)) {
				throw new ValidationException("Este número de cartão de crédito já está cadastrado");
			}
			
			cartaoCreditoDAO.salvar(cartaoCredito);
			
			commitTransaction();

		} catch(RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	/**
	 * Atualiza um cartão cadastrado
	 */
	public void atualizar(CartaoCredito cartaoCredito) throws ValidationException {
		try {
			beginTransaction();

			// Verifica se um cartão com o mesmo número já existe
			if (cartaoCreditoDAO.existeCartaoCredito(cartaoCredito.getNumero(), cartaoCredito.getId())) {
				throw new ValidationException("Este número de cartão de crédito já está cadastrado");
			}
			
			cartaoCreditoDAO.alterar(cartaoCredito);
			commitTransaction();
			
		} catch(RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	/**
	 * Exclui um cartão
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
	 * Obtém a lista de cartões cadastrados
	 */
	public List<CartaoCredito> listarCartoesCredito() {
		return cartaoCreditoDAO.listarCartoesCredito();
	}
}
