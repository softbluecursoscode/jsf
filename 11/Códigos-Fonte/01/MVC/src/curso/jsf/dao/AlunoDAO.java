package curso.jsf.dao;

import java.util.List;

import javax.persistence.Query;

import curso.jsf.model.Aluno;

/**
 * M�todos de acesso a dados relacionados � entidade Aluno
 */
public class AlunoDAO extends DAO {
	
	/**
	 * Obt�m a lista de alunos cadastrados no banco de dados
	 * @return Lista de alunos cadastrados
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<Aluno> listarAlunos() {
		Query q = criarQuery("SELECT a FROM Aluno a");
		return q.getResultList();
	}
	
	/**
	 * Obt�m a lista de alunos de uma turma
	 * @param turmaId Turma onde os alunos est�o
	 * @return Lista de alunos
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<Aluno> obterAlunosTurma(Integer turmaId) {
		Query q = criarQuery("SELECT a FROM Aluno a WHERE a.turma.id = " + turmaId);
		return q.getResultList();
	}
}
