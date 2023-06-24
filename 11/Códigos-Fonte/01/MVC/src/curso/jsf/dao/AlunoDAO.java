package curso.jsf.dao;

import java.util.List;

import javax.persistence.Query;

import curso.jsf.model.Aluno;

/**
 * Métodos de acesso a dados relacionados à entidade Aluno
 */
public class AlunoDAO extends DAO {
	
	/**
	 * Obtém a lista de alunos cadastrados no banco de dados
	 * @return Lista de alunos cadastrados
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<Aluno> listarAlunos() {
		Query q = criarQuery("SELECT a FROM Aluno a");
		return q.getResultList();
	}
	
	/**
	 * Obtém a lista de alunos de uma turma
	 * @param turmaId Turma onde os alunos estão
	 * @return Lista de alunos
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<Aluno> obterAlunosTurma(Integer turmaId) {
		Query q = criarQuery("SELECT a FROM Aluno a WHERE a.turma.id = " + turmaId);
		return q.getResultList();
	}
}
