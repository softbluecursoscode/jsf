package curso.jsf.dao;

import java.util.List;

import javax.persistence.Query;

import curso.jsf.model.Proprietario;

public class ProprietarioDAO extends DAO {

	/**
	 * Carrega os dados de um proprietário com base no seu CPF
	 */
	public Proprietario lerProprietario(String cpf) {
		Query q = criarQuery("SELECT p FROM Proprietario p WHERE p.cpf = '" + cpf + "'");
		List<?> results = q.getResultList();
		
		if (results.size() == 0) {
			return null;
		}
		
		return (Proprietario) results.get(0);
	}
}
