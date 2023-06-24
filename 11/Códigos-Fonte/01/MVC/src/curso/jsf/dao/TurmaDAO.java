package curso.jsf.dao;

import java.util.List;

import javax.persistence.Query;

import curso.jsf.model.Turma;

/**
 * Métodos de acesso a dados relacionados à entidade Turma
 */
public class TurmaDAO extends DAO {
	
	@SuppressWarnings("unchecked")
	public List<Turma> listarTurmas() {
		Query q = criarQuery("SELECT t FROM Turma t");
		return q.getResultList();
	}
}
