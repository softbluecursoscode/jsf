package curso.jsf.dao;

import java.util.List;

import javax.persistence.Query;

import curso.jsf.model.Veiculo;

public class VeiculoDAO extends DAO {

	/**
	 * Obtém a lista de veículos com base no CPF de um proprietário
	 */
	@SuppressWarnings("unchecked")
	public List<Veiculo> listarVeiculosPorCPF(String cpf) {
		Query q = criarQuery("SELECT v FROM Veiculo v WHERE v.proprietario.cpf = '" + cpf + "' ORDER BY v.placa");
		return q.getResultList();
	}
}
