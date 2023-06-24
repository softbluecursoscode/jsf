package curso.jsf.dao;

import java.util.List;

import javax.persistence.Query;

import curso.jsf.model.Turma;

/**
 * M�todos de acesso a dados relacionados � entidade Turma
 */
public class TurmaDAO extends DAO {
	
	@SuppressWarnings("unchecked")
	public List<Turma> listarTurmas() {
		Query q = criarQuery("SELECT t FROM Turma t");
		return q.getResultList();
	}
}
