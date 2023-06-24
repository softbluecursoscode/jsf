package curso.jsf.dao;

import java.util.List;

import javax.persistence.Query;

import curso.jsf.model.Parcela;

public class ParcelaDAO extends DAO {

	/**
	 * Obtém a lista de parcelas para um imposto cadastrado
	 */
	@SuppressWarnings("unchecked")
	public List<Parcela> listarParcelasImposto(String placa) {
		Query q = criarQuery("SELECT p FROM Parcela p WHERE p.imposto.veiculo.placa = '" + placa + "' ORDER BY p.numParcela");
		return q.getResultList();
	}
}
