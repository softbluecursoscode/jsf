package curso.jsf.dao;

import javax.persistence.Query;

public class ImpostoDAO extends DAO {
	
	/**
	 * Verifica se existe um imposto cadastrado para um veículo
	 */
	public boolean existeImpostoVeiculo(String placa) {
		Query q = criarQuery("SELECT COUNT(i) FROM Imposto i WHERE i.veiculo.placa = '" + placa +"'");
		long count = (Long) q.getResultList().get(0);
		return count > 0;
	}
}
